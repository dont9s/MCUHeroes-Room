package com.mcu.nikhil.mcuheroes.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mcu.nikhil.mcuheroes.ApplicationComponent;
import com.mcu.nikhil.mcuheroes.MCUHeroesApplication;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependencies(MCUHeroesApplication.get(this),
                MCUHeroesApplication.getComponent());
    }

    protected abstract void injectDependencies(MCUHeroesApplication application,
                                               ApplicationComponent component);

    @Override
    public void finish() {
        super.finish();
        releaseSubComponents(MCUHeroesApplication.get(this));
    }

    protected abstract void releaseSubComponents(MCUHeroesApplication application);
}
