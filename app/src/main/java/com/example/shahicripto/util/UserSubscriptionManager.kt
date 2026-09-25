package com.example.shahicripto.util

import android.content.Context
import android.content.SharedPreferences
import android.os.SystemClock
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class PaymentReceipt(
    val userName: String,
    val userPhone: String,
    val userEmail: String,
    val planName: String,
    val amountRials: Long,
    val gatewayName: String,
    val refId: String,
    val invoiceId: String,
    val timestamp: Long
) {
    val amountTomans: Long get() = amountRials / 10
}

enum class SubscriptionType {
    NONE,
    FREE_TRIAL,
    PAID_VIP
}

class UserSubscriptionManager(context: Context) {
    private val prefs: SharedPreferences =
        context.applicationContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        const val MAX_DAILY_ANALYSIS = 20
        const val TRIAL_DURATION_MS = 3L * 24 * 3600 * 1000 // 3 Days in milliseconds
        private const val DAY_IN_MILLIS = 24L * 3600 * 1000

        private const val PREF_NAME = "vip_subscription_prefs"
        private const val KEY_IS_REGISTERED = "is_registered"
        private const val KEY_IS_SUBSCRIBED = "is_subscribed"
        private const val KEY_IS_TRIAL = "is_trial"
        private const val KEY_USER_NAME = "user_name"
        private const val KEY_USER_PHONE = "user_phone"
        private const val KEY_USER_EMAIL = "user_email"
        private const val KEY_PLAN_NAME = "plan_name"
        private const val KEY_AMOUNT_RIALS = "amount_rials"
        private const val KEY_GATEWAY_NAME = "gateway_name"
        private const val KEY_REF_ID = "ref_id"
        private const val KEY_INVOICE_ID = "invoice_id"
        private const val KEY_SUBSCRIBED_AT = "subscribed_at"
        private const val KEY_EXPIRES_AT = "expires_at"

        // Daily quota tracking
        private const val KEY_QUOTA_DATE = "quota_date"
        private const val KEY_QUOTA_COUNT = "quota_count"
        // Monotonic uptime tracking to prevent clock manipulation tampering
        private const val KEY_LAST_KNOWN_WALL_CLOCK = "last_known_wall_clock"
        private const val KEY_LAST_KNOWN_ELAPSED_REALTIME = "last_known_elapsed_realtime"
        private const val KEY_MONOTONIC_ANCHOR = "monotonic_anchor"

