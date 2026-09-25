package com.example.shahicripto.features.marketScreen.aiSignals

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shahicripto.model.MainRepository
import com.example.shahicripto.model.api.GeminiSignalService
import com.example.shahicripto.model.api.RemoteConfigProvider
import com.example.shahicripto.model.local.AiEngine
import com.example.shahicripto.model.local.AiSignalResult
import com.example.shahicripto.model.local.TimeframeOption
import com.example.shahicripto.model.local.CoinsData.CoinsDataEntitity
import com.example.shahicripto.util.SubscriptionType
import com.example.shahicripto.util.UserSubscriptionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class AiSignalsViewModel(
    private val repository: MainRepository,
    private val subscriptionManager: UserSubscriptionManager,
    private val signalService: GeminiSignalService = GeminiSignalService()
) : ViewModel() {

    private val viewModelJob = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _isVipActive = MutableLiveData<Boolean>()
    val isVipActive: LiveData<Boolean> get() = _isVipActive

    private val _subscriptionType = MutableLiveData<SubscriptionType>()
    val subscriptionType: LiveData<SubscriptionType> get() = _subscriptionType

    private val _dailyQuotaUsed = MutableLiveData<Int>()
    val dailyQuotaUsed: LiveData<Int> get() = _dailyQuotaUsed

    private val _dailyQuotaRemaining = MutableLiveData<Int>()
    val dailyQuotaRemaining: LiveData<Int> get() = _dailyQuotaRemaining

    private val _selectedCoin = MutableLiveData<CoinsDataEntitity?>()
    val selectedCoin: LiveData<CoinsDataEntitity?> get() = _selectedCoin

    private val _selectedTimeframe = MutableLiveData(TimeframeOption.ONE_DAY)
    val selectedTimeframe: LiveData<TimeframeOption> get() = _selectedTimeframe

    private val _selectedEngine = MutableLiveData(AiEngine.GEMINI)
    val selectedEngine: LiveData<AiEngine> get() = _selectedEngine

    private val _signalResult = MutableLiveData<AiSignalResult?>()
    val signalResult: LiveData<AiSignalResult?> get() = _signalResult

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    private val _quotaExceededEvent = MutableLiveData<Boolean>()
    val quotaExceededEvent: LiveData<Boolean> get() = _quotaExceededEvent

    val coinsList: LiveData<List<CoinsDataEntitity>> = repository.getCoinsList()

    init {
        checkSubscriptionStatus()
    }

    fun checkSubscriptionStatus() {
        _isVipActive.value = subscriptionManager.hasActiveAccess()
        _subscriptionType.value = subscriptionManager.getSubscriptionType()
        updateQuotaState()
    }

    private fun updateQuotaState() {
        _dailyQuotaUsed.value = subscriptionManager.getTodayUsedQuota()
        _dailyQuotaRemaining.value = subscriptionManager.getRemainingDailyQuota()
    }

    fun getUserName(): String = subscriptionManager.getUserName()

    fun getUserPhone(): String = subscriptionManager.getUserPhone()

    fun getPlanName(): String = subscriptionManager.getPlanName()

    fun isTrialUser(): Boolean = subscriptionManager.isTrialUser()

    fun getRemainingTrialDays(): Int = subscriptionManager.getRemainingTrialDays()

    /**
     * Start 3-day Free Trial with phone and name
     */
    fun startFreeTrial(name: String, phone: String, email: String = "") {
        subscriptionManager.startFreeTrial(name, phone, email)
        checkSubscriptionStatus()
        _selectedCoin.value?.let { runAnalysis(it) }
    }

    fun activateSubscription(
        name: String,
        phone: String,
        email: String,
        plan: String,
        amountRials: Long,
        gateway: String,
        refId: String,
        invoiceId: String
    ) {
        subscriptionManager.registerAndSubscribe(
            name = name,
            phone = phone,
            email = email,
            plan = plan,
            amountRials = amountRials,
            gateway = gateway,
            refId = refId,
            invoiceId = invoiceId
        )
        checkSubscriptionStatus()
        _selectedCoin.value?.let { runAnalysis(it) }
    }

    fun getReceipt() = subscriptionManager.getReceipt()

    fun signOut() {
        subscriptionManager.signOut()
        checkSubscriptionStatus()
    }

    fun syncRemoteConfig(context: Context) {
        scope.launch {
            val url = subscriptionManager.getRemoteConfigUrl()
            RemoteConfigProvider.fetchRemoteConfig(context, url)
        }
    }

    fun selectCoin(coin: CoinsDataEntitity, isFa: Boolean = false) {
        _selectedCoin.value = coin
        runAnalysis(coin, isFa = isFa)
    }

    fun setTimeframe(timeframe: TimeframeOption, isFa: Boolean = false) {
        _selectedTimeframe.value = timeframe
        _selectedCoin.value?.let { runAnalysis(it, isFa = isFa) }
    }

    fun setEngine(engine: AiEngine, isFa: Boolean = false) {
        _selectedEngine.value = engine
        _selectedCoin.value?.let { runAnalysis(it, isFa = isFa) }
    }

    fun reanalyze(isFa: Boolean = false) {
        _selectedCoin.value?.let { runAnalysis(it, isFa = isFa) }
    }

    fun runAnalysis(coin: CoinsDataEntitity, isFa: Boolean = false) {
        if (!subscriptionManager.hasActiveAccess()) {
            _isVipActive.value = false
            return
        }

        // Check 20 analyses per day quota
        if (!subscriptionManager.canPerformAnalysis()) {
            _quotaExceededEvent.value = true
            updateQuotaState()
            return
        }

        val tf = _selectedTimeframe.value ?: TimeframeOption.ONE_DAY
        val engine = _selectedEngine.value ?: AiEngine.GEMINI

        _isLoading.value = true
        _errorMessage.value = null

        scope.launch {
            try {
                val result = signalService.analyzeCoin(
                    coin = coin,
                    timeframe = tf,
                    engine = engine,
                    isFa = isFa
                )
                // Successfully analyzed: deduct 1 from daily quota
                subscriptionManager.incrementDailyQuota()
                updateQuotaState()
                _signalResult.value = result
            } catch (e: Exception) {
                _errorMessage.value = e.localizedMessage ?: "Failed to generate AI signal"
            } finally {
                _isLoading.value = false
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
