package com.example.shahicripto.features.marketScreen.newsFragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import com.example.shahicripto.databinding.FragmentNewsBinding
import com.example.shahicripto.features.marketScreen.MarketScreenViewModel
import com.example.shahicripto.features.newsScreen.NewsActivity
import com.example.shahicripto.model.MainRepository
import com.example.shahicripto.model.MyDatabase
import com.example.shahicripto.model.api.ApiService
import com.example.shahicripto.model.local.NewsData.NewsDataEntity
import com.example.shahicripto.util.MarketViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NewsFragment : Fragment(), NewsAdapter.RecyclerCallback {
    lateinit var binding: FragmentNewsBinding
    private lateinit var marketScreenViewModel: MarketScreenViewModel

    @Inject
    lateinit var glide: RequestManager

    @Inject
    lateinit var apiService: ApiService


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        marketScreenViewModel = ViewModelProvider(
            this, MarketViewModelFactory(
                MainRepository(
                    apiService,
                    MyDatabase.getDatabase(view.context).coinsDataDao,
                    MyDatabase.getDatabase(view.context).newsDataDao
                )
            )
        ).get(MarketScreenViewModel::class.java)


        initUi()

    }

    private fun initUi() {
        getNewsFromDataBase()
    }


    private fun showDataINRecycler(data: List<NewsDataEntity>) {


        val newsAdapter = NewsAdapter(glide, ArrayList(data), this)

        binding.recyclerNews.adapter = newsAdapter
        binding.recyclerNews.layoutManager = LinearLayoutManager(context)

    }

    override fun onItemClicked(dataNews: NewsDataEntity) {
        val intent = Intent(context , NewsActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable("bundle1", dataNews)
        intent.putExtra("bundle", bundle)
        startActivity(intent)
    }


    @SuppressLint("FragmentLiveDataObserve")
    private fun getNewsFromDataBase() {


        marketScreenViewModel.getTopNewsFromDataBase().observe(this) {
            showDataINRecycler(it)

        }


    }
}