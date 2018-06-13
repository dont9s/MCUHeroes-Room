package com.mcu.nikhil.mcuheroes.character.search;

import com.mcu.nikhil.mcuheroes.database.model.CharacterModel;
import com.mcu.nikhil.mcuheroes.base.BaseView;

import java.util.List;

public interface SearchView extends BaseView {
    void showProgress();

    void hideProgress();

    void showQueryError(Throwable throwable);

    void showCharacter(CharacterModel character);

    void showRetryMessage(Throwable throwable);

    void showQueryNoResult();

    void setCharactersCachedData(List<CharacterModel> characters);

    void showError(Throwable throwable);

    void showServiceError(ApiResponseCodeException throwable);
}
