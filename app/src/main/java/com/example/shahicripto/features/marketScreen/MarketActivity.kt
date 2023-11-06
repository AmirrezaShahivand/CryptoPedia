package com.example.shahicripto.features.marketScreen

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import cn.pedant.SweetAlert.SweetAlertDialog
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shahicripto.util.NetworkChecker
import com.example.shahicripto.model.local.CoinAboutData
import com.example.shahicripto.model.local.CoinAboutItem
import com.example.shahicripto.model.MainRepository
import com.example.shahicripto.databinding.ActivityMarketBinding
import com.example.shahicripto.features.coinScreen.CoinActivity
import com.example.shahicripto.model.MyDatabase
import com.example.shahicripto.model.local.CoinsData.CoinsDataEntitity
import com.example.shahicripto.model.local.NewsData.NewsData
import com.example.shahicripto.model.local.NewsData.NewsDataEntity
import com.example.shahicripto.util.ApiServiceSingleton
import com.example.shahicripto.util.MarketViewModelFactory
import com.example.shahicripto.util.asyncRequest
import com.example.shahicripto.util.showToast
import com.google.gson.Gson
import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.lang.Exception

class MarketActivity : AppCompatActivity(), MarketAdapter.RecyclerCallback {
    lateinit var binding: ActivityMarketBinding
    lateinit var marketScreenViewModel: MarketScreenViewModel
    lateinit var dataNews: ArrayList<Pair<String, String>>
    lateinit var aboutDataMap: MutableMap<String, CoinAboutItem>
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMarketBinding.inflate(layoutInflater)
        setContentView(binding.root)
        marketScreenViewModel = ViewModelProvider(
            this,
            MarketViewModelFactory(
                MainRepository(
                    ApiServiceSingleton.apiService!!,
                    MyDatabase.getDatabase(applicationContext).coinsDataDao,
                    MyDatabase.getDatabase(applicationContext).newsDataDao
                )
            )
        ).get(MarketScreenViewModel::class.java)

        initUi()

        binding.layoutWatchlist.btnShowMore.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://coinmarketcap.com/"))
            startActivity(intent)
        }

        binding.swipeRefreshMain.setOnRefreshListener {
            marketScreenViewModel.refreshData()
            marketScreenViewModel.refreshNews()
            initUi()
            Handler(Looper.getMainLooper()).postDelayed({
                binding.swipeRefreshMain.isRefreshing = false
            }, 1500)
        }

        getAboutDataFromAssest()

        binding.layoutToolbar.info.setOnClickListener {
            val dialog = SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
            dialog.titleText = "About developer"
            dialog.contentText =
                "<< Amirreza Shahivand >>  gmail : shahivandamirreza@gmail.com \n github : AmirrezaShahivand"
            dialog.confirmText = "OK :)"
            dialog.show()

            dialog.setConfirmClickListener {
                dialog.dismiss()
            }
        }


    }

    private fun internetChecker(context: Context) {
        if (NetworkChecker(context).isInternetConnected) {

        } else {
            Toast.makeText(this, "لطفا اینترنت خود را متصل نمایید!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getAboutDataFromAssest() {

        val fileInString = applicationContext.assets
            .open("currencyinfo.json")
            .bufferedReader()
            .use { it.readText() }

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
        getNewsFromApi()
        getTopCoinsDataBase()
        internetChecker(this)
    }

    private fun getNewsFromApi() {


        marketScreenViewModel
            .getTopNewsFromDataBase()
            .observe(this) {

                val dataToSend: ArrayList<Pair<String, String>> = arrayListOf()

                it.forEach {
                    dataToSend.add(Pair(it.title, it.url))
                }

                dataNews = dataToSend


                Handler(Looper.getMainLooper()).postDelayed({
                    refreshNews()
                }, 1500)

            }


    }


    private fun refreshNews() {
        val randomAccess = (0..49).random()
        try {
            binding.layoutNews.txtNews.text = dataNews[randomAccess].first ?: "null"
            binding.layoutNews.imgNews.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(dataNews[randomAccess].second))
                startActivity(intent)
            }
            binding.layoutNews.txtNews.setOnClickListener {
                refreshNews()
            }
        } catch (e: Throwable) {
            val handler = Handler()
            handler.postDelayed({ refreshNews() }, 1500)
        }


    }


    private fun getTopCoinsDataBase() {

        marketScreenViewModel
            .getTopCoinsFromDataBase()
            .observe(this) {
                showDataINRecycler(it)
            }

        marketScreenViewModel.getErrorData().observe(this) {
            Log.e("errorData" , it)
        }

    }

    private fun showDataINRecycler(data: List<CoinsDataEntitity>) {


        val marketAdapter = MarketAdapter(ArrayList(data), this)

        binding.layoutWatchlist.recyclerMarket.adapter = marketAdapter
        binding.layoutWatchlist.recyclerMarket.layoutManager = LinearLayoutManager(this)

    }

    override fun onItemClicked(dataCoin: CoinsDataEntitity) {

        val intent = Intent(this, CoinActivity::class.java)
//        intent.putExtra("sendToData", dataCoin)

        val bundle = Bundle()
        bundle.putParcelable("bundle1", dataCoin)
        bundle.putParcelable("bundle2", aboutDataMap[dataCoin.name])
        intent.putExtra("bundle", bundle)

        startActivity(intent)

    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()

    }

}