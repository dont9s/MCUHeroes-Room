package com.mcu.nikhil.core_lib.character.cache;

import dagger.Module;
import dagger.Provides;

@Module
public class CacheModule {
    @Provides
    @Cache
    public CachePresenter providePresenter(CachePresenterImpl presenter){
        return presenter;
    }
}
