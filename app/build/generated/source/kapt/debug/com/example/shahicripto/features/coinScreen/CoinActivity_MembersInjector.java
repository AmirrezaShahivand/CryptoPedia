package com.example.shahicripto.features.coinScreen;

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
public final class CoinActivity_MembersInjector implements MembersInjector<CoinActivity> {
  private final Provider<ApiService> apiServiceProvider;

  public CoinActivity_MembersInjector(Provider<ApiService> apiServiceProvider) {
    this.apiServiceProvider = apiServiceProvider;
  }

  public static MembersInjector<CoinActivity> create(Provider<ApiService> apiServiceProvider) {
    return new CoinActivity_MembersInjector(apiServiceProvider);
  }

  @Override
  public void injectMembers(CoinActivity instance) {
    injectApiService(instance, apiServiceProvider.get());
  }

  @InjectedFieldSignature("com.example.shahicripto.features.coinScreen.CoinActivity.apiService")
  public static void injectApiService(CoinActivity instance, ApiService apiService) {
    instance.apiService = apiService;
  }
}
