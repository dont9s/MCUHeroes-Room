package com.mcu.nikhil.mcuheroes.character.cache;


import com.mcu.nikhil.core_lib.character.cache.Cache;
import com.mcu.nikhil.core_lib.character.cache.CacheModule;

import dagger.Subcomponent;

@Cache
@Subcomponent(modules = {
        CacheModule.class
})
public interface CacheSubComponent {

    void inject(CacheFragment fragment);
}
