package com.mcu.nikhil.mcuheroes.character.cache;

import dagger.Module;
import dagger.Provides;

@Module
public class CacheModule {

    @Cache
    @Provides
    public CachePresenter providePresenter(CachePresenterImpl presenter){
        return ((CachePresenter) presenter);
    }
}
