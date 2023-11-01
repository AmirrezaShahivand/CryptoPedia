package com.example.shahicripto.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shahicripto.features.marketScreen.MarketScreenViewModel
import com.example.shahicripto.model.MainRepository

class MarketViewModelFactory(private val mainRepository: MainRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MarketScreenViewModel(mainRepository) as T
    }
}