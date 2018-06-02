package com.mcu.nikhil.mcuheroes;

import com.mcu.nikhil.mcuheroes.di.AndroidModule;
import com.mcu.nikhil.mcuheroes.di.ApiModule;
import com.mcu.nikhil.mcuheroes.di.ApplicationModule;
import com.mcu.nikhil.mcuheroes.di.ClientModule;
import com.mcu.nikhil.mcuheroes.di.DatabaseModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        AndroidModule.class,
        ApplicationModule.class,
        ApiModule.class,
        ClientModule.class,
        DatabaseModule.class
})
public interface ApplicationComponent {
}
