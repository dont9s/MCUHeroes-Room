package com.mcu.nikhil.mcuheroes.character.search;

import com.mcu.nikhil.mcuheroes.domain.model.MarvelCharactersResponse;
import com.mcu.nikhil.mcuheroes.base.BaseInteractor;

import io.reactivex.Observable;


public interface SearchInteractor extends BaseInteractor {
    Observable<MarvelCharactersResponse> loadCharacter(String query, String privateKey,
                                                       String publicKey, long timestamp);
}
