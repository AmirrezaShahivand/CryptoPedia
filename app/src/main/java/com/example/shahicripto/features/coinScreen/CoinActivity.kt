package com.example.shahicripto.features.coinScreen

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.shahicripto.R
import com.example.shahicripto.util.ALL
import com.example.shahicripto.util.HOUR
import com.example.shahicripto.util.HOURS24
import com.example.shahicripto.util.MONTH
import com.example.shahicripto.util.MONTH3
import com.example.shahicripto.util.WEEK
import com.example.shahicripto.util.YEAR
import com.example.shahicripto.model.local.ChartData
import com.example.shahicripto.model.local.CoinAboutItem
import com.example.shahicripto.model.MainRepository
import com.example.shahicripto.databinding.ActivityCoinBinding
import com.example.shahicripto.model.MyDatabase
import com.example.shahicripto.model.api.ApiService
import com.example.shahicripto.model.local.CoinsData.CoinsDataEntitity
import com.example.shahicripto.util.ChartViewModelFactory
import com.example.shahicripto.util.X
import com.example.shahicripto.util.asyncRequest
import com.example.shahicripto.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

@AndroidEntryPoint
class CoinActivity : AppCompatActivity() {
    lateinit var binding: ActivityCoinBinding
    lateinit var dataThisCoin: CoinsDataEntitity
    lateinit var dataThisCoinAbout: CoinAboutItem
    lateinit var chartScreenViewModel: ChartScreenViewModel
    @Inject
    lateinit var apiService: ApiService
    val compositeDisposable = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoinBinding.inflate(layoutInflater)
        setContentView(binding.root)
        chartScreenViewModel = ViewModelProvider(
            this,
            ChartViewModelFactory(
                MainRepository(
                    apiService,
                    MyDatabase.getDatabase(applicationContext).coinsDataDao,
                    MyDatabase.getDatabase(applicationContext).newsDataDao
                )
            )
        )[ChartScreenViewModel::class.java]

        binding.swipeRefreshMain.setOnRefreshListener {
            initChartUi()
            Handler(Looper.getMainLooper()).postDelayed({
                binding.swipeRefreshMain.isRefreshing = false
            }, 1500)
        }


//        dataThisCoin = intent.getParcelableExtra<CoinsData.Data>("sendToData")!!
        val fromIntent = intent.getBundleExtra("bundle")!!
        dataThisCoin = fromIntent.getParcelable("bundle1" )!!
        if (fromIntent.getParcelable<CoinAboutItem>("bundle2") != null) {
            dataThisCoinAbout = fromIntent.getParcelable("bundle2")!!
        } else {
            dataThisCoinAbout = CoinAboutItem()
        }
        binding.layoutToolbar.toolbar.title = dataThisCoin.fullName

        initUi()

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

    private fun initUi() {
        initChartUi()
        initStatisticsUi()
        initAboutUi()
    }

    private fun initAboutUi() {
        val aboutItem = CoinAboutItem()




        binding.about.txtSomeData.text = dataThisCoinAbout.coinDesc


        if (dataThisCoinAbout.coinWebsite == "") {
            binding.about.txtWebsite.text = "no-data"
        } else {
            binding.about.txtWebsite.text = dataThisCoinAbout.coinWebsite
        }

        if (dataThisCoinAbout.coinGithub == "") {
            binding.about.txtGithub.text = "no-data"
        } else {
            binding.about.txtGithub.text = dataThisCoinAbout.coinGithub
        }

        if (dataThisCoinAbout.coinReddit == "" ) {
            binding.about.txtReddit.text = "no-data"
        } else {
            binding.about.txtReddit.text = dataThisCoinAbout.coinReddit
        }

        if (dataThisCoinAbout.coinX == aboutItem.coinX.toString() || dataThisCoinAbout.coinX == "") {
            binding.about.txtX.text = "no_data"
        } else {
            binding.about.txtX.text = "@" + dataThisCoinAbout.coinX
        }






        binding.about.txtWebsite.setOnClickListener {

            if (dataThisCoinAbout.coinWebsite == aboutItem.coinWebsite.toString() || dataThisCoinAbout.coinWebsite == "") {

            } else {
                openWebsiteDataCoin(dataThisCoinAbout.coinWebsite!!)
            }


        }

        binding.about.txtGithub.setOnClickListener {
            if (dataThisCoinAbout.coinGithub == aboutItem.coinGithub.toString() || dataThisCoinAbout.coinGithub == "") {

            } else {
                openWebsiteDataCoin(dataThisCoinAbout.coinGithub!!)

            }

        }

        binding.about.txtReddit.setOnClickListener {

            if (dataThisCoinAbout.coinReddit == aboutItem.coinReddit.toString() || dataThisCoinAbout.coinReddit == "") {

            } else {
                openWebsiteDataCoin(dataThisCoinAbout.coinReddit!!)

            }

        }

        binding.about.txtX.setOnClickListener {

            if (dataThisCoinAbout.coinX == aboutItem.coinX.toString() || dataThisCoinAbout.coinX == "") {

            } else {
                openWebsiteDataCoin(X + dataThisCoinAbout.coinX!!)

            }


        }

    }

