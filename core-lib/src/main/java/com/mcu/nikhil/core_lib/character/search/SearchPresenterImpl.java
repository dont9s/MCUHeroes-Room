package com.mcu.nikhil.core_lib.character.search;

import com.mcu.nikhil.core_lib.database.DatabaseHelper;
import com.mcu.nikhil.core_lib.database.mapper.Mapper;
import com.mcu.nikhil.core_lib.database.model.CharacterModel;
import com.mcu.nikhil.core_lib.domain.model.MarvelCharactersResponse;
import com.mcu.nikhil.core_lib.util.Constants;
import com.mcu.nikhil.core_lib.util.SchedulerProvider;

import java.sql.SQLException;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class SearchPresenterImpl implements SearchPresenter {

    @Inject
    SearchInteractor interactor;
    @Inject
    DatabaseHelper databaseHelper;

    private SearchView view;
    private SchedulerProvider scheduler;
    private Observable<MarvelCharactersResponse> characterObservable = Observable.empty();
    private Disposable characterDisposable;

    @Inject
    SearchPresenterImpl(SchedulerProvider scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public void bind(SearchView view) {
        this.view = view;
    }

    @Override
    public void doSearch(boolean isConnected, String query, long timestamp) {
        if (null != view) {
            view.showProgress();
        }

        characterDisposable = interactor.loadCharacter(query,
                Constants.PRIVATE_KEY, Constants.PUBLIC_KEY, timestamp)
                //check if result code is ok
                .map(characterResponse -> {
                    if (Constants.CODE_OK == characterResponse.getCode())
                        return characterResponse;
                    else
                        throw Exceptions.propagate(new ApiResponseCodeException(characterResponse.getCode(),
                                characterResponse.getStatus()));

                })
                //check if there is any result
                .map(characterResponse -> {
                    if (characterResponse.getData().getCount() > 0)
                        return characterResponse;
                    else
                        throw Exceptions.propagate(new NoSuchCharacterException());
                })
                //map character response to character model
                .map(Mapper::mapCharacterResponseToCharacter)
                //cache data on database
                .map(character -> {
                    try {
                        databaseHelper.addCharacter(character);
                    } catch (SQLException e) {
                        throw Exceptions.propagate(e);
                    }
                    return character;
                })
                .observeOn(scheduler.mainThread())
                .subscribe(character -> {
                    if (view != null) {
                        view.hideProgress();
                        view.showCharacter(character);

                        if (!isConnected) {
                            view.showOfflineMessage(false);
                        }
                    }
                }
                        ,
                        //handle exceptions
                        throwable -> {
                            if (view != null) {
                                view.hideProgress();

                                if (isConnected) {
                                    if (throwable instanceof ApiResponseCodeException)
                                        view.showServiceError(((ApiResponseCodeException) throwable));
                                    else if (throwable instanceof NoSuchCharacterException)
                                        view.showQueryNoResult();
                                    else
                                        view.showRetryMessage(throwable);
                                } else {
                                    view.showOfflineMessage(true);
                                }
                            }
                        });
    }

    @Override
    public boolean isQueryValid(String query) {
        return null != query && !query.isEmpty();
    }

    @Override
    public void loadCharactersCachedData() {
        if (view != null) {
            try {
                view.setCharactersCachedData(databaseHelper.selectAllCharacters());
            }catch (SQLException e){
                view.showError(e);
            }
        }
    }


    @Override
    public void unbind() {
        if (characterDisposable != null && !characterDisposable.isDisposed()) {
            characterDisposable.dispose();
        }
        interactor.unbind();
        view = null;
    }
}
