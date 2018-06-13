package com.mcu.nikhil.mcuheroes.character.cache;

import com.mcu.nikhil.mcuheroes.database.model.CharacterModel;
import com.mcu.nikhil.mcuheroes.base.BaseView;

import java.util.List;

public interface CacheView extends BaseView {
    void setLast5CharactersCachedData(List<CharacterModel> characterModels);

    void showError(Throwable throwable);
}
