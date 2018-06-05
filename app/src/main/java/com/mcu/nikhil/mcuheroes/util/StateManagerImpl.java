package com.mcu.nikhil.mcuheroes.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.mcu.nikhil.core_lib.util.StateManager;
import com.mirhoseini.utils.Utils;

import javax.inject.Inject;

public class StateManagerImpl implements StateManager {

    private Context context;

    @Inject
    public StateManagerImpl(Context context) {
        this.context = context;
    }

    @Override
    public boolean isConnect() {
        return Utils.isConnected(context);
    }
}
