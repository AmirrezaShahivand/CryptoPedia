package com.example.shahicripto.features.newsScreen;

import com.bumptech.glide.RequestManager;
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
public final class NewsActivity_MembersInjector implements MembersInjector<NewsActivity> {
  private final Provider<RequestManager> glideProvider;

  public NewsActivity_MembersInjector(Provider<RequestManager> glideProvider) {
    this.glideProvider = glideProvider;
  }

  public static MembersInjector<NewsActivity> create(Provider<RequestManager> glideProvider) {
    return new NewsActivity_MembersInjector(glideProvider);
  }

  @Override
  public void injectMembers(NewsActivity instance) {
    injectGlide(instance, glideProvider.get());
  }

  @InjectedFieldSignature("com.example.shahicripto.features.newsScreen.NewsActivity.glide")
  public static void injectGlide(NewsActivity instance, RequestManager glide) {
    instance.glide = glide;
  }
}
