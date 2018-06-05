package com.mcu.nikhil.mcuheroes.base;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.mcu.nikhil.mcuheroes.MCUHeroesApplication;

public abstract class BaseFragment extends Fragment {

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        injectDependencies(MCUHeroesApplication.get(getContext()));

        //can be used for general purpose in all Fragments of application
    }

    protected  abstract void injectDependencies(MCUHeroesApplication application);
}
