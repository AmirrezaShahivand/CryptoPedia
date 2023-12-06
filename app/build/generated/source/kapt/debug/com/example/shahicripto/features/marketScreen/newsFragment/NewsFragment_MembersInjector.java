package com.example.shahicripto.features.marketScreen.newsFragment;

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
public final class NewsFragment_MembersInjector implements MembersInjector<NewsFragment> {
  private final Provider<RequestManager> glideProvider;

  private final Provider<ApiService> apiServiceProvider;

  public NewsFragment_MembersInjector(Provider<RequestManager> glideProvider,
      Provider<ApiService> apiServiceProvider) {
    this.glideProvider = glideProvider;
    this.apiServiceProvider = apiServiceProvider;
  }

  public static MembersInjector<NewsFragment> create(Provider<RequestManager> glideProvider,
      Provider<ApiService> apiServiceProvider) {
    return new NewsFragment_MembersInjector(glideProvider, apiServiceProvider);
  }

  @Override
  public void injectMembers(NewsFragment instance) {
    injectGlide(instance, glideProvider.get());
    injectApiService(instance, apiServiceProvider.get());
  }

  @InjectedFieldSignature("com.example.shahicripto.features.marketScreen.newsFragment.NewsFragment.glide")
  public static void injectGlide(NewsFragment instance, RequestManager glide) {
    instance.glide = glide;
  }

  @InjectedFieldSignature("com.example.shahicripto.features.marketScreen.newsFragment.NewsFragment.apiService")
  public static void injectApiService(NewsFragment instance, ApiService apiService) {
    instance.apiService = apiService;
  }
}
