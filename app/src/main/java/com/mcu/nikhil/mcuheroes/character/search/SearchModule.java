package com.mcu.nikhil.mcuheroes.character.search;

import dagger.Module;
import dagger.Provides;

@Module
public class SearchModule {


    @Search
    @Provides
    public SearchInteractor provideInteractor(SearchInteractorImpl interactor){
        return ((SearchInteractor) interactor);
    }


    @Search
    @Provides
    public SearchPresenter providePresenter(SearchPresenterImpl presenter){
        return ((SearchPresenter) presenter);
    }
}
