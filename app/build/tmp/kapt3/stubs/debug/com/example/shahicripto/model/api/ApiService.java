package com.example.shahicripto.model.api;

import java.lang.System;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J@\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\u0007\u001a\u00020\u00062\b\b\u0001\u0010\b\u001a\u00020\t2\b\b\u0001\u0010\n\u001a\u00020\t2\b\b\u0003\u0010\u000b\u001a\u00020\u0006H\'J\"\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u00032\b\b\u0003\u0010\u000e\u001a\u00020\u00062\b\b\u0003\u0010\u000f\u001a\u00020\tH\'J\u0018\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u00032\b\b\u0003\u0010\u0012\u001a\u00020\u0006H\'\u00a8\u0006\u0013"}, d2 = {"Lcom/example/shahicripto/model/api/ApiService;", "", "getChartData", "Lio/reactivex/Single;", "Lcom/example/shahicripto/model/local/ChartData;", "period", "", "fromSymbol", "limit", "", "aggregate", "toSymbol", "getTopCoins", "Lcom/example/shahicripto/model/local/CoinsData/CoinsData;", "to_symbol", "limit_data", "getTopNews", "Lcom/example/shahicripto/model/local/NewsData/NewsData;", "sortOrder", "app_debug"})
public abstract interface ApiService {
    
    @org.jetbrains.annotations.NotNull
    @retrofit2.http.GET(value = "v2/news/")
    @retrofit2.http.Headers(value = {"authorization: Apikey 8cdeb9297ace8cda5728e467ae8c0dfbb42cd6bf47d3fc340e35c4e9db8fb8eb"})
    public abstract io.reactivex.Single<com.example.shahicripto.model.local.NewsData.NewsData> getTopNews(@org.jetbrains.annotations.NotNull
    @retrofit2.http.Query(value = "sortOrder")
    java.lang.String sortOrder);
    
    @org.jetbrains.annotations.NotNull
    @retrofit2.http.GET(value = "top/totalvolfull")
    @retrofit2.http.Headers(value = {"authorization: Apikey 8cdeb9297ace8cda5728e467ae8c0dfbb42cd6bf47d3fc340e35c4e9db8fb8eb"})
    public abstract io.reactivex.Single<com.example.shahicripto.model.local.CoinsData.CoinsData> getTopCoins(@org.jetbrains.annotations.NotNull
    @retrofit2.http.Query(value = "tsym")
    java.lang.String to_symbol, @retrofit2.http.Query(value = "limit")
    int limit_data);
    
    @org.jetbrains.annotations.NotNull
    @retrofit2.http.GET(value = "{period}")
    @retrofit2.http.Headers(value = {"authorization: Apikey 8cdeb9297ace8cda5728e467ae8c0dfbb42cd6bf47d3fc340e35c4e9db8fb8eb"})
    public abstract io.reactivex.Single<com.example.shahicripto.model.local.ChartData> getChartData(@org.jetbrains.annotations.NotNull
    @retrofit2.http.Path(value = "period")
    java.lang.String period, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Query(value = "fsym")
    java.lang.String fromSymbol, @retrofit2.http.Query(value = "limit")
    int limit, @retrofit2.http.Query(value = "aggregate")
    int aggregate, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Query(value = "tsym")
    java.lang.String toSymbol);
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 3)
    public final class DefaultImpls {
    }
}