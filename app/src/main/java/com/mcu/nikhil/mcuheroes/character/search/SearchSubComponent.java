package com.mcu.nikhil.mcuheroes.character.search;

import android.content.Context;
import android.content.res.Resources;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.mcu.nikhil.core_lib.character.search.Search;
import com.mcu.nikhil.core_lib.character.search.SearchModule;
import com.mcu.nikhil.core_lib.character.search.SearchPresenter;
import com.mcu.nikhil.core_lib.character.search.SearchPresenterImpl;
import com.mcu.nikhil.mcuheroes.ApplicationComponent;

import dagger.Component;
import dagger.Subcomponent;

@Search
@Subcomponent(modules = {
        SearchModule.class
})
public interface SearchSubComponent {
    void inject(SearchFragment fragment);
}
