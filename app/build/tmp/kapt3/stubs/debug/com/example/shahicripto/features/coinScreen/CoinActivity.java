package com.example.shahicripto.features.coinScreen;

import java.lang.System;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u001f\u001a\u00020 H\u0002J\b\u0010!\u001a\u00020 H\u0003J\b\u0010\"\u001a\u00020 H\u0002J\b\u0010#\u001a\u00020 H\u0002J\u0012\u0010$\u001a\u00020 2\b\u0010%\u001a\u0004\u0018\u00010&H\u0014J\b\u0010\'\u001a\u00020 H\u0014J\u0010\u0010(\u001a\u00020 2\u0006\u0010)\u001a\u00020*H\u0002J\u000e\u0010+\u001a\u00020 2\u0006\u0010,\u001a\u00020*R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u0011\u0010\u000f\u001a\u00020\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0013\u001a\u00020\u0014X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u001a\u0010\u0019\u001a\u00020\u001aX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001e\u00a8\u0006-"}, d2 = {"Lcom/example/shahicripto/features/coinScreen/CoinActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/example/shahicripto/databinding/ActivityCoinBinding;", "getBinding", "()Lcom/example/shahicripto/databinding/ActivityCoinBinding;", "setBinding", "(Lcom/example/shahicripto/databinding/ActivityCoinBinding;)V", "chartScreenViewModel", "Lcom/example/shahicripto/features/coinScreen/ChartScreenViewModel;", "getChartScreenViewModel", "()Lcom/example/shahicripto/features/coinScreen/ChartScreenViewModel;", "setChartScreenViewModel", "(Lcom/example/shahicripto/features/coinScreen/ChartScreenViewModel;)V", "compositeDisposable", "Lio/reactivex/disposables/CompositeDisposable;", "getCompositeDisposable", "()Lio/reactivex/disposables/CompositeDisposable;", "dataThisCoin", "Lcom/example/shahicripto/model/local/CoinsData/CoinsDataEntitity;", "getDataThisCoin", "()Lcom/example/shahicripto/model/local/CoinsData/CoinsDataEntitity;", "setDataThisCoin", "(Lcom/example/shahicripto/model/local/CoinsData/CoinsDataEntitity;)V", "dataThisCoinAbout", "Lcom/example/shahicripto/model/local/CoinAboutItem;", "getDataThisCoinAbout", "()Lcom/example/shahicripto/model/local/CoinAboutItem;", "setDataThisCoinAbout", "(Lcom/example/shahicripto/model/local/CoinAboutItem;)V", "initAboutUi", "", "initChartUi", "initStatisticsUi", "initUi", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "openWebsiteDataCoin", "url", "", "requestAndShowChart", "period", "app_debug"})
public final class CoinActivity extends androidx.appcompat.app.AppCompatActivity {
    public com.example.shahicripto.databinding.ActivityCoinBinding binding;
    public com.example.shahicripto.model.local.CoinsData.CoinsDataEntitity dataThisCoin;
    public com.example.shahicripto.model.local.CoinAboutItem dataThisCoinAbout;
    public com.example.shahicripto.features.coinScreen.ChartScreenViewModel chartScreenViewModel;
    @org.jetbrains.annotations.NotNull
    private final io.reactivex.disposables.CompositeDisposable compositeDisposable = null;
    
    public CoinActivity() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.example.shahicripto.databinding.ActivityCoinBinding getBinding() {
        return null;
    }
    
    public final void setBinding(@org.jetbrains.annotations.NotNull
    com.example.shahicripto.databinding.ActivityCoinBinding p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.example.shahicripto.model.local.CoinsData.CoinsDataEntitity getDataThisCoin() {
        return null;
    }
    
    public final void setDataThisCoin(@org.jetbrains.annotations.NotNull
    com.example.shahicripto.model.local.CoinsData.CoinsDataEntitity p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.example.shahicripto.model.local.CoinAboutItem getDataThisCoinAbout() {
        return null;
    }
    
    public final void setDataThisCoinAbout(@org.jetbrains.annotations.NotNull
    com.example.shahicripto.model.local.CoinAboutItem p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.example.shahicripto.features.coinScreen.ChartScreenViewModel getChartScreenViewModel() {
        return null;
    }
    
    public final void setChartScreenViewModel(@org.jetbrains.annotations.NotNull
    com.example.shahicripto.features.coinScreen.ChartScreenViewModel p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final io.reactivex.disposables.CompositeDisposable getCompositeDisposable() {
        return null;
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void initUi() {
    }
    
    private final void initAboutUi() {
    }
    
    private final void openWebsiteDataCoin(java.lang.String url) {
    }
    
    private final void initStatisticsUi() {
    }
    
    @android.annotation.SuppressLint(value = {"SetTextI18n"})
    private final void initChartUi() {
    }
    
    public final void requestAndShowChart(@org.jetbrains.annotations.NotNull
    java.lang.String period) {
    }
    
    @java.lang.Override
    protected void onDestroy() {
    }
}