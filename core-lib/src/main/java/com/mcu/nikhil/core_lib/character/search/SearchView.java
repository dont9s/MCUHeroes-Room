package com.mcu.nikhil.core_lib.character.search;

import com.mcu.nikhil.core_lib.base.BaseView;
import com.mcu.nikhil.core_lib.database.model.CharacterModel;

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
