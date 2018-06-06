package com.mcu.nikhil.mcuheroes.character.cache;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mcu.nikhil.core_lib.character.cache.CachePresenter;
import com.mcu.nikhil.core_lib.character.cache.CacheView;
import com.mcu.nikhil.core_lib.database.model.CharacterModel;
import com.mcu.nikhil.mcuheroes.MCUHeroesApplication;
import com.mcu.nikhil.mcuheroes.R;
import com.mcu.nikhil.mcuheroes.base.BaseFragment;
import com.mcu.nikhil.mcuheroes.character.cache.adapter.CharacterRecyclerViewAdapter;
import com.mcu.nikhil.mcuheroes.util.AppConstants;
import com.mcu.nikhil.mcuheroes.util.GridSpacingItemDecoration;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.PublishSubject;
import timber.log.Timber;

public class CacheFragment extends BaseFragment implements CacheView {

    public static final int COLUMN_COUNT =  2;
    //injecting Dependencies via Dagger
    @Inject
    Context context;
    @Inject
    CachePresenter presenter;
    @Inject
    CharacterRecyclerViewAdapter adapter;
    //injecting view via butterknife
    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.empty)
    ViewGroup empty;

    CompositeDisposable subscriptions = new CompositeDisposable();
    GridLayoutManager gridLayoutManager;
    GridSpacingItemDecoration gridSpacingItemDecoration;

    PublishSubject<CharacterModel> notifyCharacter = PublishSubject.create();
    PublishSubject<String> notifyMessage = PublishSubject.create();
    PublishSubject<Boolean> notifyOffline = PublishSubject.create();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    @Inject
    public CacheFragment() {
    }

    public static CacheFragment newInstance() {
        CacheFragment fragment = new CacheFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        presenter.bind(this);
        subscriptions.add(adapter.asObservable().subscribe(notifyCharacter::onNext));
    }

    @Override
    protected void injectDependencies(MCUHeroesApplication application) {
        application.getCacheSubComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cache , container, false);
        ButterKnife.bind(this, view);
        initRecyclerView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.loadLast5CharactersCachedData();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        presenter.unbind();
        subscriptions.dispose();
    }

    @Override
    public void showMessage(String message) {
        notifyMessage.onNext(message);
    }

    @Override
    public void showOfflineMessage(boolean isCritical) {
        notifyOffline.onNext(isCritical);
    }

    private void initRecyclerView() {
        initLayoutanager();
        initGridSpacingItemDecorator();

        list.setLayoutManager(gridLayoutManager);
        list.addItemDecoration(gridSpacingItemDecoration);
    }

    private void initLayoutanager() {
        gridLayoutManager = new GridLayoutManager(context, COLUMN_COUNT);
        // Create a custom SpanSizeLookup where the first item spans both columns
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == 0 ? COLUMN_COUNT : 1;
            }
        });
    }

    private void initGridSpacingItemDecorator() {
        gridSpacingItemDecoration = new GridSpacingItemDecoration(COLUMN_COUNT,
                AppConstants.RECYCLER_VIEW_ITEM_SPACE, true, 1);
    }

    @Override
    public void setLast5CharactersCachedData(List<CharacterModel> characterModels) {
        if(characterModels.size()>0){
            list.setVisibility(View.VISIBLE);
            empty.setVisibility(View.GONE);

            adapter.setCharacters(characterModels);
            list.setAdapter(adapter);
        }else {
            list.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showError(Throwable throwable) {
        Timber.e(throwable, getString(R.string.error));
        Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_LONG).show();
    }

    public Observable<CharacterModel> characterObservable(){
        return notifyCharacter.hide();
    }

    public Observable<String> messageObservable(){return notifyMessage.hide();}

    public Observable<Boolean> offlineObservable(){return notifyOffline.hide();}
}
