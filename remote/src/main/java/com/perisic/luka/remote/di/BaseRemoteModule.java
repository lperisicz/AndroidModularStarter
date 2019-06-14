package com.perisic.luka.remote.di;

import com.google.gson.GsonBuilder;
import com.perisic.luka.base.di.qualifiers.BaseUrl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Luka Perisic on 13.6.2019..
 */
@Module
public abstract class BaseRemoteModule {

    @Provides
    @Singleton
    static Converter.Factory provideConverterFactory() {
        return GsonConverterFactory.create(new GsonBuilder().setLenient().create());
    }

    @Provides
    @Singleton
    static OkHttpClient provideOkhttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
    }

    @Provides
    @Singleton
    static Retrofit provideRetrofit(OkHttpClient okHttpClient, Converter.Factory converterFactory, @BaseUrl String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(converterFactory)
                .client(okHttpClient)
                .build();
    }

}