package com.mcu.nikhil.mcuheroes.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;

import com.mcu.nikhil.mcuheroes.ApplicationComponent;
import com.mcu.nikhil.mcuheroes.MCUHeroesApplication;
import com.mcu.nikhil.mcuheroes.R;
import com.mcu.nikhil.mcuheroes.base.BaseActivity;
import com.mcu.nikhil.mcuheroes.util.AppConstants;

import javax.inject.Inject;

import butterknife.ButterKnife;
import timber.log.Timber;

public class SplashActivity extends BaseActivity {
    //injecting dependencies via dagger
    @Inject
    Context context;

    //Thread to process splash screen events
    private Thread splashThread;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ButterKnife.bind(this);
        //the thread towait for splash screen events
        splashThread = new Thread(){
            @Override
            public void run() {
                try{
                    synchronized (this){
                        //wait given time or ecxit on touch
                        wait(AppConstants.SPLASH_TIMEOUT_SEC);
                    }
                }catch (InterruptedException ex){
                    Timber.e(ex, getString(R.string.splash_thread_interrupt));
                }
                finish();
                //Open Mainactivity
                Intent mainActivityIntent = new Intent();
                mainActivityIntent.setClass(context, MainActivity.class);
                startActivity(mainActivityIntent);
            }
        };
        splashThread.start();
    }

    @Override
    protected void injectDependencies(MCUHeroesApplication application,
                                      ApplicationComponent component) {
        component.inject(this);
    }

    //Listening to whole activity touch events


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            synchronized (splashThread){
                splashThread.notifyAll();
            }
        }
        return true;
    }

    @Override
    protected void releaseSubComponents(MCUHeroesApplication application) {

    }
}
