package com.example.shahicripto.features.coinScreen;

import java.lang.System;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001c\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\tR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/example/shahicripto/features/coinScreen/ChartScreenViewModel;", "Landroidx/lifecycle/ViewModel;", "mainRepository", "Lcom/example/shahicripto/model/MainRepository;", "(Lcom/example/shahicripto/model/MainRepository;)V", "getChartCoinFromApi", "Lio/reactivex/Single;", "Lcom/example/shahicripto/model/local/ChartData;", "symbol", "", "period", "app_debug"})
public final class ChartScreenViewModel extends androidx.lifecycle.ViewModel {
    private final com.example.shahicripto.model.MainRepository mainRepository = null;
    
    public ChartScreenViewModel(@org.jetbrains.annotations.NotNull
    com.example.shahicripto.model.MainRepository mainRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final io.reactivex.Single<com.example.shahicripto.model.local.ChartData> getChartCoinFromApi(@org.jetbrains.annotations.NotNull
    java.lang.String symbol, @org.jetbrains.annotations.NotNull
    java.lang.String period) {
        return null;
    }
}