package com.example.shahicripto.features.marketScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shahicripto.model.MainRepository
import com.example.shahicripto.model.local.CoinsData.CoinsDataEntitity
import com.example.shahicripto.model.local.NewsData.NewsDataEntity
import io.reactivex.rxjava3.core.CompletableObserver
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import com.example.shahicripto.util.userFacingMessage

class MarketScreenViewModel(private val mainRepository: MainRepository) : ViewModel() {
    private val disposables = CompositeDisposable()
    private val errorData = MutableLiveData<String>()
    fun getTopCoinsFromDataBase(): LiveData<List<CoinsDataEntitity>> {
        return mainRepository.getCoinsList()
    }

    fun getTopNewsFromDataBase(): LiveData<List<NewsDataEntity>> {
        return mainRepository.getNews()
    }

    fun getErrorData(): LiveData<String> {
        return errorData
    }

    fun refreshData() {
         mainRepository
            .refreshData()
            .subscribeOn(Schedulers.io())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    disposables.add(d)
                }

                override fun onComplete() {

                }

                override fun onError(e: Throwable) {
                    errorData.postValue(e.userFacingMessage())
                }

            })


    }

    fun refreshNews(){
        mainRepository
            .refreshDataNews()
            .subscribeOn(Schedulers.io())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    disposables.add(d)
                }

                override fun onComplete() {
                }

                override fun onError(e: Throwable) {
                    errorData.postValue(e.userFacingMessage())
                }

            })
    }


    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }


}
