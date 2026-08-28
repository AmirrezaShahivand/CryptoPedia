package com.example.shahicripto.features.coinScreen

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.res.Configuration
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import androidx.appcompat.app.AppCompatDelegate
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
import com.example.shahicripto.util.NewsTranslator
import com.example.shahicripto.util.showToast
import com.example.shahicripto.util.userFacingMessage
import com.example.shahicripto.util.formatCryptoPrice
import com.example.shahicripto.util.formatCryptoPriceText
import com.example.shahicripto.util.formatGroupedNumber
import com.example.shahicripto.util.formatGroupedNumberText
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle
import javax.inject.Inject

@AndroidEntryPoint
class CoinActivity : AppCompatActivity() {
    lateinit var binding: ActivityCoinBinding
    lateinit var dataThisCoin: CoinsDataEntitity
    lateinit var dataThisCoinAbout: CoinAboutItem
    lateinit var chartScreenViewModel: ChartScreenViewModel

    @Inject
    lateinit var apiService: ApiService
    private var chartDisposable: Disposable? = null
    private var detailsDisposable: Disposable? = null
    private var ohlcDisposable: Disposable? = null
    private var descriptionTranslationDisposable: Disposable? = null
    private var descriptionTranslator: NewsTranslator? = null
    private var displayedChartData: List<ChartData.Data> = emptyList()
    private var selectedPeriod = HOUR


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
                    MyDatabase.getDatabase(applicationContext).newsDataDao,
                    MyDatabase.getDatabase(applicationContext).coinCatalogDao,
                    MyDatabase.getDatabase(applicationContext).priceSnapshotDao
                )
            )
        )[ChartScreenViewModel::class.java]

        binding.swipeRefreshMain.setOnRefreshListener {
            requestAndShowChart(selectedPeriod)
            Handler(Looper.getMainLooper()).postDelayed({
                binding.swipeRefreshMain.isRefreshing = false
            }, 1500)
        }