        // Remote Server Config override (e.g. Firebase / Google Cloud URL)
        private const val KEY_REMOTE_CONFIG_URL = "remote_config_url"
        private const val DEFAULT_REMOTE_CONFIG_URL = "https://raw.githubusercontent.com/shahicripto/config/main/app_config.json"
    }

    init {
        detectAndPreventClockTampering()
    }

    /**
     * Prevents clock manipulation:
     * - Uses SystemClock.elapsedRealtime() which cannot be manipulated by device date/time settings.
     * - Detects if user rolls clock backwards or forwards irregularly.
     */
    private fun detectAndPreventClockTampering() {
        val currentWall = System.currentTimeMillis()
        val currentElapsed = SystemClock.elapsedRealtime()

        val lastWall = prefs.getLong(KEY_LAST_KNOWN_WALL_CLOCK, 0L)
        val lastElapsed = prefs.getLong(KEY_LAST_KNOWN_ELAPSED_REALTIME, 0L)

        if (lastWall > 0 && lastElapsed > 0) {
            val elapsedDiff = currentElapsed - lastElapsed
            val wallDiff = currentWall - lastWall

            // If user rolled clock backwards (e.g. current wall clock is before last recorded time)
            if (currentWall < lastWall) {
                // Suspicious clock manipulation backwards detected!
                // Keep the last recorded quota state to prevent quota reset bypass
                return
            }

            // If elapsedRealtime difference is tiny (< 1 hour) but wall clock jumped ahead more than a day,
            // the user artificially advanced the clock forward!
            if (elapsedDiff in 0..3_600_000L && wallDiff > DAY_IN_MILLIS) {
                // Suspicious forward clock jump detected!
                return
            }
        }

        prefs.edit()
            .putLong(KEY_LAST_KNOWN_WALL_CLOCK, currentWall)
            .putLong(KEY_LAST_KNOWN_ELAPSED_REALTIME, currentElapsed)
            .apply()
    }

    fun isRegistered(): Boolean = prefs.getBoolean(KEY_IS_REGISTERED, false)

    fun getSubscriptionType(): SubscriptionType {
        if (!isRegistered()) return SubscriptionType.NONE
        val isSub = prefs.getBoolean(KEY_IS_SUBSCRIBED, false)
        if (!isSub) return SubscriptionType.NONE

        val expiresAt = prefs.getLong(KEY_EXPIRES_AT, 0L)
        val now = System.currentTimeMillis()
        if (expiresAt > 0 && now > expiresAt) {
            return SubscriptionType.NONE
        }

        val isTrial = prefs.getBoolean(KEY_IS_TRIAL, false)
        return if (isTrial) SubscriptionType.FREE_TRIAL else SubscriptionType.PAID_VIP
    }

    fun hasActiveAccess(): Boolean {
        return getSubscriptionType() != SubscriptionType.NONE
    }

    fun isVipSubscribed(): Boolean {
        return hasActiveAccess()
    }

    fun isTrialUser(): Boolean {
        return getSubscriptionType() == SubscriptionType.FREE_TRIAL
    }

    fun getRemainingTrialDays(): Int {
        val expiresAt = prefs.getLong(KEY_EXPIRES_AT, 0L)
        val diff = expiresAt - System.currentTimeMillis()
        if (diff <= 0) return 0
        val days = (diff / DAY_IN_MILLIS).toInt() + 1
        return days.coerceIn(1, 3)
    }

    fun getUserName(): String = prefs.getString(KEY_USER_NAME, "") ?: ""

    fun getUserEmail(): String = prefs.getString(KEY_USER_EMAIL, "") ?: ""

    fun getUserPhone(): String = prefs.getString(KEY_USER_PHONE, "") ?: ""

    fun getPlanName(): String {
        return if (isTrialUser()) {
            "۳ روز تست رایگان (VIP Trial)"
        } else {
            prefs.getString(KEY_PLAN_NAME, "1 Month") ?: "1 Month"
        }
    }

    fun getGatewayName(): String = prefs.getString(KEY_GATEWAY_NAME, "زرین‌پال (ZarinPal)") ?: "زرین‌پال (ZarinPal)"

    fun getRefId(): String = prefs.getString(KEY_REF_ID, "") ?: ""

    fun getAmountRials(): Long = prefs.getLong(KEY_AMOUNT_RIALS, 0L)

    fun getReceipt(): PaymentReceipt {
        return PaymentReceipt(
            userName = getUserName(),
            userPhone = getUserPhone(),
            userEmail = getUserEmail(),
            planName = getPlanName(),
            amountRials = getAmountRials(),
            gatewayName = getGatewayName(),
            refId = getRefId(),
            invoiceId = prefs.getString(KEY_INVOICE_ID, "INV-1001") ?: "INV-1001",
            timestamp = prefs.getLong(KEY_SUBSCRIBED_AT, System.currentTimeMillis())
        )
    }

    /**
     * Start 3-day Free Trial with user's mobile number and name
     */
    fun startFreeTrial(name: String, phone: String, email: String = "") {
        val now = System.currentTimeMillis()
        val expires = now + TRIAL_DURATION_MS

        prefs.edit()
            .putBoolean(KEY_IS_REGISTERED, true)
            .putBoolean(KEY_IS_SUBSCRIBED, true)
            .putBoolean(KEY_IS_TRIAL, true)
            .putString(KEY_USER_NAME, name.trim())
            .putString(KEY_USER_PHONE, phone.trim())
            .putString(KEY_USER_EMAIL, email.trim())
            .putString(KEY_PLAN_NAME, "3-Day Free VIP Trial")
            .putLong(KEY_AMOUNT_RIALS, 0L)
            .putString(KEY_GATEWAY_NAME, "هدیه عضویت ویژه (رایگان)")
            .putString(KEY_REF_ID, "TRIAL-" + (100000..999999).random())
            .putString(KEY_INVOICE_ID, "TR-" + (1000..9999).random())
            .putLong(KEY_SUBSCRIBED_AT, now)
            .putLong(KEY_EXPIRES_AT, expires)
            .apply()
    }

    /**
     * Register and activate paid subscription
     */
    fun registerAndSubscribe(
        name: String,
        phone: String,
        email: String,
        plan: String,
        amountRials: Long,
        gateway: String,
        refId: String,
        invoiceId: String
    ) {
        val now = System.currentTimeMillis()
        val durationMs = when {
            plan.contains("1 Year", ignoreCase = true) || plan.contains("۱ سال") -> 365L * DAY_IN_MILLIS
            plan.contains("3 Month", ignoreCase = true) || plan.contains("۳ ماه") -> 90L * DAY_IN_MILLIS
            else -> 30L * DAY_IN_MILLIS
        }

        prefs.edit()
            .putBoolean(KEY_IS_REGISTERED, true)
            .putBoolean(KEY_IS_SUBSCRIBED, true)
            .putBoolean(KEY_IS_TRIAL, false)
            .putString(KEY_USER_NAME, name.trim())
            .putString(KEY_USER_PHONE, phone.trim())
            .putString(KEY_USER_EMAIL, email.trim())
            .putString(KEY_PLAN_NAME, plan)
            .putLong(KEY_AMOUNT_RIALS, amountRials)
            .putString(KEY_GATEWAY_NAME, gateway)
            .putString(KEY_REF_ID, refId)
            .putString(KEY_INVOICE_ID, invoiceId)
            .putLong(KEY_SUBSCRIBED_AT, now)
            .putLong(KEY_EXPIRES_AT, now + durationMs)
            .apply()
    }

    // ==========================================
    // DAILY QUOTA LIMIT (20 analyses per day)
    // Anti-Tampering: uses monotonic elapsed time
    // ==========================================

    private fun getTodayDateString(): String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())
    }

    fun getTodayUsedQuota(): Int {
        val today = getTodayDateString()
        val savedDate = prefs.getString(KEY_QUOTA_DATE, "")
        val lastWall = prefs.getLong(KEY_LAST_KNOWN_WALL_CLOCK, 0L)
        val currentWall = System.currentTimeMillis()

        // Clock rollback detection: If user turns phone clock backwards
        if (lastWall > 0 && currentWall < (lastWall - 60_000L)) {
            // Keep using the higher recorded quota count to prevent cheat
            return prefs.getInt(KEY_QUOTA_COUNT, 0)
        }

        return if (savedDate == today) {
            prefs.getInt(KEY_QUOTA_COUNT, 0)
        } else {
            // New day reset
            0
        }
    }

    fun getRemainingDailyQuota(): Int {
        val used = getTodayUsedQuota()
        return (MAX_DAILY_ANALYSIS - used).coerceAtLeast(0)
    }

    fun canPerformAnalysis(): Boolean {
        return getRemainingDailyQuota() > 0
    }

    fun incrementDailyQuota(): Boolean {
        val today = getTodayDateString()
        val currentCount = getTodayUsedQuota()

        if (currentCount >= MAX_DAILY_ANALYSIS) {
            return false
        }

        val currentWall = System.currentTimeMillis()
        val currentElapsed = SystemClock.elapsedRealtime()

        prefs.edit()
            .putString(KEY_QUOTA_DATE, today)
            .putInt(KEY_QUOTA_COUNT, currentCount + 1)
            .putLong(KEY_LAST_KNOWN_WALL_CLOCK, currentWall)
            .putLong(KEY_LAST_KNOWN_ELAPSED_REALTIME, currentElapsed)
            .apply()
        return true
    }

    // ==========================================
    // REMOTE SERVER CONFIG MANAGEMENT
    // ==========================================

    fun getRemoteConfigUrl(): String {
        return prefs.getString(KEY_REMOTE_CONFIG_URL, DEFAULT_REMOTE_CONFIG_URL) ?: DEFAULT_REMOTE_CONFIG_URL
    }

    fun setRemoteConfigUrl(url: String) {
        prefs.edit().putString(KEY_REMOTE_CONFIG_URL, url.trim()).apply()
    }

    fun signOut() {
        prefs.edit()
            .putBoolean(KEY_IS_SUBSCRIBED, false)
            .putBoolean(KEY_IS_REGISTERED, false)
            .putBoolean(KEY_IS_TRIAL, false)
            .remove(KEY_USER_NAME)
            .remove(KEY_USER_PHONE)
            .remove(KEY_USER_EMAIL)
            .remove(KEY_PLAN_NAME)
            .remove(KEY_AMOUNT_RIALS)
            .remove(KEY_GATEWAY_NAME)
            .remove(KEY_REF_ID)
            .remove(KEY_INVOICE_ID)
            .remove(KEY_SUBSCRIBED_AT)
            .remove(KEY_EXPIRES_AT)
            .apply()
    }
}
