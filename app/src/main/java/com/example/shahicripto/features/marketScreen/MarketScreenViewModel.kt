package com.example.shahicripto.features.marketScreen

import com.example.shahicripto.apiManager.model.CoinsData
import com.example.shahicripto.apiManager.model.MainRepository
import com.example.shahicripto.apiManager.model.NewsData
import com.example.shahicripto.util.asyncRequest
import io.reactivex.Single

class MarketScreenViewModel(private val mainRepository: MainRepository) {

    fun getTopCoinsFromApi(): Single<CoinsData> {


        return mainRepository.getCoinsList()
    }

    fun getTopNewsFromApi() : Single<NewsData> {
        return mainRepository.getNews()
    }

}