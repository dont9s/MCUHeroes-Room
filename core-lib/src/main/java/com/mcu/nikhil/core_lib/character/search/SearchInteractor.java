package com.mcu.nikhil.core_lib.character.search;

import com.mcu.nikhil.core_lib.base.BaseInteractor;
import com.mcu.nikhil.core_lib.domain.model.MarvelCharactersResponse;

import io.reactivex.Observable;


public interface SearchInteractor extends BaseInteractor {
    Observable<MarvelCharactersResponse> loadCharacter(String query, String privateKey,
                                                       String publicKey, long timestamp);
}
