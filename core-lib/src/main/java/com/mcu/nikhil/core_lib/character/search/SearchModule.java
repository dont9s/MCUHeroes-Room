package com.mcu.nikhil.core_lib.character.search;

import dagger.Module;
import dagger.Provides;

@Module
public class SearchModule {

    @Provides
    @Search
    public SearchInteractor provideInteractor(SearchInteractorImpl interactor){
        return interactor;
    }

    @Provides
    @Search
    public SearchPresenter providePresenter(SearchPresenterImpl presenter){
        return presenter;
    }
}
