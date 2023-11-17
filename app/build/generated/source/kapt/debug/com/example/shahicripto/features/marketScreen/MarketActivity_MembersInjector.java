package com.example.shahicripto.features.marketScreen;

import com.bumptech.glide.RequestManager;
import com.example.shahicripto.model.api.ApiService;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class MarketActivity_MembersInjector implements MembersInjector<MarketActivity> {
  private final Provider<RequestManager> glideProvider;

  private final Provider<ApiService> apiServiceProvider;

  public MarketActivity_MembersInjector(Provider<RequestManager> glideProvider,
      Provider<ApiService> apiServiceProvider) {
    this.glideProvider = glideProvider;
    this.apiServiceProvider = apiServiceProvider;
  }

  public static MembersInjector<MarketActivity> create(Provider<RequestManager> glideProvider,
      Provider<ApiService> apiServiceProvider) {
    return new MarketActivity_MembersInjector(glideProvider, apiServiceProvider);
  }

  @Override
  public void injectMembers(MarketActivity instance) {
    injectGlide(instance, glideProvider.get());
    injectApiService(instance, apiServiceProvider.get());
  }

  @InjectedFieldSignature("com.example.shahicripto.features.marketScreen.MarketActivity.glide")
  public static void injectGlide(MarketActivity instance, RequestManager glide) {
    instance.glide = glide;
  }

  @InjectedFieldSignature("com.example.shahicripto.features.marketScreen.MarketActivity.apiService")
  public static void injectApiService(MarketActivity instance, ApiService apiService) {
    instance.apiService = apiService;
  }
}
