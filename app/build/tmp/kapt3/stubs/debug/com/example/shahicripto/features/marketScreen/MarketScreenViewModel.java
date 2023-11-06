package com.example.shahicripto.features.marketScreen;

import java.lang.System;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\u000bJ\u0012\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\u000bJ\u0012\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\r0\u000bJ\u0006\u0010\u0011\u001a\u00020\u0012J\u0006\u0010\u0013\u001a\u00020\u0012R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2 = {"Lcom/example/shahicripto/features/marketScreen/MarketScreenViewModel;", "Landroidx/lifecycle/ViewModel;", "mainRepository", "Lcom/example/shahicripto/model/MainRepository;", "(Lcom/example/shahicripto/model/MainRepository;)V", "errorData", "Landroidx/lifecycle/MutableLiveData;", "", "netDisposable", "Lio/reactivex/disposables/Disposable;", "getErrorData", "Landroidx/lifecycle/LiveData;", "getTopCoinsFromDataBase", "", "Lcom/example/shahicripto/model/local/CoinsData/CoinsDataEntitity;", "getTopNewsFromDataBase", "Lcom/example/shahicripto/model/local/NewsData/NewsDataEntity;", "refreshData", "", "refreshNews", "app_debug"})
public final class MarketScreenViewModel extends androidx.lifecycle.ViewModel {
    private final com.example.shahicripto.model.MainRepository mainRepository = null;
    private io.reactivex.disposables.Disposable netDisposable;
    private final androidx.lifecycle.MutableLiveData<java.lang.String> errorData = null;
    
    public MarketScreenViewModel(@org.jetbrains.annotations.NotNull
    com.example.shahicripto.model.MainRepository mainRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<java.util.List<com.example.shahicripto.model.local.CoinsData.CoinsDataEntitity>> getTopCoinsFromDataBase() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<java.util.List<com.example.shahicripto.model.local.NewsData.NewsDataEntity>> getTopNewsFromDataBase() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<java.lang.String> getErrorData() {
        return null;
    }
    
    public final void refreshData() {
    }
    
    public final void refreshNews() {
    }
}