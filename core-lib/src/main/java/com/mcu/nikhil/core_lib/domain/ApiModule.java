package com.mcu.nikhil.core_lib.domain;

import com.google.gson.Gson;
import com.mcu.nikhil.core_lib.domain.client.MarvelApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {

    @Singleton
    @Provides
    public MarvelApi provideMarvelApiService (Retrofit retrofit){
        return  retrofit.create(MarvelApi.class);
    }


    @Singleton
    @Provides
    public Retrofit provideRetrofit(HttpUrl baseUrl, Converter.Factory converterFactory
            , CallAdapter.Factory callAdapterFactory, OkHttpClient okHttpClient){
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(callAdapterFactory)
                .client(okHttpClient)
                .build();
    }


    @Singleton
    @Provides
    public Converter.Factory provideGsonConverterFactory(Gson gson){
        return GsonConverterFactory.create(gson);
    }


    @Singleton
    @Provides
    public Gson provideGson(){return new Gson();}


    @Singleton
    @Provides
    public CallAdapter.Factory provideRxJava2CallAdapterFactory(){
        return RxJava2CallAdapterFactory.create();
    }


}


