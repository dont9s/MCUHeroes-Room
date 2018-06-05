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

    @Singleton
    @Provides
    Context provideContext(){return application.getApplicationContext();}

    @Singleton
    @Provides
    Resources provideResources(){return application.getResources();}
}
