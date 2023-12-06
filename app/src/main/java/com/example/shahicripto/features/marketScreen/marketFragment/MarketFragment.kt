package com.example.shahicripto.features.marketScreen.marketFragment

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import com.example.shahicripto.databinding.FragmentMarketBinding
import com.example.shahicripto.features.coinScreen.CoinActivity
import com.example.shahicripto.features.marketScreen.MarketActivity
import com.example.shahicripto.features.marketScreen.MarketScreenViewModel
import com.example.shahicripto.model.MainRepository
import com.example.shahicripto.model.MyDatabase
import com.example.shahicripto.model.api.ApiService
import com.example.shahicripto.model.local.CoinAboutData
import com.example.shahicripto.model.local.CoinAboutItem
import com.example.shahicripto.model.local.CoinsData.CoinsDataEntitity
import com.example.shahicripto.util.MarketViewModelFactory
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@AndroidEntryPoint
class MarketFragment : Fragment(), MarketAdapter.RecyclerCallback {
    lateinit var binding: FragmentMarketBinding
    lateinit var marketScreenViewModel: MarketScreenViewModel
    lateinit var aboutDataMap: MutableMap<String, CoinAboutItem>

    @Inject
    lateinit var glide: RequestManager

    @Inject
    lateinit var apiService: ApiService
    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMarketBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        marketScreenViewModel = ViewModelProvider(
            this,
            MarketViewModelFactory(
                MainRepository(
                    apiService,
                    MyDatabase.getDatabase(view.context).coinsDataDao,
                    MyDatabase.getDatabase(view.context).newsDataDao
                )
            )
        ).get(MarketScreenViewModel::class.java)




        initUi()

        binding.layoutWatchlist.btnShowMore.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://coinmarketcap.com/"))
            startActivity(intent)
        }



        getAboutDataFromAssest()


    }


    private fun getAboutDataFromAssest() {

        val fileInString = context?.assets
            ?.open("currencyinfo.json")
            ?.bufferedReader()
            .use { it?.readText() }

        aboutDataMap = mutableMapOf<String, CoinAboutItem>()

        val gson = Gson()
        val dataAboutAll = gson.fromJson(fileInString, CoinAboutData::class.java)

        dataAboutAll.forEach {
            aboutDataMap[it.currencyName] = CoinAboutItem(
                it.info.web,
                it.info.github,
                it.info.twt,
                it.info.desc,
                it.info.reddit
            )
        }
    }

    override fun onResume() {
        super.onResume()
    }

    private fun initUi() {
        getTopCoinsDataBase()
    }

    override fun onItemClicked(dataCoin: CoinsDataEntitity) {

        val intent = Intent(context, CoinActivity::class.java)
//        intent.putExtra("sendToData", dataCoin)


        val bundle = Bundle()
        bundle.putParcelable("bundle1", dataCoin)
        bundle.putParcelable("bundle2", aboutDataMap[dataCoin.name])
        intent.putExtra("bundle", bundle)

        startActivity(intent)

    }


    @SuppressLint("FragmentLiveDataObserve")
    private fun getTopCoinsDataBase() {

        marketScreenViewModel
            .getTopCoinsFromDataBase()
            .observe(this) {
                showDataINRecycler(it)
            }

        marketScreenViewModel.getErrorData().observe(MarketActivity()) {
            Log.e("errorData", it)
        }

    }

    private fun showDataINRecycler(data: List<CoinsDataEntitity>) {


        val marketAdapter = MarketAdapter(glide, ArrayList(data), this)

        binding.layoutWatchlist.recyclerMarket.adapter = marketAdapter
        binding.layoutWatchlist.recyclerMarket.layoutManager = LinearLayoutManager(context)

    }


    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()

    }
}