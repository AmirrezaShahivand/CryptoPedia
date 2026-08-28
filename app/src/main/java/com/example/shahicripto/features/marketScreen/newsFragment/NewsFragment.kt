package com.example.shahicripto.features.marketScreen.newsFragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.shahicripto.databinding.FragmentNewsBinding
import com.example.shahicripto.MyApp
import com.example.shahicripto.features.marketScreen.MarketScreenViewModel
import com.example.shahicripto.features.newsScreen.NewsActivity
import com.example.shahicripto.model.MainRepository
import com.example.shahicripto.model.MyDatabase
import com.example.shahicripto.model.api.ApiService
import com.example.shahicripto.model.local.NewsData.NewsDataEntity
import com.example.shahicripto.model.local.NewsData.NewsDisplayItem
import com.example.shahicripto.util.MarketViewModelFactory
import com.example.shahicripto.util.NewsTranslator
import com.example.shahicripto.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

@AndroidEntryPoint
class NewsFragment : Fragment(), NewsAdapter.RecyclerCallback {
    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private lateinit var marketScreenViewModel: MarketScreenViewModel

    @Inject
    lateinit var glide: RequestManager

    @Inject
    lateinit var apiService: ApiService

    private val newsTranslator: NewsTranslator
        get() = (requireContext().applicationContext as MyApp).newsTranslator
    private val translationDisposables = CompositeDisposable()
    private var newsAdapter: NewsAdapter? = null
    private var currentNews: List<NewsDataEntity> = emptyList()
    private val translatedUrls = mutableSetOf<String>()
    private val queuedUrls = mutableSetOf<String>()
    private val pendingUrls = linkedSetOf<String>()
    private var translationInProgress = false

    private val newsScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (dy <= 0 || currentNews.isEmpty()) return

            val layoutManager = recyclerView.layoutManager as? LinearLayoutManager ?: return
            val first = layoutManager.findFirstVisibleItemPosition().coerceAtLeast(0)
            val last = layoutManager.findLastVisibleItemPosition()
                .coerceAtMost(currentNews.lastIndex)
            if (last < first) return

            // Translate the visible cards and prefetch the next two cards.
            val end = (last + 2).coerceAtMost(currentNews.lastIndex)
            requestTranslations(currentNews.subList(first, end + 1))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        marketScreenViewModel = ViewModelProvider(
            this,
            MarketViewModelFactory(
                MainRepository(
                    apiService,
                    MyDatabase.getDatabase(view.context).coinsDataDao,
                    MyDatabase.getDatabase(view.context).newsDataDao,
                    MyDatabase.getDatabase(view.context).coinCatalogDao,
                    MyDatabase.getDatabase(view.context).priceSnapshotDao
                )
            )
        )[MarketScreenViewModel::class.java]

