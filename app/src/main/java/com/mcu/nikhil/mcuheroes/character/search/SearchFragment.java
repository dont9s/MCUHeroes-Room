package com.mcu.nikhil.mcuheroes.character.search;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.mcu.nikhil.core_lib.character.search.ApiResponseCodeException;
import com.mcu.nikhil.core_lib.character.search.SearchPresenter;
import com.mcu.nikhil.core_lib.character.search.SearchPresenterImpl;
import com.mcu.nikhil.core_lib.character.search.SearchView;
import com.mcu.nikhil.core_lib.database.model.CharacterModel;
import com.mcu.nikhil.mcuheroes.MCUHeroesApplication;
import com.mcu.nikhil.mcuheroes.R;
import com.mcu.nikhil.mcuheroes.activity.MainActivity;
import com.mcu.nikhil.mcuheroes.base.BaseFragment;
import com.mcu.nikhil.mcuheroes.util.AppSchedulerProvider;
import com.mirhoseini.utils.Utils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import timber.log.Timber;

public class SearchFragment extends BaseFragment implements SearchView {

    //injecting dependencies via dagger
    @Inject
    Context context;
    @Inject
    Resources resources;
    @Inject
    FirebaseAnalytics firebaseAnalytics;
    @Inject
    SearchPresenter presenter;

    //injecting view via Butterknife
    @BindView(R.id.character)
    protected AutoCompleteTextView actvCharacter;
    @BindView(R.id.show)
    protected Button btShow;

    private SearchEventListener eventListener;

    PublishSubject<CharacterModel> notifyCharacter = PublishSubject.create();
    PublishSubject<String> notifyMessage = PublishSubject.create();
    PublishSubject<Boolean> notifyOffline = PublishSubject.create();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SearchFragment() {
    }

    public static SearchFragment newInstance(){
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    @OnEditorAction(R.id.character)
    boolean onEditorAction(EditText v , int actionId, KeyEvent event){
        if(actionId == EditorInfo.IME_ACTION_SEARCH ||
                (event.getAction() == KeyEvent.ACTION_DOWN &&
                event.getAction() == KeyEvent.KEYCODE_ENTER)){
            onShowClick(v);
            return true;
        }
        return false;
    }

    @OnClick(R.id.show)
    void onShowClick(View view) {
        actvCharacter.setError(null);
        Utils.hideKeyboard(context, actvCharacter);

        String query = actvCharacter.getText().toString().trim();

        if(presenter.isQueryValid(query)){
            logFirebaseAnalyticsSearchEvent(query);
            presenter.doSearch(Utils.isConnected(context) , query,
                    Utils.getCurrentTimestamp());
        }else {
            actvCharacter.setError(resources.getString(R.string.character_error));
            actvCharacter.requestFocus();
        }

    }

    private void logFirebaseAnalyticsSearchEvent(String query) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SEARCH_TERM, query);
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SEARCH, bundle);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        presenter.bind(this);
        if (context instanceof  SearchEventListener) {
            eventListener = ((SearchEventListener) context);
        }
    }


    @Override
    protected void injectDependencies(MCUHeroesApplication application) {
        application.getSearchComponent().inject(this);
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.loadCharactersCachedData();
    }

    @Override
    public void setCharactersCachedData(List<CharacterModel> characters) {
        actvCharacter.setAdapter(new ArrayAdapter<>(context,
                android.R.layout.simple_list_item_1, characters));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        presenter.unbind();
    }

    @Override
    public void showMessage(String message) {
        notifyMessage.onNext(message);
    }

    @Override
    public void showOfflineMessage(boolean isCritical) {
        notifyOffline.onNext(isCritical);
    }

    @Override
    public void showError(Throwable throwable) {
        Timber.e(throwable, getString(R.string.error));
        showRetryMessage(throwable);
    }

    @Override
    public void showRetryMessage(Throwable throwable) {
        Timber.e(throwable, getString(R.string.retry_error));

        Snackbar.make(actvCharacter, resources.getString(R.string.retry_message),
                Snackbar.LENGTH_LONG)
                .setAction(R.string.retry, this::onShowClick)
                .show();
    }

    @Override
    public void showQueryNoResult() {
        showMessage(getString(R.string.no_result));
    }

    @Override
    public void showProgress() {
        if (eventListener != null)
            eventListener.showProgress();
    }

    @Override
    public void hideProgress() {
        if (eventListener != null)
            eventListener.hideProgress();
    }

    @Override
    public void showServiceError(ApiResponseCodeException throwable) {
        Timber.e(throwable, getString(R.string.service_error));
        showRetryMessage(throwable);
    }

    @Override
    public void showQueryError(Throwable throwable) {
        Timber.e(throwable, getString(R.string.query_error));
        showRetryMessage(throwable);
    }

    @Override
    public void showCharacter(CharacterModel character) {
        logFirebaseAnalyticsSelectEvent(character);
        notifyCharacter.onNext(character);
    }

    private void logFirebaseAnalyticsSelectEvent(CharacterModel character) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, character.getId() + "");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, character.getName());
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }

    public Observable<CharacterModel> characterObservable(){
        //renamed version of asObservable()
        return notifyCharacter.hide();
    }

    public Observable<String> messageObservable(){
        return notifyMessage.hide();
    }

    public Observable<Boolean> offlineObservable(){
        return notifyOffline.hide();
    }

    public interface SearchEventListener{
        void showProgress();
        void hideProgress();
    }

}
