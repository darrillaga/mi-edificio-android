package com.miedificio.miedificio.networking;

import com.miedificio.miedificio.BuildConfig;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

import static com.miedificio.miedificio.networking.RetrofitHelper.buildObjectMapper;

@Module
public class RestServicesModule {

    @Provides
    @Singleton
    MiEdificioServerClient provideServiceClient() {

        OkHttpClient okHttpClient = new OkHttpClient();

        okHttpClient.interceptors().add(new RetrofitHelper.LoggingInterceptor());

        Retrofit retrofit = new Retrofit.Builder().
                client(okHttpClient).
                addConverterFactory(JacksonConverterFactory.create(buildObjectMapper())).
                addCallAdapterFactory(RxJavaCallAdapterFactory.create()).
                baseUrl(BuildConfig.API_PATH).build();

        return retrofit.create(MiEdificioServerClient.class);
    }
}