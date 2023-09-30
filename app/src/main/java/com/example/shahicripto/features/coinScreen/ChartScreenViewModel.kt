package com.example.shahicripto.features.coinScreen

import com.example.shahicripto.apiManager.model.ChartData
import com.example.shahicripto.apiManager.model.MainRepository
import io.reactivex.Single
import io.reactivex.SingleObserver

class ChartScreenViewModel(private val mainRepository: MainRepository)  {

    fun getChartCoinFromApi(symbol : String , period : String ):Single<ChartData>{
        return mainRepository.getChartData(symbol , period)
    }
}