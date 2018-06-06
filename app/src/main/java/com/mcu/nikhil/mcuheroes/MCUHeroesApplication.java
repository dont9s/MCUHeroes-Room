package com.mcu.nikhil.mcuheroes;

import android.app.Application;
import android.content.Context;

import com.mcu.nikhil.core_lib.character.cache.CacheModule;
import com.mcu.nikhil.core_lib.character.search.SearchModule;
import com.mcu.nikhil.mcuheroes.character.cache.CacheSubComponent;
import com.mcu.nikhil.mcuheroes.character.search.SearchSubComponent;
import com.mcu.nikhil.mcuheroes.di.AndroidModule;

public abstract class MCUHeroesApplication extends Application {

    private static ApplicationComponent component;
    private SearchSubComponent searchSubComponent;
    private CacheSubComponent cacheSubComponent;

    public static ApplicationComponent getComponent(){return component;}

    public static MCUHeroesApplication get(Context context){
        return ((MCUHeroesApplication) context.getApplicationContext());
    }

    public CacheSubComponent getCacheSubComponent() {
        if (cacheSubComponent == null) {
            createCacheSubComponent();
        }
        return cacheSubComponent;
    }

    public CacheSubComponent createCacheSubComponent() {
        cacheSubComponent = component.plus(new CacheModule());
        return cacheSubComponent;
    }

    public void releaseCacheSubComponent() {
        cacheSubComponent = null;
    }

    public SearchSubComponent getSearchSubComponent(){
        if (searchSubComponent == null) {
            createSearchSubComponent();
        }
        return searchSubComponent;
    }
    public SearchSubComponent createSearchSubComponent() {
        searchSubComponent = component.plus(new SearchModule());
        return searchSubComponent;
    }
    public void releaseSearchSubComponent(){
        searchSubComponent = null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        initApplication();

        component = createComponent();
    }

    public ApplicationComponent createComponent(){
        return DaggerApplicationComponent.builder()
                .androidModule(new AndroidModule(this))
                .build();
    }

    public abstract void initApplication();
}
