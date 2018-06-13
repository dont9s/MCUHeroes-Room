package com.mcu.nikhil.mcuheroes.character.cache;


import dagger.Subcomponent;

@Cache
@Subcomponent(modules = {
        CacheModule.class
})
public interface CacheSubComponent {

    void inject(CacheFragment fragment);
}
