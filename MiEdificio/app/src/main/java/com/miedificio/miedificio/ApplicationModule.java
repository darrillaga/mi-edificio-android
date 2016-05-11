package com.miedificio.miedificio;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Context context) {
        mApplication = Application.get(context);
    }

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    ApplicationComponent provideApplicationComponent() {
        return mApplication.getComponent();
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return mApplication;
    }
}
