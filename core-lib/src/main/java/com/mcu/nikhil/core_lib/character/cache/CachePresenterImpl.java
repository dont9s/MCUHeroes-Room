package com.mcu.nikhil.core_lib.character.cache;

import com.mcu.nikhil.core_lib.database.DatabaseHelper;

import java.sql.SQLException;

import javax.inject.Inject;

public class CachePresenterImpl implements CachePresenter {

    @Inject
    DatabaseHelper databaseHelper;

    private CacheView view;


    @Inject
    public CachePresenterImpl() {
    }

    @Override
    public void bind(CacheView view) {
        this.view = view;
    }

    @Override
    public void loadLast5CharactersCachedData() {
        if (view != null) {
            try {
                view.setLast5CharactersCachedData(databaseHelper.selectLast5Characters());

            }catch (SQLException e){
                view.showError(e);
            }
        }
    }

    @Override
    public void unbind() {
        view = null;
    }
}
