package com.mcu.nikhil.mcuheroes;

import com.mcu.nikhil.core_lib.domain.ApiModule;
import com.mcu.nikhil.core_lib.domain.ClientModule;
import com.mcu.nikhil.mcuheroes.database.DatabaseModule;

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
