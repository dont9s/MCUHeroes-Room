package com.mcu.nikhil.core_lib.character.cache;

import com.mcu.nikhil.core_lib.base.BaseView;
import com.mcu.nikhil.core_lib.database.model.CharacterModel;

import java.util.List;

interface CacheView extends BaseView {
    void setLast5CharactersCachedData(List<CharacterModel> characterModels);

    void showError(Throwable throwable);
}
