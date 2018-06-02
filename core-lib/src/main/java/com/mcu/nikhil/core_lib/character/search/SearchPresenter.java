package com.mcu.nikhil.core_lib.character.search;

import com.mcu.nikhil.core_lib.base.BasePresenter;

public interface SearchPresenter extends BasePresenter<SearchView> {

    void doSearch(boolean isConnected, String query, long timestamp);

    boolean isQueryValid(String query);

    void loadCharactersCachedData();
}
