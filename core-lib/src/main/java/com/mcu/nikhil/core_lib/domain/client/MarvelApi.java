package com.mcu.nikhil.core_lib.domain.client;

import com.mcu.nikhil.core_lib.domain.model.MarvelCharactersResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MarvelApi {

    String NAME = "name";
    String API_KEY = "apikey";
    String HASH = "hash";
    String TIMESTAMP = "ts";
    // http://gateway.marvel.com:80/v1/public/characters?name=Iron%20Man&apikey=PUBLIC_API_KEY&hash=HASH&ts=TIMESTAMP

    @GET("/v1/public/characters")
    Observable<MarvelCharactersResponse> getCharacters(
            @Query(NAME) String query,
            @Query(API_KEY) String publicKey,
            @Query(HASH) String hash,
            @Query(TIMESTAMP) long timestamp);
}
