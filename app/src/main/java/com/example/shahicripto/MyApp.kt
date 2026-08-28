package com.example.shahicripto

import android.app.Application
import android.util.Log
import com.google.firebase.analytics.FirebaseAnalytics
import com.example.shahicripto.model.MyDatabase
import com.example.shahicripto.util.NewsTranslator
import dagger.hilt.android.HiltAndroidApp
import io.reactivex.rxjava3.exceptions.UndeliverableException
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import java.io.IOException
import java.net.SocketException
import java.util.concurrent.CancellationException

@HiltAndroidApp
class MyApp :Application() {
    private lateinit var analytics: FirebaseAnalytics

    /** One ML Kit translator/model for the whole process instead of one per screen. */
    val newsTranslator: NewsTranslator by lazy {
        NewsTranslator(this, MyDatabase.getDatabase(this).newsTranslationDao)
    }

    override fun onCreate() {
        super.onCreate()
        installRxJavaErrorHandler()
        analytics = FirebaseAnalytics.getInstance(this)
    }

    private fun installRxJavaErrorHandler() {
        RxJavaPlugins.setErrorHandler { error ->
            val cause = if (error is UndeliverableException) error.cause ?: error else error
            when (cause) {
                is InterruptedException,
                is CancellationException,
                is IOException,
                is SocketException -> {
                    // Expected when a translation/database request is disposed
                    // because the user scrolled away or the screen was rebuilt.
                }
                else -> Log.e("RxJava", "Undeliverable RxJava error", cause)
            }
        }
    }



}
