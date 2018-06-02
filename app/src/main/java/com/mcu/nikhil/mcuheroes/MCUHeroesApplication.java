package com.mcu.nikhil.mcuheroes;

import android.app.Application;
import android.content.Context;

public class MCUHeroesApplication extends Application {

    private static ApplicationComponent component;

    public static ApplicationComponent getComponent(){return component;}

    public static MCUHeroesApplication get(Context context){
        return ((MCUHeroesApplication) context.getApplicationContext());
    }

    @Override
    public void onCreate() {
        super.onCreate();

        component = createComponent();
    }

    public ApplicationComponent createComponent(){
        return null;
    }

}
