package com.example.shahicripto.features.coinScreen

import androidx.lifecycle.ViewModel
import com.example.shahicripto.model.local.ChartData
import com.example.shahicripto.model.MainRepository
import io.reactivex.rxjava3.core.Single

class ChartScreenViewModel(private val mainRepository: MainRepository) : ViewModel()  {

    fun getChartCoinFromApi(coinId: String, symbol: String, period: String): Single<ChartData> {
        return mainRepository.getChartData(coinId, symbol, period)
    }

    fun getCoinDetails(coinId: String) = mainRepository.getCoinDetails(coinId)

    fun getTodayOhlc(coinId: String) = mainRepository.getTodayOhlc(coinId)
}
