package com.example.shahicripto.model.api

import android.content.Context
import android.util.Log
import com.example.shahicripto.BuildConfig
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit

data class RemoteAppConfig(
    val geminiApiKey: String = "",
    val openAiApiKey: String = "",
    val zarinpalMerchantId: String = "",
    val maxDailyAnalysis: Int = 20,
    val isPaymentActive: Boolean = true,
    val serverNotice: String = ""
)

object RemoteConfigProvider {
    private const val TAG = "RemoteConfigProvider"
    private const val PREF_REMOTE_CONFIG = "remote_app_config_cache"
    private const val KEY_CACHED_GEMINI_KEY = "cached_gemini_key"
    private const val KEY_CACHED_OPENAI_KEY = "cached_openai_key"
    private const val KEY_CACHED_MAX_QUOTA = "cached_max_quota"

    // Default Google Cloud Storage / Firebase Hosting / Custom JSON URL
    @Volatile
    private var currentConfig: RemoteAppConfig = RemoteAppConfig()

    private val httpClient: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .build()

    private val gson = Gson()

    fun init(context: Context) {
        val sp = context.getSharedPreferences(PREF_REMOTE_CONFIG, Context.MODE_PRIVATE)
        val gemini = sp.getString(KEY_CACHED_GEMINI_KEY, "") ?: ""
        val openai = sp.getString(KEY_CACHED_OPENAI_KEY, "") ?: ""
        val quota = sp.getInt(KEY_CACHED_MAX_QUOTA, 20)

        currentConfig = currentConfig.copy(
            geminiApiKey = gemini,
            openAiApiKey = openai,
            maxDailyAnalysis = quota
        )
    }

    /**
     * Resolves the effective Gemini API Key:
     * 1. Dynamic Server key from Google Cloud / Remote Config (if available)
     * 2. Local BuildConfig (fallback)
     */
    fun getEffectiveGeminiKey(): String {
        if (currentConfig.geminiApiKey.isNotBlank()) {
            return currentConfig.geminiApiKey
        }
        return BuildConfig.GEMINI_API_KEY.trim()
    }

    /**
     * Resolves the effective OpenAI API Key:
     * 1. Dynamic Server key from Google Cloud / Remote Config (if available)
     * 2. Local BuildConfig (fallback)
     */
    fun getEffectiveOpenAiKey(): String {
        if (currentConfig.openAiApiKey.isNotBlank()) {
            return currentConfig.openAiApiKey
        }
        return BuildConfig.OPENAI_API_KEY.trim()
    }

    fun getMaxDailyQuota(): Int {
        return if (currentConfig.maxDailyAnalysis > 0) currentConfig.maxDailyAnalysis else 20
    }

    /**
     * Sync with remote Google Cloud Storage or Firebase endpoint
     */
    suspend fun fetchRemoteConfig(context: Context, serverUrl: String) = withContext(Dispatchers.IO) {
        if (serverUrl.isBlank() || !serverUrl.startsWith("http")) return@withContext

        try {
            val request = Request.Builder()
                .url(serverUrl)
                .get()
                .build()

            val response = httpClient.newCall(request).execute()
            if (response.isSuccessful) {
                val body = response.body?.string()
                if (!body.isNullOrBlank()) {
                    val json = JsonParser.parseString(body).asJsonObject
                    val gemini = json.get("gemini_api_key")?.asString ?: ""
                    val openai = json.get("openai_api_key")?.asString ?: ""
                    val zarinpal = json.get("zarinpal_merchant_id")?.asString ?: ""
                    val quota = json.get("max_daily_analysis")?.asInt ?: 20
                    val active = json.get("is_payment_active")?.asBoolean ?: true
                    val notice = json.get("server_notice")?.asString ?: ""

                    currentConfig = RemoteAppConfig(
                        geminiApiKey = gemini,
                        openAiApiKey = openai,
                        zarinpalMerchantId = zarinpal,
                        maxDailyAnalysis = quota,
                        isPaymentActive = active,
                        serverNotice = notice
                    )

                    context.getSharedPreferences(PREF_REMOTE_CONFIG, Context.MODE_PRIVATE)
                        .edit()
                        .putString(KEY_CACHED_GEMINI_KEY, gemini)
                        .putString(KEY_CACHED_OPENAI_KEY, openai)
                        .putInt(KEY_CACHED_MAX_QUOTA, quota)
                        .apply()

                    Log.d(TAG, "Remote config updated successfully from cloud server")
                }
            }
        } catch (e: Exception) {
            Log.w(TAG, "Could not fetch remote config: ${e.message}")
        }
    }
}
