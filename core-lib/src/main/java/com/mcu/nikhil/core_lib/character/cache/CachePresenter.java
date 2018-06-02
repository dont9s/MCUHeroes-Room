package com.mcu.nikhil.core_lib.character.cache;

import com.mcu.nikhil.core_lib.base.BasePresenter;

public interface CachePresenter extends BasePresenter<CacheView> {

    void loadLast5CharactersCachedData();

}