    private fun openWebsiteDataCoin(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }


    private fun initStatisticsUi() {

        binding.statistics.txtStatisticsOpenPrice.text = dataThisCoin.oPEN24HOUR
        binding.statistics.txtStatisticsHigh.text = dataThisCoin.hIGH24HOUR
        binding.statistics.txtStatisticsLow.text = dataThisCoin.lOW24HOUR
        binding.statistics.txtStatisticsChange.text = dataThisCoin.cHANGE24HOUR
        binding.statistics.txtStatisticsAlgoritm.text = dataThisCoin.algorithm
        binding.statistics.txtStatisticsTotalVolume.text = dataThisCoin.tOTALVOLUME24H
        binding.statistics.txtStatisticsMarketCap.text = dataThisCoin.mKTCAP
        binding.statistics.txtStatisticsSupply.text = dataThisCoin.sUPPLY

    }

    @SuppressLint("SetTextI18n")
    private fun initChartUi() {

        var period: String = HOUR
        requestAndShowChart(period)
        binding.chart.radioGroupMain.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radio_12h -> {
                    period = HOUR
                }

                R.id.radio_1d -> {
                    period = HOURS24
                }

                R.id.radio_1w -> {
                    period = WEEK
                }

                R.id.radio_1month -> {
                    period = MONTH
                }

                R.id.radio_3month -> {
                    period = MONTH3
                }

                R.id.radio_1years -> {
                    period = YEAR
                }

                R.id.radio_all -> {
                    period = ALL
                }
            }
            requestAndShowChart(period)


        }

        binding.chart.txtChartPrice.text = dataThisCoin.price
        binding.chart.txtChartChange1.text = dataThisCoin.cHANGE24HOUR
        binding.chart.txtChartChange2.text = dataThisCoin.cHANGEPCT24HOUR + "%"
        val taghir = dataThisCoin.cHANGE24HOUR_RAW
        if (taghir > 0) {
            binding.chart.txtChartChange2.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.colorGain
                )
            )
            binding.chart.txtUpdown.text = "▲"
            binding.chart.txtUpdown.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.colorGain
                )
            )
            binding.chart.txtChartPrice.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.colorGain
                )
            )
            binding.chart.sparkView.lineColor = ContextCompat.getColor(
                binding.root.context,
                R.color.colorGain
            )

        } else if (taghir < 0) {
            binding.chart.txtChartChange2.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.colorLoss
                )
            )
            binding.chart.txtUpdown.text = "▼"
            binding.chart.txtUpdown.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.colorLoss
                )
            )

            binding.chart.txtChartPrice.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.colorLoss
                )
            )
            binding.chart.sparkView.lineColor = ContextCompat.getColor(
                binding.root.context,
                R.color.colorLoss
            )
        } else {
            binding.chart.txtChartChange2.text = "0%"
            binding.chart.txtUpdown.text = "▲"
            binding.chart.txtChartPrice.text = dataThisCoin.price
        }

        binding.chart.sparkView.setScrubListener {
            //show price kamel
            if (it == null) {
                binding.chart.txtChartPrice.text = dataThisCoin.price
            } else {
                binding.chart.txtChartPrice.text = (it as ChartData.Data).close.toString()
            }

        }
    }

    fun requestAndShowChart(period: String) {

        chartScreenViewModel
            .getChartCoinFromApi(dataThisCoin.name, period)
            .asyncRequest()
            .subscribe(object : SingleObserver<ChartData> {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onError(e: Throwable) {
//                    onBackPressed()
                    showToast("لطفا اینترنت خود را متصل نمایید")
                }

                override fun onSuccess(t: ChartData) {
                    t.data.forEach {
                        val dataOpen = it.open.toString()
                        val chartAdapter = ChartAdapter(t.data, dataOpen)
                        binding.chart.sparkView.adapter = chartAdapter
                    }


                }

            })

    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}