        getNewsFromDataBase()
    }

    private fun showDataInRecycler(data: List<NewsDisplayItem>) {
        binding.progressNews.visibility = View.GONE
        binding.txtNewsEmpty.visibility = if (data.isEmpty()) View.VISIBLE else View.GONE
        binding.recyclerNews.apply {
            if (layoutManager == null) {
                layoutManager = LinearLayoutManager(context)
                addOnScrollListener(newsScrollListener)
            }
            if (newsAdapter == null) {
                newsAdapter = NewsAdapter(glide, this@NewsFragment)
                adapter = newsAdapter
            } else {
                newsAdapter?.updateItems(data)
            }
        }
    }

    private fun requestTranslations(news: List<NewsDataEntity>) {
        if (currentLanguage() != NewsTranslator.PERSIAN || news.isEmpty()) return

        val candidates = news.filter { item ->
            item.url !in translatedUrls && item.url !in queuedUrls
        }
        if (candidates.isEmpty()) return

        queuedUrls.addAll(candidates.map { it.url })
        binding.recyclerNews.post {
            newsAdapter?.markTranslating(candidates.map { it.url }.toSet())
        }
        if (translationInProgress) {
            pendingUrls.addAll(candidates.map { it.url })
            return
        }
        translateBatch(candidates)
    }

    private fun translateBatch(batch: List<NewsDataEntity>) {
        if (batch.isEmpty()) return
        translationInProgress = true
        val batchUrls = batch.map { it.url }.toSet()

        newsTranslator.displayAll(batch, NewsTranslator.PERSIAN)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ translatedItems ->
                queuedUrls.removeAll(batchUrls)
                translatedUrls.addAll(batchUrls)
                translationInProgress = false
                // A translation may finish from inside RecyclerView's scroll
                // callback. Post adapter notifications to the next UI frame;
                // notifying immediately can throw while RecyclerView is
                // computing its layout.
                _binding?.recyclerNews?.post {
                    if (_binding != null) {
                        val translatedByUrl = translatedItems.associateBy { it.original.url }
                        newsAdapter?.updateItems(
                            newsAdapter?.itemsSnapshot()?.map { current ->
                                translatedByUrl[current.original.url] ?: current
                            }.orEmpty()
                        )
                    }
                }
                translatePending()
            }, {
                // Keep original text and do not retry endlessly on a bad item.
                queuedUrls.removeAll(batchUrls)
                translatedUrls.addAll(batchUrls)
                translationInProgress = false
                val originals = batch.associateBy { it.url }
                _binding?.recyclerNews?.post {
                    val currentItems = newsAdapter?.itemsSnapshot().orEmpty()
                    newsAdapter?.updateItems(currentItems.map { current ->
                        val original = originals[current.original.url] ?: return@map current
                        NewsDisplayItem(
                            original,
                            original.title,
                            original.body,
                            original.url.substringAfter("//").substringBefore("/")
                        )
                    })
                }
                translatePending()
            })
            .also(translationDisposables::add)
    }

    private fun translatePending() {
        if (pendingUrls.isEmpty()) return
        val next = currentNews.filter { item ->
            item.url in pendingUrls && item.url !in translatedUrls
        }
        pendingUrls.removeAll(next.map { it.url })
        if (next.isNotEmpty()) translateBatch(next)
    }

    private fun currentLanguage(): String = resources.configuration.locales[0].language

    override fun onItemClicked(dataNews: NewsDataEntity) {
        val intent = Intent(requireContext(), NewsActivity::class.java)
        val bundle = Bundle()
        bundle.putSerializable("bundle1", dataNews)
        intent.putExtra("bundle", bundle)
        startActivity(intent)
    }

    private fun getNewsFromDataBase() {
        marketScreenViewModel.getTopNewsFromDataBase().observe(viewLifecycleOwner) { news ->
            translationDisposables.clear()
            translationInProgress = false
            currentNews = news
            translatedUrls.clear()
            queuedUrls.clear()
            pendingUrls.clear()

            showDataInRecycler(news.mapIndexed { index, item ->
                NewsDisplayItem(
                    item,
                    item.title,
                    item.body,
                    item.url.substringAfter("//").substringBefore("/"),
                    currentLanguage() == NewsTranslator.PERSIAN && index < INITIAL_TRANSLATION_COUNT
                )
            })

            // Translate at least ten top stories immediately. Further stories
            // are translated when they approach the visible viewport.
            requestTranslations(news.take(INITIAL_TRANSLATION_COUNT))
        }

        marketScreenViewModel.getErrorData().observe(viewLifecycleOwner) {
            binding.progressNews.visibility = View.GONE
            binding.txtNewsEmpty.text = it
            binding.txtNewsEmpty.visibility = View.VISIBLE
            context?.showToast(it)
        }

        marketScreenViewModel.refreshNews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        translationDisposables.clear()
        binding.recyclerNews.removeOnScrollListener(newsScrollListener)
        newsAdapter = null
        currentNews = emptyList()
        translationInProgress = false
        _binding = null
    }

    companion object {
        private const val INITIAL_TRANSLATION_COUNT = 10
    }
}
