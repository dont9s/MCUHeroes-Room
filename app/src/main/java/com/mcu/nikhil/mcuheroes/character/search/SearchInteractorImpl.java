package com.mcu.nikhil.mcuheroes.character.search;

import com.mcu.nikhil.mcuheroes.domain.client.MarvelApi;
import com.mcu.nikhil.mcuheroes.domain.model.MarvelCharactersResponse;
import com.mcu.nikhil.mcuheroes.util.HashGenerator;
import com.mcu.nikhil.mcuheroes.util.SchedulerProvider;


import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

@Search
public class SearchInteractorImpl implements SearchInteractor {

    private MarvelApi api;
    private SchedulerProvider scheduler;
    private Observable<MarvelCharactersResponse> characterObservable;
    private Disposable characterDisposable;


    @Inject
    SearchInteractorImpl(MarvelApi api, SchedulerProvider scheduler){
        this.api = api;
        this.scheduler = scheduler;
    }

    @Override
    public Observable<MarvelCharactersResponse> loadCharacter(String query, String privateKey,
                                                              String publicKey, long timestamp) {

        //generate hash using timestamp and keys
        String hash = HashGenerator.generate(timestamp, privateKey, publicKey);
        characterObservable =  api.getCharacters(query, publicKey,hash,timestamp)
                    .subscribeOn(scheduler.backgroundThread());

        characterDisposable = characterObservable
                .subscribe();

        return characterObservable;
    }

    @Override
    public void unbind() {
        if (characterDisposable != null && !characterDisposable.isDisposed()) {
            characterDisposable.dispose();
        }
    }
}