//        dataThisCoin = intent.getParcelableExtra<CoinsData.Data>("sendToData")!!
        val fromIntent = intent.getBundleExtra("bundle")
        val coin = fromIntent?.getSerializable("bundle1") as? CoinsDataEntitity
        if (coin == null) {
            finish()
            return
        }
        dataThisCoin = coin
        dataThisCoinAbout = fromIntent?.getSerializable("bundle2") as? CoinAboutItem ?: CoinAboutItem()
        binding.layoutToolbar.toolbar.title = dataThisCoin.fullName

        initUi()

        binding.layoutToolbar.info.setOnClickListener {
            val dialog = SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
            dialog.titleText = getString(R.string.about_developer)
            dialog.contentText = getString(R.string.about_developer_text)
            dialog.confirmText = getString(R.string.ok)
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
        loadCoinPaprikaDetails()
        loadTodayOhlc()
    }

    private fun loadCoinPaprikaDetails() {
        detailsDisposable?.dispose()
        detailsDisposable = chartScreenViewModel
            .getCoinDetails(dataThisCoin.coinId)
            .asyncRequest()
            .subscribe({ details ->
                val fallback = dataThisCoinAbout
                dataThisCoinAbout = CoinAboutItem(
                    coinWebsite = details.links?.website?.firstOrNull { it.isNotBlank() }
                        ?: fallback.coinWebsite,
                    coinGithub = details.links?.sourceCode?.firstOrNull { it.isNotBlank() }
                        ?: fallback.coinGithub,
                    coinX = details.links?.twitter?.firstOrNull { it.isNotBlank() }
                        ?: fallback.coinX,
                    coinDesc = details.description?.takeIf { it.isNotBlank() }
                        ?: fallback.coinDesc,
                    coinReddit = details.links?.reddit?.firstOrNull { it.isNotBlank() }
                        ?: fallback.coinReddit
                )
                initAboutUi(translateDescription = true)
            }, { error ->
                initAboutUi(translateDescription = true)
                showToast(error.userFacingMessage())
            })
    }

    private fun loadTodayOhlc() {
        ohlcDisposable?.dispose()
        ohlcDisposable = chartScreenViewModel
            .getTodayOhlc(dataThisCoin.coinId)
            .asyncRequest()
            .subscribe({ values ->
                values.firstOrNull()?.let { today ->
                    binding.statistics.txtStatisticsOpenPrice.text = formatCryptoPrice(today.open)
                    binding.statistics.txtStatisticsHigh.text = formatCryptoPrice(today.high)
                    binding.statistics.txtStatisticsLow.text = formatCryptoPrice(today.low)
                    binding.statistics.txtStatisticsTotalVolume.text = formatGroupedNumber(today.volume)
                    binding.statistics.txtStatisticsMarketCap.text = formatGroupedNumber(today.marketCap)
                }
            }, { error ->
                showToast(error.userFacingMessage())
            })
    }

    private fun initAboutUi(translateDescription: Boolean = false) {
        val aboutItem = CoinAboutItem()




        val description = dataThisCoinAbout.coinDesc.orEmpty()
        binding.about.txtSomeData.text = displayValue(description)
        if (translateDescription) translateDescription(description)


        binding.about.txtWebsite.text = displayValue(dataThisCoinAbout.coinWebsite)

        binding.about.txtGithub.text = displayValue(dataThisCoinAbout.coinGithub)

        binding.about.txtReddit.text = displayValue(dataThisCoinAbout.coinReddit)

        if (dataThisCoinAbout.coinX == aboutItem.coinX.toString() || dataThisCoinAbout.coinX.isNullOrBlank() || dataThisCoinAbout.coinX == "no-data") {
            binding.about.txtX.text = getString(R.string.not_available)
        } else {
            binding.about.txtX.text = "@" + dataThisCoinAbout.coinX
        }






        binding.about.txtWebsite.setOnClickListener {
            dataThisCoinAbout.coinWebsite?.takeIf { it.isNotBlank() && it != aboutItem.coinWebsite }?.let(::openWebsiteDataCoin)
        }

        binding.about.txtGithub.setOnClickListener {
            dataThisCoinAbout.coinGithub?.takeIf { it.isNotBlank() && it != aboutItem.coinGithub }?.let(::openWebsiteDataCoin)
        }

        binding.about.txtReddit.setOnClickListener {
            dataThisCoinAbout.coinReddit?.takeIf { it.isNotBlank() && it != aboutItem.coinReddit }?.let(::openWebsiteDataCoin)
        }

        binding.about.txtX.setOnClickListener {
            dataThisCoinAbout.coinX?.takeIf { it.isNotBlank() && it != aboutItem.coinX }?.let { openWebsiteDataCoin(X + it) }
        }
        binding.layoutToolbar.themeToggle.setOnClickListener { toggleTheme() }

    }

    private fun toggleTheme() {
        val isNight = (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
        AppCompatDelegate.setDefaultNightMode(
            if (isNight) AppCompatDelegate.MODE_NIGHT_NO else AppCompatDelegate.MODE_NIGHT_YES
        )
    }

    private fun displayValue(value: String?): String {
        return value?.takeIf {
            it.isNotBlank() && it != "no-data" && it != "no_data"
        } ?: getString(R.string.not_available)
    }

    private fun translateDescription(description: String) {
        val language = resources.configuration.locales[0].language
        if (language != NewsTranslator.PERSIAN || description.isBlank() || displayValue(description) == getString(R.string.not_available)) {
            return
        }

        descriptionTranslationDisposable?.dispose()
        if (descriptionTranslator == null) {
            descriptionTranslator = NewsTranslator(
                this,
                MyDatabase.getDatabase(applicationContext).newsTranslationDao
            )
        }
        val cacheKey = "coin_description_${dataThisCoin.coinId}_${description.hashCode()}"
        descriptionTranslationDisposable = descriptionTranslator!!
            .displayText(cacheKey, description, language)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ translated ->
                if (resources.configuration.locales[0].language == NewsTranslator.PERSIAN) {
                    binding.about.txtSomeData.text = translated
                }
            }, {
                binding.about.txtSomeData.text = description
            })
    }

    private fun openWebsiteDataCoin(url: String) {
        val uri = Uri.parse(url)
        if (uri.scheme == "http" || uri.scheme == "https") {
            val intent = Intent(Intent.ACTION_VIEW, uri)
            if (intent.resolveActivity(packageManager) != null) startActivity(intent)
        }
    }


    private fun initStatisticsUi() {

        binding.statistics.txtStatisticsOpenPrice.text = formatCryptoPriceText(dataThisCoin.oPEN24HOUR)
        binding.statistics.txtStatisticsHigh.text = formatCryptoPriceText(dataThisCoin.hIGH24HOUR)
        binding.statistics.txtStatisticsLow.text = formatCryptoPriceText(dataThisCoin.lOW24HOUR)
        binding.statistics.txtStatisticsChange.text = formatCryptoPriceText(dataThisCoin.cHANGE24HOUR)
        binding.statistics.txtStatisticsAlgoritm.text = dataThisCoin.algorithm
        binding.statistics.txtStatisticsTotalVolume.text = formatGroupedNumberText(dataThisCoin.tOTALVOLUME24H)
        binding.statistics.txtStatisticsMarketCap.text = formatGroupedNumberText(dataThisCoin.mKTCAP)
        binding.statistics.txtStatisticsSupply.text = formatGroupedNumberText(dataThisCoin.sUPPLY)

    }

    @SuppressLint("SetTextI18n")
    private fun initChartUi() {

        binding.chart.radioGroupMain.setOnCheckedChangeListener { _, checkedId ->
            selectedPeriod = when (checkedId) {
                R.id.radio_12h -> {
                    HOUR
                }

                R.id.radio_1d -> {
                    HOURS24
                }

                R.id.radio_1w -> {
                    WEEK
                }

                R.id.radio_1month -> {
                    MONTH
                }

                R.id.radio_3month -> {
                    MONTH3
                }

                R.id.radio_1years -> {
                    YEAR
                }

                R.id.radio_all -> {
                    ALL
                }
                else -> HOUR
            }
            requestAndShowChart(selectedPeriod)


        }

        binding.chart.txtChartPrice.text = formatCryptoPriceText(dataThisCoin.price)
        binding.chart.txtChartChange1.text = formatCryptoPriceText(dataThisCoin.cHANGE24HOUR)
        binding.chart.txtChartChange2.text = formatGroupedNumberText(dataThisCoin.cHANGEPCT24HOUR) + "%"
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
            binding.chart.txtChartPrice.text = formatCryptoPriceText(dataThisCoin.price)
        }

        binding.chart.sparkView.setScrubListener {
            val point = it as? ChartData.Data
            if (point == null) {
                binding.chart.txtChartPrice.text = formatCryptoPriceText(dataThisCoin.price)
                displayedChartData.lastOrNull()?.let { last ->
                    binding.chart.txtChartDate.text = getString(R.string.chart_date, formatChartDate(last))
                }
            } else {
                binding.chart.txtChartPrice.text = formatCryptoPrice(point.close)
                binding.chart.txtChartDate.text = getString(R.string.chart_date, formatChartDate(point))
            }

        }
        requestAndShowChart(selectedPeriod)
    }

    fun requestAndShowChart(period: String) {
        chartDisposable?.dispose()
        chartScreenViewModel
            .getChartCoinFromApi(dataThisCoin.coinId, dataThisCoin.name, period)
            .asyncRequest()
            .subscribe(object : SingleObserver<ChartData> {
                override fun onSubscribe(d: Disposable) {
                    chartDisposable = d
                }

                override fun onError(e: Throwable) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        binding.chart.textView5.visibility = View.VISIBLE
                        binding.chart.textView5.text = e.userFacingMessage()
                        MotionToast.darkColorToast(
                            this@CoinActivity, "Data error",
                            e.userFacingMessage(),
                            MotionToastStyle.ERROR,
                            MotionToast.GRAVITY_BOTTOM,
                            MotionToast.LONG_DURATION,
                            ResourcesCompat.getFont(
                                this@CoinActivity,
                                www.sanju.motiontoast.R.font.helvetica_regular
                            )
                        )

                    }, 1000)

                }

                override fun onSuccess(t: ChartData) {
                    if (t.data.isNotEmpty()) {
                        displayedChartData = t.data
                        binding.chart.textView5.visibility = View.GONE
                        val chartAdapter = ChartAdapter(t.data, t.data.first().open.toString())
                        binding.chart.sparkView.adapter = chartAdapter
                        binding.chart.txtChartDate.text = getString(
                            R.string.chart_date,
                            formatChartDate(t.data.last())
                        )
                    } else {
                        binding.chart.textView5.visibility = View.VISIBLE
                        binding.chart.textView5.text = "برای این بازه داده‌ای وجود ندارد"
                    }


                }

            })

    }

    private fun formatChartDate(point: ChartData.Data): String {
        val locale = if (resources.configuration.locales[0].language == "fa") {
            Locale("fa", "IR")
        } else {
            Locale.US
        }
        return SimpleDateFormat("yyyy/MM/dd HH:mm", locale).format(Date(point.time * 1000L))
    }

    override fun onDestroy() {
        chartDisposable?.dispose()
        detailsDisposable?.dispose()
        ohlcDisposable?.dispose()
        descriptionTranslationDisposable?.dispose()
        descriptionTranslator?.close()
        super.onDestroy()
    }
}
