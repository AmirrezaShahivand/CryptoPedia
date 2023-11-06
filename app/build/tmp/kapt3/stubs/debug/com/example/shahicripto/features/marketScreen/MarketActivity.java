package com.example.shahicripto.features.marketScreen;

import java.lang.System;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010\"\u001a\u00020#H\u0002J\b\u0010$\u001a\u00020#H\u0002J\b\u0010%\u001a\u00020#H\u0002J\b\u0010&\u001a\u00020#H\u0002J\u0010\u0010\'\u001a\u00020#2\u0006\u0010(\u001a\u00020)H\u0002J\u0012\u0010*\u001a\u00020#2\b\u0010+\u001a\u0004\u0018\u00010,H\u0014J\b\u0010-\u001a\u00020#H\u0014J\u0010\u0010.\u001a\u00020#2\u0006\u0010/\u001a\u000200H\u0016J\b\u00101\u001a\u00020#H\u0014J\b\u00102\u001a\u00020#H\u0002J\u0016\u00103\u001a\u00020#2\f\u00104\u001a\b\u0012\u0004\u0012\u00020005H\u0002R&\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001a\u0010\f\u001a\u00020\rX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000RB\u0010\u0014\u001a*\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u00160\u0015j\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0016`\u0017X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u001a\u0010\u001c\u001a\u00020\u001dX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!\u00a8\u00066"}, d2 = {"Lcom/example/shahicripto/features/marketScreen/MarketActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "Lcom/example/shahicripto/features/marketScreen/MarketAdapter$RecyclerCallback;", "()V", "aboutDataMap", "", "", "Lcom/example/shahicripto/model/local/CoinAboutItem;", "getAboutDataMap", "()Ljava/util/Map;", "setAboutDataMap", "(Ljava/util/Map;)V", "binding", "Lcom/example/shahicripto/databinding/ActivityMarketBinding;", "getBinding", "()Lcom/example/shahicripto/databinding/ActivityMarketBinding;", "setBinding", "(Lcom/example/shahicripto/databinding/ActivityMarketBinding;)V", "compositeDisposable", "Lio/reactivex/disposables/CompositeDisposable;", "dataNews", "Ljava/util/ArrayList;", "Lkotlin/Pair;", "Lkotlin/collections/ArrayList;", "getDataNews", "()Ljava/util/ArrayList;", "setDataNews", "(Ljava/util/ArrayList;)V", "marketScreenViewModel", "Lcom/example/shahicripto/features/marketScreen/MarketScreenViewModel;", "getMarketScreenViewModel", "()Lcom/example/shahicripto/features/marketScreen/MarketScreenViewModel;", "setMarketScreenViewModel", "(Lcom/example/shahicripto/features/marketScreen/MarketScreenViewModel;)V", "getAboutDataFromAssest", "", "getNewsFromApi", "getTopCoinsDataBase", "initUi", "internetChecker", "context", "Landroid/content/Context;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onItemClicked", "dataCoin", "Lcom/example/shahicripto/model/local/CoinsData/CoinsDataEntitity;", "onResume", "refreshNews", "showDataINRecycler", "data", "", "app_debug"})
public final class MarketActivity extends androidx.appcompat.app.AppCompatActivity implements com.example.shahicripto.features.marketScreen.MarketAdapter.RecyclerCallback {
    public com.example.shahicripto.databinding.ActivityMarketBinding binding;
    public com.example.shahicripto.features.marketScreen.MarketScreenViewModel marketScreenViewModel;
    public java.util.ArrayList<kotlin.Pair<java.lang.String, java.lang.String>> dataNews;
    public java.util.Map<java.lang.String, com.example.shahicripto.model.local.CoinAboutItem> aboutDataMap;
    private final io.reactivex.disposables.CompositeDisposable compositeDisposable = null;
    
    public MarketActivity() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.example.shahicripto.databinding.ActivityMarketBinding getBinding() {
        return null;
    }
    
    public final void setBinding(@org.jetbrains.annotations.NotNull
    com.example.shahicripto.databinding.ActivityMarketBinding p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.example.shahicripto.features.marketScreen.MarketScreenViewModel getMarketScreenViewModel() {
        return null;
    }
    
    public final void setMarketScreenViewModel(@org.jetbrains.annotations.NotNull
    com.example.shahicripto.features.marketScreen.MarketScreenViewModel p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.ArrayList<kotlin.Pair<java.lang.String, java.lang.String>> getDataNews() {
        return null;
    }
    
    public final void setDataNews(@org.jetbrains.annotations.NotNull
    java.util.ArrayList<kotlin.Pair<java.lang.String, java.lang.String>> p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.Map<java.lang.String, com.example.shahicripto.model.local.CoinAboutItem> getAboutDataMap() {
        return null;
    }
    
    public final void setAboutDataMap(@org.jetbrains.annotations.NotNull
    java.util.Map<java.lang.String, com.example.shahicripto.model.local.CoinAboutItem> p0) {
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void internetChecker(android.content.Context context) {
    }
    
    private final void getAboutDataFromAssest() {
    }
    
    @java.lang.Override
    protected void onResume() {
    }
    
    private final void initUi() {
    }
    
    private final void getNewsFromApi() {
    }
    
    private final void refreshNews() {
    }
    
    private final void getTopCoinsDataBase() {
    }
    
    private final void showDataINRecycler(java.util.List<com.example.shahicripto.model.local.CoinsData.CoinsDataEntitity> data) {
    }
    
    @java.lang.Override
    public void onItemClicked(@org.jetbrains.annotations.NotNull
    com.example.shahicripto.model.local.CoinsData.CoinsDataEntitity dataCoin) {
    }
    
    @java.lang.Override
    protected void onDestroy() {
    }
}