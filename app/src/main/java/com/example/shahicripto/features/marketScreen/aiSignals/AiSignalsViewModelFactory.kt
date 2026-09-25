package com.example.shahicripto.features.marketScreen.aiSignals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shahicripto.model.MainRepository
import com.example.shahicripto.util.UserSubscriptionManager

class AiSignalsViewModelFactory(
    private val repository: MainRepository,
    private val subscriptionManager: UserSubscriptionManager
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AiSignalsViewModel::class.java)) {
            return AiSignalsViewModel(repository, subscriptionManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
