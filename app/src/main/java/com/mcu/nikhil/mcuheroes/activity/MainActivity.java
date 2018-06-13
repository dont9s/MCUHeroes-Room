package com.mcu.nikhil.mcuheroes.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mcu.nikhil.mcuheroes.database.model.CharacterModel;
import com.mcu.nikhil.mcuheroes.ApplicationComponent;
import com.mcu.nikhil.mcuheroes.MCUHeroesApplication;
import com.mcu.nikhil.mcuheroes.R;
import com.mcu.nikhil.mcuheroes.base.BaseActivity;
import com.mcu.nikhil.mcuheroes.character.cache.CacheFragment;
import com.mcu.nikhil.mcuheroes.character.search.SearchFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public class MainActivity extends BaseActivity implements SearchFragment.SearchEventListener {

    public static final String TAG_SEARCH_FRAGMENT = "search_fragment";
    public static final String TAG_CACHE_FRAGMENT = "cache_fragment";

    //injecting dependencies via dagger
    @Inject
    Context context;

    //injecting views via butterknife
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.indeterminateLoading)
    ProgressBar indeterminateLoading;


    CompositeDisposable subscriptions;
    private SearchFragment searchFragment;
    private CacheFragment cacheFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //inject view using butterknife
        ButterKnife.bind(this);

        setupToolbar();
        if (savedInstanceState == null) {
            searchFragment = SearchFragment.newInstance();
            cacheFragment = CacheFragment.newInstance();
            attachFragments();
        }else {
            searchFragment = ((SearchFragment) getSupportFragmentManager()
                    .findFragmentByTag(TAG_SEARCH_FRAGMENT));
            cacheFragment = ((CacheFragment) getSupportFragmentManager()
                    .findFragmentByTag(TAG_CACHE_FRAGMENT));
        }
        Timber.d(getString(R.string.main_activity_created));
    }

    @Override
    protected void injectDependencies(MCUHeroesApplication application, ApplicationComponent component) {
        component.inject(this);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.logo);
        getSupportActionBar().setTitle(R.string.main_title);
    }

    private void attachFragments() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.search_fragment, searchFragment, TAG_SEARCH_FRAGMENT);
        transaction.replace(R.id.cache_fragment, cacheFragment, TAG_CACHE_FRAGMENT);
        transaction.commitAllowingStateLoss();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (subscriptions == null || subscriptions.isDisposed()) {
            subscriptions = new CompositeDisposable();
        }

        subscriptions.addAll(
                searchFragment.characterObservable()
                .subscribe(this::showCharacter),
                searchFragment.messageObservable()
                .subscribe(this::showMessage),
                searchFragment.offlineObservable()
                .subscribe(this::showOfflineMessage),
                cacheFragment.characterObservable()
                .subscribe(this::showCharacter),
                cacheFragment.messageObservable()
                .subscribe(this::showMessage),
                cacheFragment.offlineObservable()
                .subscribe(this::showOfflineMessage)
        );
    }

    private void showMessage(String s) {
        Timber.d("Showing message: %s", s);
        Toast.makeText(context, s, Toast.LENGTH_LONG).show();
    }

    private void showOfflineMessage(Boolean aBoolean) {
        Timber.d("Showing offline message");
        Snackbar.make(toolbar,R.string.offline_message, Snackbar.LENGTH_LONG)
                .setAction(R.string.go_online, v->startActivity(new Intent(
                        Settings.ACTION_WIFI_SETTINGS)))
                .setActionTextColor(Color.GREEN)
                .show();
    }

    private void showCharacter(CharacterModel characterModel) {
        startActivity(CharacterActivity.newIntent(this, characterModel));
    }

    @Override
    protected void onPause() {
        super.onPause();
        subscriptions.dispose();
    }

    @Override
    protected void releaseSubComponents(MCUHeroesApplication application) {
        application.releaseCacheSubComponent();
        application.releaseSearchSubComponent();
    }

    @Override
    public void showProgress() {
        if (indeterminateLoading.getVisibility() == View.GONE){
            indeterminateLoading.setVisibility(View.VISIBLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }

    }

    @Override
    public void hideProgress() {
        if (indeterminateLoading.getVisibility() == View.VISIBLE){
            indeterminateLoading.setVisibility(View.GONE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }
}
