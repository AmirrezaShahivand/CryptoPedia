package com.example.shahicripto.features.marketScreen.marketFragment;

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
public final class MarketFragment_MembersInjector implements MembersInjector<MarketFragment> {
  private final Provider<RequestManager> glideProvider;

  private final Provider<ApiService> apiServiceProvider;

  public MarketFragment_MembersInjector(Provider<RequestManager> glideProvider,
      Provider<ApiService> apiServiceProvider) {
    this.glideProvider = glideProvider;
    this.apiServiceProvider = apiServiceProvider;
  }

  public static MembersInjector<MarketFragment> create(Provider<RequestManager> glideProvider,
      Provider<ApiService> apiServiceProvider) {
    return new MarketFragment_MembersInjector(glideProvider, apiServiceProvider);
  }

  @Override
  public void injectMembers(MarketFragment instance) {
    injectGlide(instance, glideProvider.get());
    injectApiService(instance, apiServiceProvider.get());
  }

  @InjectedFieldSignature("com.example.shahicripto.features.marketScreen.marketFragment.MarketFragment.glide")
  public static void injectGlide(MarketFragment instance, RequestManager glide) {
    instance.glide = glide;
  }

  @InjectedFieldSignature("com.example.shahicripto.features.marketScreen.marketFragment.MarketFragment.apiService")
  public static void injectApiService(MarketFragment instance, ApiService apiService) {
    instance.apiService = apiService;
  }
}
