package com.mcu.nikhil.mcuheroes.character.search;


import com.mcu.nikhil.mcuheroes.base.BasePresenter;

public interface SearchPresenter extends BasePresenter<SearchView> {

    void doSearch(boolean isConnected, String query, long timestamp);

    boolean isQueryValid(String query);

    void loadCharactersCachedData();
}
