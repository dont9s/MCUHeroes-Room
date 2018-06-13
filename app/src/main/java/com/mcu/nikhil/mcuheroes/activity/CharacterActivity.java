package com.mcu.nikhil.mcuheroes.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.mcu.nikhil.mcuheroes.database.model.CharacterModel;
import com.mcu.nikhil.mcuheroes.ApplicationComponent;
import com.mcu.nikhil.mcuheroes.BR;
import com.mcu.nikhil.mcuheroes.MCUHeroesApplication;
import com.mcu.nikhil.mcuheroes.R;
import com.mcu.nikhil.mcuheroes.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class CharacterActivity extends BaseActivity{

    private static final String ARG_CHARACTER = "characterModel";

    //injecting views via ButterKnife
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public static Intent newIntent(Context context, CharacterModel character){
        Intent  intent = new Intent(context, CharacterActivity.class);
        intent.putExtra(ARG_CHARACTER, character);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding binding = DataBindingUtil.setContentView(this,
                R.layout.activity_character);

        //inject views via butterknife
        ButterKnife.bind(this);
        if (getIntent() == null || getIntent().getExtras() == null ||
                getIntent().getExtras().getSerializable(ARG_CHARACTER) == null) {
            finish();
            return;
        }
        //get args
        CharacterModel character = ((CharacterModel) getIntent()
                .getExtras().getSerializable(ARG_CHARACTER));

        //bind values using Android Binding
        binding.setVariable(BR.character, character);
        setupToolbar(character.getName());
        Timber.d(getString(R.string.character_activity_created));
    }

    @Override
    protected void injectDependencies(MCUHeroesApplication application, ApplicationComponent component) {
        component.inject(this);
    }

    private void setupToolbar(String name) {
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.logo);
        getSupportActionBar().setTitle(name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void releaseSubComponents(MCUHeroesApplication application) {

    }
}
