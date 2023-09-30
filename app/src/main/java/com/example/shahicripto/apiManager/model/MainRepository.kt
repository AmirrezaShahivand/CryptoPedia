package com.example.shahicripto.apiManager.model

import com.example.shahicripto.util.ALL
import com.example.shahicripto.util.BASE_URL
import com.example.shahicripto.util.HISTO_DAY
import com.example.shahicripto.util.HISTO_HOUR
import com.example.shahicripto.util.HISTO_MINUTE
import com.example.shahicripto.util.HOUR
import com.example.shahicripto.util.HOURS24
import com.example.shahicripto.util.MONTH
import com.example.shahicripto.util.MONTH3
import com.example.shahicripto.util.WEEK
import com.example.shahicripto.util.YEAR
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

lateinit var disposable: Disposable

class MainRepository() {
    private val apiService: ApiService
    lateinit var dispos: Disposable


    init {

        val retrofit = Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)

    }


    fun getNews(): Single<NewsData> {

        return apiService.getTopNews()

    }

    fun getCoinsList(): Single<CoinsData> {

        return apiService.getTopCoins()

    }

    fun getChartData(
        symbol: String,
        period: String,

        ): Single<ChartData> {

        var histoPeriod = ""
        var limit = 30
        var aggregate = 1

        when (period) {

            HOUR -> {
                histoPeriod = HISTO_MINUTE
                limit = 60
                aggregate = 12
            }

            HOURS24 -> {
                histoPeriod = HISTO_HOUR
                limit = 24
            }

            MONTH -> {
                histoPeriod = HISTO_DAY
                limit = 30
            }

            MONTH3 -> {
                histoPeriod = HISTO_DAY
                limit = 90
            }

            WEEK -> {
                histoPeriod = HISTO_HOUR
                aggregate = 6
            }

            YEAR -> {
                histoPeriod = HISTO_DAY
                aggregate = 13
            }

            ALL -> {
                histoPeriod = HISTO_DAY
                aggregate = 30
                limit = 2000
            }

        }

        return apiService.getChartData(histoPeriod, symbol, limit, aggregate)


    }

    interface ApiCallback<T> {

        fun onSuccess(data: T)
        fun onError(errorMessage: String)

    }

}