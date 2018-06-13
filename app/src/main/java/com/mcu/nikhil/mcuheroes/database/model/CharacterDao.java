package com.mcu.nikhil.mcuheroes.database.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.sql.SQLException;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

@Dao
public interface CharacterDao {

    String DATABASE_NAME = "mcuheroes.db";
    int DATABASE_VERSION = 1;
    long ROWS = 5L;

    @Insert
    void addCharacter(CharacterModel character) throws SQLException;

    @Query("SELECT * FROM " +
            " (SELECT * FROM " +
            CharacterModel.TABLE_NAME_CHARACTER +
            " order by id DESC LIMIT " +
            ROWS +
            ") sub " +
            " order by id ASC ")
    Flowable<List<CharacterModel>> selectLast5Characters() throws SQLException;

    @Query("SELECT * FROM " +
            CharacterModel.TABLE_NAME_CHARACTER)
    Maybe<List<CharacterModel>> selectAllCharacters() throws SQLException;


    /*SELECT * FROM (
    SELECT * FROM table ORDER BY id DESC LIMIT 50
) sub
ORDER BY id ASC*/

   /*
    int addCharacter(CharacterModel character) throws SQLException;

    List<CharacterModel> selectLast5Characters() throws SQLException;

    List<CharacterModel> selectAllCharacters() throws SQLException;*/
}
