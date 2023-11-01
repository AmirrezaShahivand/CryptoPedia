package com.example.shahicripto.features.marketScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shahicripto.model.MainRepository
import com.example.shahicripto.model.local.CoinsData.CoinsDataEntitity
import com.example.shahicripto.model.local.NewsData.NewsData
import io.reactivex.CompletableObserver
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MarketScreenViewModel(private val mainRepository: MainRepository) : ViewModel() {
    private lateinit var netDisposable: Disposable
    private val errorData = MutableLiveData<String>()
    fun getTopCoinsFromDataBase(): LiveData<List<CoinsDataEntitity>> {
        return mainRepository.getCoinsList()
    }

    fun getTopNewsFromApi(): Single<NewsData> {
        return mainRepository.getNews()
    }

    fun getErrorData() : LiveData<String>{
        return errorData
    }

    fun refreshData() {
        return mainRepository
            .refreshData()
            .subscribeOn(Schedulers.io())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    netDisposable=d
                }

                override fun onComplete() {

                }

                override fun onError(e: Throwable) {
                    errorData.postValue(e.message ?: "unknown error")
                }

            })
    }


    init {
        mainRepository
            .refreshData()
            .subscribeOn(Schedulers.io())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    netDisposable=d
                }

                override fun onComplete() {

                }

                override fun onError(e: Throwable) {
                    errorData.postValue(e.message ?: "unknown error")
                }

            })
    }


}