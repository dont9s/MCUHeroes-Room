package com.mcu.nikhil.mcuheroes.character.cache;

import com.mcu.nikhil.mcuheroes.database.DatabaseHelper;
import com.mcu.nikhil.mcuheroes.database.model.CharacterModel;

import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CachePresenterImpl implements CachePresenter {

    @Inject
    DatabaseHelper databaseHelper;

    private Disposable cacheDisposable;
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
                cacheDisposable = databaseHelper
                        .characterDao()
                        .selectLast5Characters()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(characterModels -> view
                                .setLast5CharactersCachedData(characterModels));

            } catch (SQLException e) {
                view.showError(e);
            }
        }
    }

    @Override
    public void unbind() {
        if (cacheDisposable != null && !cacheDisposable.isDisposed()) {
            cacheDisposable.dispose();
        }
        view = null;
    }
}
