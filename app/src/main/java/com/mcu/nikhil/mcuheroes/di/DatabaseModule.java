package com.mcu.nikhil.mcuheroes.di;

import com.mcu.nikhil.core_lib.database.DatabaseHelper;
import com.mcu.nikhil.mcuheroes.database.DatabaseHelperImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    @Provides
    @Singleton
    DatabaseHelper provideDatabaseHelperService(DatabaseHelperImpl databaseHelper){
        return ((DatabaseHelper) databaseHelper);
    }
}
