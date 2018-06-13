package com.mcu.nikhil.mcuheroes.character.cache;


import com.mcu.nikhil.mcuheroes.base.BasePresenter;

public interface CachePresenter extends BasePresenter<CacheView> {

    void loadLast5CharactersCachedData();

}
