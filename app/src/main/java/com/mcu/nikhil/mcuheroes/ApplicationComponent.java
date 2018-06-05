package com.mcu.nikhil.mcuheroes;

import com.mcu.nikhil.core_lib.character.cache.CacheModule;
import com.mcu.nikhil.core_lib.character.search.SearchModule;
import com.mcu.nikhil.core_lib.domain.ApiModule;
import com.mcu.nikhil.core_lib.domain.ClientModule;
import com.mcu.nikhil.mcuheroes.activity.CharacterActivity;
import com.mcu.nikhil.mcuheroes.activity.MainActivity;
import com.mcu.nikhil.mcuheroes.activity.SplashActivity;
import com.mcu.nikhil.mcuheroes.character.cache.CacheSubComponent;
import com.mcu.nikhil.mcuheroes.character.search.SearchSubComponent;
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

    void inject(SplashActivity splashActivity);

    void inject(MainActivity mainActivity);

    void inject(CharacterActivity characterActivity);

    SearchSubComponent plus(SearchModule module);

    CacheSubComponent plus(CacheModule module);
}
