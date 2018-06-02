package com.mcu.nikhil.mcuheroes.database;

import com.mcu.nikhil.core_lib.database.DatabaseHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    @Provides
    @Singleton
    DatabaseHelper provideDatabaseHelperService(DatabaseHelperImpl databaseHelper){
        return databaseHelper;
    }
}
