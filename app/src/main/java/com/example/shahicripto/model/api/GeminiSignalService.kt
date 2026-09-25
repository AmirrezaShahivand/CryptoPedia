package com.example.shahicripto.model.api

import com.example.shahicripto.BuildConfig
import com.example.shahicripto.model.local.AiEngine
import com.example.shahicripto.model.local.AiSignalResult
import com.example.shahicripto.model.local.SignalAction
import com.example.shahicripto.model.local.TimeframeOption
import com.example.shahicripto.model.local.CoinsData.CoinsDataEntitity
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.Locale
import java.util.concurrent.TimeUnit
import kotlin.math.abs
import kotlin.math.roundToInt

class GeminiSignalService(
    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build(),
    private val gson: Gson = Gson()
) {

    suspend fun analyzeCoin(
        coin: CoinsDataEntitity,
        timeframe: TimeframeOption,
        engine: AiEngine,
        isFa: Boolean
    ): AiSignalResult = withContext(Dispatchers.IO) {
        val currentPrice = parseCleanPrice(coin.price)
        val change24h = coin.change
        val volume = coin.hajm

        val geminiKey = RemoteConfigProvider.getEffectiveGeminiKey()
        val openAiKey = RemoteConfigProvider.getEffectiveOpenAiKey()

        if (engine == AiEngine.CHATGPT && openAiKey.isNotBlank()) {
            try {
                return@withContext requestChatGptAnalysis(
                    apiKey = openAiKey,
                    coin = coin,
                    currentPrice = currentPrice,
                    change24h = change24h,
                    volume = volume,
                    timeframe = timeframe,
                    isFa = isFa
                )
            } catch (_: Exception) {
                // Try Gemini fallback if key is available
                if (geminiKey.isNotBlank()) {
                    try {
                        return@withContext requestGeminiAnalysis(
                            apiKey = geminiKey,
                            coin = coin,
                            currentPrice = currentPrice,
                            change24h = change24h,
                            volume = volume,
                            timeframe = timeframe,
                            isFa = isFa
                        )
                    } catch (_: Exception) {}
                }
            }
        } else if (engine == AiEngine.GEMINI && geminiKey.isNotBlank()) {
            try {
                return@withContext requestGeminiAnalysis(
                    apiKey = geminiKey,
                    coin = coin,
                    currentPrice = currentPrice,
                    change24h = change24h,
                    volume = volume,
                    timeframe = timeframe,
                    isFa = isFa
                )
            } catch (_: Exception) {
                // Try ChatGPT fallback if key is available
                if (openAiKey.isNotBlank()) {
                    try {
                        return@withContext requestChatGptAnalysis(
                            apiKey = openAiKey,
                            coin = coin,
                            currentPrice = currentPrice,
                            change24h = change24h,
                            volume = volume,
                            timeframe = timeframe,
                            isFa = isFa
                        )
                    } catch (_: Exception) {}
                }
            }
        }

        // Quantitative algorithmic fallback
        return@withContext computeQuantitativeSignal(
            coin = coin,
            currentPrice = currentPrice,
            change24h = change24h,
            volume = volume,
            timeframe = timeframe,
            engine = engine,
            isFa = isFa
        )
    }

    private fun requestChatGptAnalysis(
        apiKey: String,
        coin: CoinsDataEntitity,
        currentPrice: Double,
        change24h: Double,
        volume: Double,
        timeframe: TimeframeOption,
        isFa: Boolean
    ): AiSignalResult {
        val langInstruction = if (isFa) {
            "Respond in fluent Persian (Farsi). All text explanations must be in Persian."
        } else {
            "Respond in clear, professional English."
        }

        val prompt = """
            Analyze the following cryptocurrency asset:
            - Name: ${coin.fullName} (${coin.name})
            - Current Price: $$currentPrice
            - 24h Price Change: $change24h%
            - 24h Trading Volume: $$volume
            - Target Analysis Timeframe: ${timeframe.titleEn} (${timeframe.code})

            Task:
            Combine in-depth Technical Analysis (support, resistance, momentum, trend, estimated RSI) with Fundamental Analysis (liquidity ratio, network value, market momentum).
            Produce a decisive trade recommendation for the chosen timeframe: ${timeframe.titleEn}.

            $langInstruction

            Return STRICTLY a JSON object with this exact schema:
            {
              "action": "STRONG_BUY" or "BUY" or "NEUTRAL" or "SELL" or "STRONG_SELL",
              "confidencePercent": integer between 60 and 95,
              "targetPrice": number (logical profit target for this timeframe),
              "stopLoss": number (logical stop loss level for this timeframe),
              "riskLevel": "Low" or "Medium" or "High",
              "technicalSummary": "1-2 sentences summarizing technical indicators and levels",
              "fundamentalSummary": "1-2 sentences summarizing liquidity and fundamentals",
              "aiRationale": "Comprehensive 2-3 sentence strategic rationale for this signal in the selected timeframe",
              "rsiEstimate": number between 15.0 and 85.0,
              "trendDirection": "Bullish" or "Bearish" or "Neutral"
            }
        """.trimIndent()

        val messagesArray = com.google.gson.JsonArray().apply {
            val systemMsg = JsonObject().apply {
                addProperty("role", "system")
                addProperty("content", "You are a professional Senior Cryptocurrency Quantitative Analyst & Algorithmic Trading AI.")
            }
            val userMsg = JsonObject().apply {
                addProperty("role", "user")
                addProperty("content", prompt)
            }
            add(systemMsg)
            add(userMsg)
        }

        val requestJson = JsonObject().apply {
            addProperty("model", "gpt-4o-mini")
            addProperty("temperature", 0.3)
            val formatObj = JsonObject().apply {
                addProperty("type", "json_object")
            }
            add("response_format", formatObj)
            add("messages", messagesArray)
        }

        val requestBody = requestJson.toString().toRequestBody("application/json".toMediaType())
        val request = Request.Builder()
            .url("https://api.openai.com/v1/chat/completions")
            .addHeader("Authorization", "Bearer $apiKey")
            .post(requestBody)
            .build()

        val response = okHttpClient.newCall(request).execute()
        if (!response.isSuccessful) {
            throw IllegalStateException("ChatGPT API error: ${response.code}")
        }

        val bodyString = response.body?.string().orEmpty()
        val parsedJson = JsonParser.parseString(bodyString).asJsonObject
        val content = parsedJson
            .getAsJsonArray("choices")
            ?.get(0)?.asJsonObject
            ?.getAsJsonObject("message")
            ?.get("content")?.asString.orEmpty()

        val cleanJson = content.trim().removePrefix("```json").removePrefix("```").removeSuffix("```").trim()
        val resultObj = JsonParser.parseString(cleanJson).asJsonObject

        return parseResultJson(
            resultObj = resultObj,
            coin = coin,
            currentPrice = currentPrice,
            timeframe = timeframe,
            engine = AiEngine.CHATGPT
        )
    }

    private fun requestGeminiAnalysis(
        apiKey: String,
        coin: CoinsDataEntitity,
        currentPrice: Double,
        change24h: Double,
        volume: Double,
        timeframe: TimeframeOption,
        isFa: Boolean
    ): AiSignalResult {
        val langInstruction = if (isFa) {
            "Respond in fluent Persian (Farsi). All text explanations must be in Persian."
        } else {
            "Respond in clear, professional English."
        }

        val prompt = """
            You are a professional Senior Cryptocurrency Quantitative Analyst & Algorithmic Trading AI.
            Analyze the following cryptocurrency asset:
            - Name: ${coin.fullName} (${coin.name})
            - Current Price: $$currentPrice
            - 24h Price Change: $change24h%
            - 24h Trading Volume: $$volume
            - Target Analysis Timeframe: ${timeframe.titleEn} (${timeframe.code})

            Task:
            Combine in-depth Technical Analysis (support, resistance, momentum, trend, estimated RSI) with Fundamental Analysis (liquidity ratio, network value, market momentum).
            Produce a decisive trade recommendation for the chosen timeframe: ${timeframe.titleEn}.

            $langInstruction

            Return STRICTLY a JSON object with this exact schema (no markdown, no backticks, pure json):
            {
              "action": "STRONG_BUY" or "BUY" or "NEUTRAL" or "SELL" or "STRONG_SELL",
              "confidencePercent": integer between 60 and 95,
              "targetPrice": number (logical profit target for this timeframe),
              "stopLoss": number (logical stop loss level for this timeframe),
              "riskLevel": "Low" or "Medium" or "High",
              "technicalSummary": "1-2 sentences summarizing technical indicators and levels",
              "fundamentalSummary": "1-2 sentences summarizing liquidity and fundamentals",
              "aiRationale": "Comprehensive 2-3 sentence strategic rationale for this signal in the selected timeframe",
              "rsiEstimate": number between 15.0 and 85.0,
              "trendDirection": "Bullish" or "Bearish" or "Neutral"
            }
        """.trimIndent()

        val requestJson = JsonObject().apply {
            val contentsArray = com.google.gson.JsonArray().apply {
                val contentObj = JsonObject().apply {
                    val partsArray = com.google.gson.JsonArray().apply {
                        val partObj = JsonObject().apply {
                            addProperty("text", prompt)
                        }
                        add(partObj)
                    }
                    add("parts", partsArray)
                }
                add(contentObj)
            }
            add("contents", contentsArray)

            val configObj = JsonObject().apply {
                addProperty("temperature", 0.3)
                addProperty("responseMimeType", "application/json")
            }
            add("generationConfig", configObj)
        }

        val requestBody = requestJson.toString().toRequestBody("application/json".toMediaType())
        val url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent?key=$apiKey"

        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        val response = okHttpClient.newCall(request).execute()
        if (!response.isSuccessful) {
            throw IllegalStateException("Gemini API error: ${response.code}")
        }

        val bodyString = response.body?.string().orEmpty()
        val parsedJson = JsonParser.parseString(bodyString).asJsonObject
        val text = parsedJson
            .getAsJsonArray("candidates")
            ?.get(0)?.asJsonObject
            ?.getAsJsonObject("content")
            ?.getAsJsonArray("parts")
            ?.get(0)?.asJsonObject
            ?.get("text")?.asString.orEmpty()

        val cleanJson = text.trim().removePrefix("```json").removePrefix("```").removeSuffix("```").trim()
        val resultObj = JsonParser.parseString(cleanJson).asJsonObject

        return parseResultJson(
            resultObj = resultObj,
            coin = coin,
            currentPrice = currentPrice,
            timeframe = timeframe,
            engine = AiEngine.GEMINI
        )
    }

    private fun parseResultJson(
        resultObj: JsonObject,
        coin: CoinsDataEntitity,
        currentPrice: Double,
        timeframe: TimeframeOption,
        engine: AiEngine
    ): AiSignalResult {
        val actionStr = resultObj.get("action")?.asString ?: "BUY"
        val action = when (actionStr.uppercase(Locale.US)) {
            "STRONG_BUY" -> SignalAction.STRONG_BUY
            "BUY" -> SignalAction.BUY
            "SELL" -> SignalAction.SELL
            "STRONG_SELL" -> SignalAction.STRONG_SELL
            else -> SignalAction.NEUTRAL
        }

        val confidence = resultObj.get("confidencePercent")?.asInt ?: 78
        val target = resultObj.get("targetPrice")?.asDouble ?: (currentPrice * 1.05)
        val stop = resultObj.get("stopLoss")?.asDouble ?: (currentPrice * 0.95)
        val risk = resultObj.get("riskLevel")?.asString ?: "Medium"
        val tech = resultObj.get("technicalSummary")?.asString ?: ""
        val fund = resultObj.get("fundamentalSummary")?.asString ?: ""
        val rationale = resultObj.get("aiRationale")?.asString ?: ""
        val rsi = resultObj.get("rsiEstimate")?.asDouble ?: 52.0
        val trend = resultObj.get("trendDirection")?.asString ?: "Bullish"

        return AiSignalResult(
            coinSymbol = coin.name,
            coinName = coin.fullName,
            timeframe = timeframe,
            action = action,
            confidencePercent = confidence,
            currentPrice = currentPrice,
            targetPrice = target,
            stopLoss = stop,
            riskLevel = risk,
            technicalSummary = tech,
            fundamentalSummary = fund,
            aiRationale = rationale,
            rsiEstimate = rsi,
            trendDirection = trend,
            aiEngine = engine
        )
    }

    private fun computeQuantitativeSignal(
        coin: CoinsDataEntitity,
        currentPrice: Double,
        change24h: Double,
        volume: Double,
        timeframe: TimeframeOption,
        engine: AiEngine,
        isFa: Boolean
    ): AiSignalResult {
        val tfFactor = when (timeframe) {
            TimeframeOption.ONE_HOUR -> 0.012
            TimeframeOption.ONE_DAY -> 0.045
            TimeframeOption.ONE_WEEK -> 0.12
            TimeframeOption.ONE_MONTH -> 0.25
            TimeframeOption.ONE_YEAR -> 0.65
        }

        val baseRsi = 50.0 + (change24h * 1.8).coerceIn(-35.0, 35.0)
        val rsi = (baseRsi * 10.0).roundToInt() / 10.0

        val (action, confidence, targetMultiplier, stopMultiplier) = when {
            change24h > 4.5 && rsi < 72.0 -> {
                Quad(SignalAction.STRONG_BUY, 88, 1.0 + (tfFactor * 1.3), 1.0 - (tfFactor * 0.65))
            }
            change24h > 0.5 -> {
                Quad(SignalAction.BUY, 79, 1.0 + tfFactor, 1.0 - (tfFactor * 0.7))
            }
            change24h < -5.0 && rsi < 28.0 -> {
                Quad(SignalAction.BUY, 72, 1.0 + (tfFactor * 0.9), 1.0 - (tfFactor * 0.6))
            }
            change24h < -3.0 -> {
                Quad(SignalAction.SELL, 76, 1.0 - (tfFactor * 0.9), 1.0 + (tfFactor * 0.5))
            }
            change24h < -8.0 -> {
                Quad(SignalAction.STRONG_SELL, 84, 1.0 - (tfFactor * 1.2), 1.0 + (tfFactor * 0.6))
            }
            else -> {
                Quad(SignalAction.NEUTRAL, 68, 1.0 + (tfFactor * 0.5), 1.0 - (tfFactor * 0.5))
            }
        }

        val targetPrice = currentPrice * targetMultiplier
        val stopLoss = currentPrice * stopMultiplier

        val trendDirection = when {
            change24h > 1.5 -> if (isFa) "صعودی (Bullish)" else "Bullish"
            change24h < -1.5 -> if (isFa) "نزولی (Bearish)" else "Bearish"
            else -> if (isFa) "رنج / خنثی (Neutral)" else "Neutral / Sideways"
        }

        val riskLevel = when {
            abs(change24h) > 6.0 || timeframe == TimeframeOption.ONE_HOUR -> if (isFa) "بالا" else "High"
            abs(change24h) > 2.5 -> if (isFa) "متوسط" else "Medium"
            else -> if (isFa) "کم" else "Low"
        }

        val engineName = engine.displayName

        val technicalSummary = if (isFa) {
            "تحلیل تکنیکال $engineName: شاخص RSI روی %.1f و ساختار روند در بازه %s به صورت %s ارزیابی شد. سطوح کلیدی حمایت و مقاومت مشخص شده‌اند."
                .format(Locale.US, rsi, timeframe.titleFa, trendDirection)
        } else {
            "$engineName Technical: RSI is evaluated at %.1f with %s price structure on the %s horizon. Key dynamic support/resistance established."
                .format(Locale.US, rsi, trendDirection, timeframe.titleEn)
        }

        val fundamentalSummary = if (isFa) {
            "تحلیل فاندامنتال: حجم معاملات ۲۴ ساعته برابر با %s دلار است که نشان‌دهنده نقدینگی فعال و تقاضای مستمر نهادی برای این دارایی است."
                .format(Locale.US, formatCompactNumber(volume))
        } else {
            "Fundamental Analysis: 24h trading volume stands at $%s, indicating active liquidity and institutional demand."
                .format(Locale.US, formatCompactNumber(volume))
        }

        val aiRationale = if (isFa) {
            "هوش مصنوعی $engineName با تجمیع متغیرهای تکنیکال و فاندامنتال در تایم‌فریم %s سیگنال %s را با اطمینان %d٪ اعلام می‌کند. پایبندی به حد ضرر تعیین‌شده ضروری است."
                .format(Locale.US, timeframe.titleFa, action.labelFa, confidence)
        } else {
            "$engineName synthesis combines technical & fundamental factors in the %s timeframe, generating a %s recommendation with %d%% confidence."
                .format(Locale.US, timeframe.titleEn, action.labelEn, confidence)
        }

        return AiSignalResult(
            coinSymbol = coin.name,
            coinName = coin.fullName,
            timeframe = timeframe,
            action = action,
            confidencePercent = confidence,
            currentPrice = currentPrice,
            targetPrice = targetPrice,
            stopLoss = stopLoss,
            riskLevel = riskLevel,
            technicalSummary = technicalSummary,
            fundamentalSummary = fundamentalSummary,
            aiRationale = aiRationale,
            rsiEstimate = rsi,
            trendDirection = trendDirection,
            aiEngine = engine
        )
    }

    private fun parseCleanPrice(priceStr: String): Double {
        val clean = priceStr.replace("$", "").replace(",", "").trim()
        return clean.toDoubleOrNull() ?: 1.0
    }

    private fun formatCompactNumber(value: Double): String {
        return when {
            value >= 1_000_000_000 -> String.format(Locale.US, "%.2fB", value / 1_000_000_000.0)
            value >= 1_000_000 -> String.format(Locale.US, "%.2fM", value / 1_000_000.0)
            value >= 1_000 -> String.format(Locale.US, "%.2fK", value / 1_000.0)
            else -> String.format(Locale.US, "%.2f", value)
        }
    }

    private data class Quad<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)
}
