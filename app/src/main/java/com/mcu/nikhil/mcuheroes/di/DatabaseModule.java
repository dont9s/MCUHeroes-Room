package com.mcu.nikhil.mcuheroes.di;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.mcu.nikhil.mcuheroes.database.DatabaseHelper;
import com.mcu.nikhil.mcuheroes.database.model.CharacterDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {
    @Provides
    @Singleton
    DatabaseHelper provideDatabaseHelperService(Context context) {
        DatabaseHelper db = Room.databaseBuilder(context,
                DatabaseHelper.class, CharacterDao.DATABASE_NAME).build();
        return db;
    }
}
