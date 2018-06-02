package com.mcu.nikhil.mcuheroes;

import android.content.Context;
import android.content.res.Resources;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AndroidModule {
    private MCUHeroesApplication application;

    public AndroidModule(MCUHeroesApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideContext(){return application.getApplicationContext();}

    @Provides
    @Singleton
    Resources provideResources(){return application.getResources();}
}
