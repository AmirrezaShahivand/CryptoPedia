package com.example.shahicripto.util

import android.content.Context
import com.example.shahicripto.model.local.NewsData.NewsDataEntity
import com.example.shahicripto.model.local.NewsData.NewsDisplayItem
import com.example.shahicripto.model.local.NewsData.NewsTranslationDao
import com.example.shahicripto.model.local.NewsData.NewsTranslationEntity
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.net.URI

class NewsTranslator(
    context: Context,
    private val dao: NewsTranslationDao
) {
    private val translator: Translator by lazy {
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ENGLISH)
            .setTargetLanguage(TranslateLanguage.PERSIAN)
            .build()
        Translation.getClient(options)
    }

    /**
     * Model download is shared by all articles. Creating a translator for every
     * article makes ML Kit allocate a native instance for every item and can
     * exhaust the phone memory when the Persian list is opened.
     */
    private val modelReady: Single<Unit> by lazy {
        Single.create<Unit> { emitter ->
            translator.downloadModelIfNeeded(DownloadConditions.Builder().build())
                .addOnSuccessListener { if (!emitter.isDisposed) emitter.onSuccess(Unit) }
                .addOnFailureListener { if (!emitter.isDisposed) emitter.onError(it) }
        }
            .subscribeOn(Schedulers.io())
            .cache()
    }

    fun display(news: NewsDataEntity, language: String): Single<NewsDisplayItem> {
        val source = sourceOf(news)
        if (language != PERSIAN) {
            return Single.just(NewsDisplayItem(news, news.title, news.body, source))
        }

        return findCachedTranslation(news.url)
            .flatMap { cached ->
                if (cached.newsUrl.isNotBlank() && cached.sourceHash == sourceHash(news)) {
                    Single.just(NewsDisplayItem(news, cached.translatedTitle, cached.translatedBody, source))
                } else {
                    translate(news)
                        .flatMap { translated ->
                            saveTranslation(translated)
                        }
                        .map { NewsDisplayItem(news, it.translatedTitle, it.translatedBody, source) }
                }
            }
            .onErrorReturn { NewsDisplayItem(news, news.title, news.body, source) }
    }

    fun displayAll(news: List<NewsDataEntity>, language: String): Single<List<NewsDisplayItem>> {
        return Observable.fromIterable(news)
            .flatMap(
                { item -> display(item, language).toObservable() },
                false,
                MAX_PARALLEL_TRANSLATIONS
            )
            .toList()
    }

    /** Translates and caches a non-news text such as a CoinPaprika description. */
    fun displayText(cacheKey: String, text: String, language: String): Single<String> {
        if (language != PERSIAN || text.isBlank()) return Single.just(text)

        return findCachedTranslation(cacheKey)
            .flatMap { cached ->
                if (cached.newsUrl.isNotBlank() && cached.sourceHash == text.hashCode().toString()) {
                    Single.just(cached.translatedBody)
                } else {
                    modelReady.flatMap { translateText(text) }
                        .flatMap { translated ->
                            saveTranslation(
                                NewsTranslationEntity(
                                    cacheKey,
                                    PERSIAN,
                                    text,
                                    translated,
                                    text.hashCode().toString()
                                )
                            ).map { translated }
                        }
                }
            }
            .onErrorReturn { text }
    }

    private fun translate(news: NewsDataEntity): Single<NewsTranslationEntity> {
        return modelReady.flatMap {
            translateText(news.title).flatMap { translatedTitle ->
                if (news.body.isBlank()) {
                    Single.just(
                        NewsTranslationEntity(
                            news.url,
                            PERSIAN,
                            translatedTitle,
                            news.body,
                            sourceHash(news)
                        )
                    )
                } else {
                    translateText(news.body).map { translatedBody ->
                        NewsTranslationEntity(
                            news.url,
                            PERSIAN,
                            translatedTitle,
                            translatedBody,
                            sourceHash(news)
                        )
                    }
                }
            }
        }
    }

    private fun translateText(text: String): Single<String> {
        if (text.isBlank()) return Single.just(text)
        return Single.create { emitter ->
            translator.translate(text)
                .addOnSuccessListener { if (!emitter.isDisposed) emitter.onSuccess(it) }
                .addOnFailureListener { if (!emitter.isDisposed) emitter.onError(it) }
        }.subscribeOn(Schedulers.io())
    }

    /**
     * Room's blocking DAO can throw InterruptedException when its Rx chain is
     * disposed. Single.fromCallable forwards that late error to RxJava's
     * global error handler, which can terminate the app. Single.create lets us
     * ignore the expected error after disposal.
     */
    private fun findCachedTranslation(key: String): Single<NewsTranslationEntity> {
        return Single.create { emitter ->
            try {
                val value = dao.find(key, PERSIAN) ?: EMPTY
                if (!emitter.isDisposed) emitter.onSuccess(value)
            } catch (error: Throwable) {
                if (!emitter.isDisposed) emitter.onError(error)
            }
        }.subscribeOn(Schedulers.io())
    }

    private fun saveTranslation(value: NewsTranslationEntity): Single<NewsTranslationEntity> {
        return Single.create { emitter ->
            try {
                dao.save(value)
                if (!emitter.isDisposed) emitter.onSuccess(value)
            } catch (error: Throwable) {
                if (!emitter.isDisposed) emitter.onError(error)
            }
        }.subscribeOn(Schedulers.io())
    }

    private fun sourceOf(news: NewsDataEntity): String {
        return runCatching { URI(news.url).host?.removePrefix("www.") }
            .getOrNull().orEmpty().ifBlank { "News" }
    }

    private fun sourceHash(news: NewsDataEntity): String {
        return "${news.title}\u0000${news.body}".hashCode().toString()
    }

    fun close() {
        translator.close()
    }

    companion object {
        const val PERSIAN = "fa"
        private const val MAX_PARALLEL_TRANSLATIONS = 3
        private val EMPTY = NewsTranslationEntity("", "", "", "", "")
    }
}
