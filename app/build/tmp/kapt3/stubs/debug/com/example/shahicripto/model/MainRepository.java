package com.example.shahicripto.model;

import java.lang.System;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u001c\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\rJ\u0012\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\u00110\u0010J\u0012\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u00110\u0010J\u0006\u0010\u0015\u001a\u00020\u0016J\u0006\u0010\u0017\u001a\u00020\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0018"}, d2 = {"Lcom/example/shahicripto/model/MainRepository;", "", "apiService", "Lcom/example/shahicripto/model/api/ApiService;", "coinsDataDao", "Lcom/example/shahicripto/model/local/CoinsData/CoinsDataDao;", "newsDataDao", "Lcom/example/shahicripto/model/local/NewsData/NewsDataDao;", "(Lcom/example/shahicripto/model/api/ApiService;Lcom/example/shahicripto/model/local/CoinsData/CoinsDataDao;Lcom/example/shahicripto/model/local/NewsData/NewsDataDao;)V", "getChartData", "Lio/reactivex/Single;", "Lcom/example/shahicripto/model/local/ChartData;", "symbol", "", "period", "getCoinsList", "Landroidx/lifecycle/LiveData;", "", "Lcom/example/shahicripto/model/local/CoinsData/CoinsDataEntitity;", "getNews", "Lcom/example/shahicripto/model/local/NewsData/NewsDataEntity;", "refreshData", "Lio/reactivex/Completable;", "refreshDataNews", "app_debug"})
public final class MainRepository {
    private final com.example.shahicripto.model.api.ApiService apiService = null;
    private final com.example.shahicripto.model.local.CoinsData.CoinsDataDao coinsDataDao = null;
    private final com.example.shahicripto.model.local.NewsData.NewsDataDao newsDataDao = null;
    
    public MainRepository(@org.jetbrains.annotations.NotNull
    com.example.shahicripto.model.api.ApiService apiService, @org.jetbrains.annotations.NotNull
    com.example.shahicripto.model.local.CoinsData.CoinsDataDao coinsDataDao, @org.jetbrains.annotations.NotNull
    com.example.shahicripto.model.local.NewsData.NewsDataDao newsDataDao) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<java.util.List<com.example.shahicripto.model.local.CoinsData.CoinsDataEntitity>> getCoinsList() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final io.reactivex.Completable refreshData() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final io.reactivex.Completable refreshDataNews() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<java.util.List<com.example.shahicripto.model.local.NewsData.NewsDataEntity>> getNews() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final io.reactivex.Single<com.example.shahicripto.model.local.ChartData> getChartData(@org.jetbrains.annotations.NotNull
    java.lang.String symbol, @org.jetbrains.annotations.NotNull
    java.lang.String period) {
        return null;
    }
}