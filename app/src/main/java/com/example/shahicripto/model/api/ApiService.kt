package com.example.shahicripto.model.api

import com.example.shahicripto.model.local.ChartData
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url
import okhttp3.ResponseBody

interface ApiService {
    @GET
    fun getNewsFeed(@Url url: String): Single<ResponseBody>

    @GET("https://api.coinpaprika.com/v1/coins")
    fun getCoinCatalog(): Single<List<CoinPaprikaCoinSummary>>

    @GET("https://api.coinpaprika.com/v1/tickers")
    fun getTopCoins(
        @Query("quotes") quote: String = "USD",
        @Query("limit") limit: Int = 15
    ): Single<List<CoinPaprikaTicker>>

    @GET("https://api.coinpaprika.com/v1/tickers/{coinId}")
    fun getCoinTicker(
        @Path("coinId") coinId: String,
        @Query("quotes") quote: String = "USD"
    ): Single<CoinPaprikaTicker>

    @GET("https://api.coinpaprika.com/v1/coins/{coinId}")
    fun getCoinDetails(@Path("coinId") coinId: String): Single<CoinPaprikaCoinDetails>

    @GET("https://api.coinpaprika.com/v1/coins/{coinId}/ohlcv/today")
    fun getTodayOhlc(
        @Path("coinId") coinId: String,
        @Query("quote") quote: String = "usd"
    ): Single<List<CoinPaprikaOhlc>>

    // Binance public candles provide a real multi-point chart when the
    // CoinPaprika free plan has not yet accumulated local snapshots.
    @GET("https://data-api.binance.vision/api/v3/klines")
    fun getPublicKlines(
        @Query("symbol") symbol: String,
        @Query("interval") interval: String,
        @Query("limit") limit: Int
    ): Single<List<List<Double>>>

    @GET("https://api.coingecko.com/api/v3/coins/markets")
    fun getCoinGeckoMarkets(
        @Query("vs_currency") currency: String = "usd",
        @Query("order") order: String = "market_cap_desc",
        @Query("per_page") perPage: Int = 15,
        @Query("page") page: Int = 1,
        @Query("sparkline") sparkline: Boolean = false
    ): Single<List<CoinGeckoMarketCoin>>

}
