package com.example.shahicripto.model

import androidx.lifecycle.LiveData
import com.example.shahicripto.model.api.ApiService
import com.example.shahicripto.model.local.ChartData
import com.example.shahicripto.model.local.CoinsData.CoinsDataDao
import com.example.shahicripto.model.local.CoinsData.CoinsDataEntitity
import com.example.shahicripto.model.local.NewsData.NewsDataDao
import com.example.shahicripto.model.local.NewsData.NewsDataEntity
import com.example.shahicripto.util.ALL
import com.example.shahicripto.util.HISTO_DAY
import com.example.shahicripto.util.HISTO_HOUR
import com.example.shahicripto.util.HISTO_MINUTE
import com.example.shahicripto.util.HOUR
import com.example.shahicripto.util.HOURS24
import com.example.shahicripto.util.MONTH
import com.example.shahicripto.util.MONTH3
import com.example.shahicripto.util.WEEK
import com.example.shahicripto.util.YEAR
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.disposables.Disposable

class MainRepository(
    private val apiService: ApiService,
    private val coinsDataDao: CoinsDataDao,
    private val newsDataDao: NewsDataDao
) {


    fun getCoinsList(): LiveData<List<CoinsDataEntitity>> {
        return coinsDataDao.getAllCoins()
    }

    fun refreshData(): Completable {
        return apiService
            .getTopCoins()
            .doOnSuccess {
                it.data.forEach {
                    coinsDataDao.insertAll(
                        CoinsDataEntitity(
                            it.coinInfo.name,
                            it.dISPLAY.uSDT.pRICE,
                            it.rAW.uSDT.cHANGEPCT24HOUR,
                            it.rAW.uSDT.mKTCAP,
                            it.coinInfo.imageUrl,
                            it.dISPLAY.uSDT.oPEN24HOUR,
                            it.dISPLAY.uSDT.hIGH24HOUR,
                            it.dISPLAY.uSDT.lOW24HOUR,
                            it.dISPLAY.uSDT.cHANGE24HOUR,
                            it.coinInfo.algorithm,
                            it.dISPLAY.uSDT.tOTALVOLUME24H,
                            it.dISPLAY.uSDT.mKTCAP,
                            it.dISPLAY.uSDT.sUPPLY,
                            it.coinInfo.fullName,
                            it.dISPLAY.uSDT.cHANGEPCT24HOUR,
                            it.rAW.uSDT.cHANGE24HOUR
                        )
                    )
                }
            }.ignoreElement()
    }

    fun refreshDataNews(): Completable {
        return apiService
            .getTopNews()
            .doOnSuccess {
                it.data.forEach {
                    newsDataDao.insertAll(NewsDataEntity(it.title, it.url , it.imageurl , it.body))
                }
            }.ignoreElement()
    }


    fun getNews(): LiveData<List<NewsDataEntity>> {

        return newsDataDao.getAllNews()

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


}