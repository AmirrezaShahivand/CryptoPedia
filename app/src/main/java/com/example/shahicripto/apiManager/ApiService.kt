package com.example.shahicripto.apiManager

import com.example.shahicripto.apiManager.model.ChartData
import com.example.shahicripto.apiManager.model.CoinsData
import com.example.shahicripto.apiManager.model.NewsData
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
@Headers(API_KEY)
    @GET("v2/news/")
    fun getTopNews(
        @Query("sortOrder")sortOrder : String = "popular"
    ):retrofit2.Call<NewsData>

    @Headers(API_KEY)
    @GET("top/totalvolfull")
    fun getTopCoins(
        @Query("tsym") to_symbol : String = "USDT" ,
        @Query("limit") limit_data : Int = 35
        ):Single<CoinsData>


    @Headers(API_KEY)
    @GET("{period}")
    fun getChartData(
        @Path("period") period : String ,
        @Query("fsym") fromSymbol : String ,
        @Query("limit") limit : Int ,
        @Query("aggregate") aggregate : Int ,
        @Query("tsym") toSymbol : String = "USDT"
    ):Call<ChartData>

}