package com.mcu.nikhil.mcuheroes.database;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.mcu.nikhil.mcuheroes.database.model.CharacterDao;
import com.mcu.nikhil.mcuheroes.database.model.CharacterModel;


@Database(entities = {CharacterModel.class} , version = 1, exportSchema = false)
public abstract class DatabaseHelper extends RoomDatabase{
    public abstract CharacterDao characterDao();
}
