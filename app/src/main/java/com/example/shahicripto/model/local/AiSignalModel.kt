package com.example.shahicripto.model.local

import java.io.Serializable

enum class AiEngine(val code: String, val displayName: String) {
    GEMINI("gemini", "Gemini 3.5"),
    CHATGPT("chatgpt", "ChatGPT 4o")
}

enum class TimeframeOption(val code: String, val titleEn: String, val titleFa: String, val hoursMultiplier: Double) {
    ONE_HOUR("1h", "1 Hour", "۱ ساعت", 1.0),
    ONE_DAY("1d", "1 Day", "۱ روز", 24.0),
    ONE_WEEK("1w", "1 Week", "۱ هفته", 168.0),
    ONE_MONTH("1m", "1 Month", "۱ ماه", 720.0),
    ONE_YEAR("1y", "1 Year", "۱ سال", 8760.0);

    fun getDisplayName(isFa: Boolean): String = if (isFa) titleFa else titleEn
}

enum class SignalAction(val labelEn: String, val labelFa: String, val colorHex: String) {
    STRONG_BUY("STRONG BUY", "خرید قوی", "#059669"),
    BUY("BUY", "خرید", "#10B981"),
    NEUTRAL("NEUTRAL / HOLD", "خنثی / نگهداری", "#F59E0B"),
    SELL("SELL", "فروش", "#EF4444"),
    STRONG_SELL("STRONG SELL", "فروش قوی", "#B91C1C");

    fun getDisplayLabel(isFa: Boolean): String = if (isFa) labelFa else labelEn
}

data class AiSignalResult(
    val coinSymbol: String,
    val coinName: String,
    val timeframe: TimeframeOption,
    val action: SignalAction,
    val confidencePercent: Int,
    val currentPrice: Double,
    val targetPrice: Double,
    val stopLoss: Double,
    val riskLevel: String,
    val technicalSummary: String,
    val fundamentalSummary: String,
    val aiRationale: String,
    val rsiEstimate: Double,
    val trendDirection: String,
    val aiEngine: AiEngine = AiEngine.GEMINI,
    val timestamp: Long = System.currentTimeMillis()
) : Serializable
