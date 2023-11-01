package com.example.shahicripto.features.coinScreen

import com.example.shahicripto.model.local.ChartData
import com.example.shahicripto.model.MainRepository
import io.reactivex.Single

class ChartScreenViewModel(private val mainRepository: MainRepository)  {

    fun getChartCoinFromApi(symbol : String , period : String ):Single<ChartData>{
        return mainRepository.getChartData(symbol , period)
    }
}