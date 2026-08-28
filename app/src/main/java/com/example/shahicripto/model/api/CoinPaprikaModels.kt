package com.example.shahicripto.model.api

import com.google.gson.annotations.SerializedName

data class CoinPaprikaCoinSummary(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("symbol") val symbol: String,
    @SerializedName("rank") val rank: Int,
    @SerializedName("is_new") val isNew: Boolean,
    @SerializedName("is_active") val isActive: Boolean,
    @SerializedName("type") val type: String
)

data class CoinPaprikaTicker(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("symbol") val symbol: String,
    @SerializedName("rank") val rank: Int,
    @SerializedName("total_supply") val totalSupply: Double?,
    @SerializedName("quotes") val quotes: Map<String, CoinPaprikaQuote>?
)

data class CoinPaprikaQuote(
    @SerializedName("price") val price: Double?,
    @SerializedName("volume_24h") val volume24h: Double?,
    @SerializedName("market_cap") val marketCap: Double?,
    @SerializedName("percent_change_24h") val percentChange24h: Double?
)

data class CoinPaprikaCoinDetails(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("symbol") val symbol: String,
    @SerializedName("logo") val logo: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("links") val links: CoinPaprikaLinks?
)

data class CoinPaprikaLinks(
    @SerializedName("website") val website: List<String>?,
    @SerializedName("source_code") val sourceCode: List<String>?,
    @SerializedName("reddit") val reddit: List<String>?,
    @SerializedName("twitter") val twitter: List<String>?
)

data class CoinGeckoMarketCoin(
    @SerializedName("id") val id: String,
    @SerializedName("symbol") val symbol: String,
    @SerializedName("name") val name: String,
    @SerializedName("image") val image: String?,
    @SerializedName("current_price") val currentPrice: Double?,
    @SerializedName("market_cap") val marketCap: Double?,
    @SerializedName("total_volume") val totalVolume: Double?,
    @SerializedName("price_change_percentage_24h") val priceChangePercentage24h: Double?,
    @SerializedName("total_supply") val totalSupply: Double?
)

data class CoinPaprikaOhlc(
    @SerializedName("time_open") val timeOpen: String?,
    @SerializedName("time_close") val timeClose: String?,
    @SerializedName("open") val open: Double?,
    @SerializedName("high") val high: Double?,
    @SerializedName("low") val low: Double?,
    @SerializedName("close") val close: Double?,
    @SerializedName("volume") val volume: Double?,
    @SerializedName("market_cap") val marketCap: Double?
)
