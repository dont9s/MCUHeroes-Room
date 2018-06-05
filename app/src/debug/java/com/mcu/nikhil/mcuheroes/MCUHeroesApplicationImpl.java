package com.mcu.nikhil.mcuheroes;

import timber.log.Timber;

public class MCUHeroesApplicationImpl extends MCUHeroesApplication{
    @Override
    public void initApplication() {
        // initialize Timber in debug version to log
        Timber.plant(new Timber.DebugTree() {
            @Override
            protected String createStackElementTag(StackTraceElement element) {
                // adding line number to logs
                return super.createStackElementTag(element) + ":" + element.getLineNumber();
            }
        });
    }
}
