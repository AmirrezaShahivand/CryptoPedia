package com.example.shahicripto.features.marketActivity

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shahicripto.NetworkChecker
import com.example.shahicripto.apiManager.ApiManager
import com.example.shahicripto.apiManager.model.CoinAboutData
import com.example.shahicripto.apiManager.model.CoinAboutItem
import com.example.shahicripto.apiManager.model.CoinsData
import com.example.shahicripto.databinding.ActivityMarketBinding
import com.example.shahicripto.features.coinActivity.CoinActivity
import com.google.gson.Gson

class MarketActivity : AppCompatActivity(), MarketAdapter.RecyclerCallback {
    lateinit var binding: ActivityMarketBinding
    val apiManager = ApiManager()
    lateinit var dataNews: ArrayList<Pair<String, String>>
    lateinit var aboutDataMap : MutableMap<String , CoinAboutItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMarketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.layoutWatchlist.btnShowMore.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://coinmarketcap.com/"))
            startActivity(intent)
        }

        binding.swipeRefreshMain.setOnRefreshListener {
            initUi()
            Handler(Looper.getMainLooper()).postDelayed({
                binding.swipeRefreshMain.isRefreshing = false
            }, 1500)
        }

        getAboutDataFromAssest()

        binding.layoutToolbar.info.setOnClickListener {
            val dialog = SweetAlertDialog(this , SweetAlertDialog.NORMAL_TYPE)
            dialog.titleText = "About developer"
            dialog.contentText = "Amirreza Shahivand \n gmail : shahivandamirreza@gmail.com \n github : AmirrezaShahivand"
            dialog.confirmText = "OK :)"
            dialog.show()

            dialog.setConfirmClickListener {
                dialog.dismiss()
            }
        }


    }

    private fun internetChecker(context: Context){
        if (NetworkChecker(context).isInternetConnected){

        }else{
            Toast.makeText(this, "اینترنت خود را متصل نمایید!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getAboutDataFromAssest() {

        val fileInString = applicationContext.assets
            .open("currencyinfo.json")
            .bufferedReader()
            .use { it.readText() }

        aboutDataMap = mutableMapOf<String, CoinAboutItem>()

        val gson = Gson()
        val dataAboutAll = gson.fromJson(fileInString , CoinAboutData::class.java)

        dataAboutAll.forEach{
            aboutDataMap[it.currencyName] = CoinAboutItem(
                it.info.web ,
                it.info.github ,
                it.info.twt ,
                it.info.desc ,
                it.info.reddit
            )
        }
    }

    override fun onResume() {
        super.onResume()
        initUi()
    }

    private fun initUi() {

        getNewsFromApi()
        getTopCoinsFromApi()

    }

    private fun getNewsFromApi() {

        apiManager.getNews(object : ApiManager.ApiCallback<ArrayList<Pair<String, String>>> {
            override fun onSuccess(data: ArrayList<Pair<String, String>>) {
                dataNews = data
                refreshNews()
            }

            override fun onError(errorMessage: String) {
                internetChecker(binding.root.context)
            }


        })

    }

    private fun refreshNews() {
        val randomAccess = (0..49).random()
        binding.layoutNews.txtNews.text = dataNews[randomAccess].first
        binding.layoutNews.imgNews.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(dataNews[randomAccess].second))
            startActivity(intent)
        }
        binding.layoutNews.txtNews.setOnClickListener {
            refreshNews()
        }
    }


    private fun getTopCoinsFromApi() {
        apiManager.getCoinsList(object : ApiManager.ApiCallback<List<CoinsData.Data>> {

            override fun onSuccess(data: List<CoinsData.Data>) {
                showDataINRecycler(data)
            }

            override fun onError(errorMessage: String) {
            }

        })
    }

    private fun showDataINRecycler(data: List<CoinsData.Data>) {

        val marketAdapter = MarketAdapter(ArrayList(data), this)

        binding.layoutWatchlist.recyclerMarket.adapter = marketAdapter
        binding.layoutWatchlist.recyclerMarket.layoutManager = LinearLayoutManager(this)

    }

    override fun onItemClicked(dataCoin: CoinsData.Data) {

        val intent = Intent(this, CoinActivity::class.java)
//        intent.putExtra("sendToData", dataCoin)
        val bundle = Bundle()
        bundle.putParcelable("bundle1" , dataCoin)
        bundle.putParcelable("bundle2" , aboutDataMap[dataCoin.coinInfo.name])
        intent.putExtra("bundle" , bundle)

        startActivity(intent)
    }


}