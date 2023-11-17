package com.example.shahicripto.di.module;

import android.content.Context;
import com.bumptech.glide.RequestManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class ImageModule_ProvidesGlideFactory implements Factory<RequestManager> {
  private final Provider<Context> contextProvider;

  public ImageModule_ProvidesGlideFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public RequestManager get() {
    return providesGlide(contextProvider.get());
  }

  public static ImageModule_ProvidesGlideFactory create(Provider<Context> contextProvider) {
    return new ImageModule_ProvidesGlideFactory(contextProvider);
  }

  public static RequestManager providesGlide(Context context) {
    return Preconditions.checkNotNullFromProvides(ImageModule.INSTANCE.providesGlide(context));
  }
}
