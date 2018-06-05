package com.mcu.nikhil.mcuheroes.di;

import android.content.Context;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.mcu.nikhil.core_lib.util.Constants;
import com.mcu.nikhil.core_lib.util.SchedulerProvider;
import com.mcu.nikhil.core_lib.util.StateManager;
import com.mcu.nikhil.mcuheroes.BuildConfig;
import com.mcu.nikhil.mcuheroes.util.AppConstants;
import com.mcu.nikhil.mcuheroes.util.AppSchedulerProvider;
import com.mcu.nikhil.mcuheroes.util.StateManagerImpl;
import com.mirhoseini.utils.Utils;

import java.io.File;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;

@Module
public class ApplicationModule {

    @Singleton
    @Provides
    @Named("isDebug")
    boolean provideIsDebug(){return BuildConfig.DEBUG;}



    @Singleton
    @Provides
    @Named("networkTimeoutInSeconds")
    int provideNetworkTimeoutInSeconds(){return AppConstants.NETWORK_CONNECTION_TIMEOUT;};

    @Singleton
    @Provides
    HttpUrl provideEndopint(){return HttpUrl.parse(Constants.BASE_URL);}

    @Singleton
    @Provides
    SchedulerProvider provideAppScheduler(){return new AppSchedulerProvider();}

    @Singleton
    @Provides
    @Named("cacheSize")
    long provideCacheSize(){return AppConstants.CACHE_SIZE;}

    @Singleton
    @Provides
    @Named("cacheMaxAge")
    int provideCacheMaxAgeMinutes(){return AppConstants.CACHE_MAX_AGE;}

    @Singleton
    @Provides
    @Named("cacheMaxStale")
    int provideCacheMaxStaleDays(){return AppConstants.CACHE_MAX_STALE;}

    @Singleton
    @Provides
    @Named("retryCount")
    public int provideApiRetryCount(){return AppConstants.API_RETRY_COUNT;}

    @Singleton
    @Provides
    @Named("cacheDir")
    File provideCacheDir(Context context){return  context.getCacheDir();}

    @Provides
    @Named("isConnect")
    boolean provideIsConnect(Context context){return Utils.isConnected(context);}

    @Provides
    FirebaseAnalytics provideFirebaseAnalytics(Context  context){
        return FirebaseAnalytics.getInstance(context);
    }

    @Singleton
    @Provides
    public StateManager provideStateManager(StateManagerImpl manager){return manager;}
}
