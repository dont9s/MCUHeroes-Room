package com.mcu.nikhil.mcuheroes.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.mcu.nikhil.core_lib.database.DatabaseHelper;
import com.mcu.nikhil.core_lib.database.model.CharacterModel;
import com.mcu.nikhil.mcuheroes.MCUHeroesApplication;
import com.mcu.nikhil.mcuheroes.R;

import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

public class DatabaseHelperImpl extends OrmLiteSqliteOpenHelper implements DatabaseHelper {

    private static final String DATABASE_NAME = "mcuheroes.db";
    private static final int DATABASE_VERSION = 1;
    public static final long ROWS = 5L;
    @Inject
    protected MCUHeroesApplication application;

    private Dao<CharacterModel, Integer> characterDao;

    @Inject
    public DatabaseHelperImpl(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            //Create table. This onCreate() method will be invoked only once of the application
            //lifetime i.e. first time the application start
            TableUtils.createTable(connectionSource, CharacterModel.class);
        }catch (SQLException e){
            Log.e(DatabaseHelper.class.getName(),
                    application.getString(R.string.sql_error_message),
                    e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, CharacterModel.class, true);
            onCreate(database, connectionSource);
        }catch (SQLException e){
            Log.e(DatabaseHelper.class.getName(),
                    application.getString(R.string.sql_upgrade_error) +
                            oldVersion +
                            application.getString(R.string.to_new) +
                            newVersion,
                     e);
        }
    }

    public  Dao<CharacterModel, Integer> getCharacterDao() throws SQLException{
        if (characterDao == null) {
            characterDao = getDao(CharacterModel.class);
        }
        return characterDao;
    }

    @Override
    public int addCharacter(CharacterModel character) throws SQLException {
        CharacterModel result = getCharacterDao().queryForFirst(getCharacterDao()
                .queryBuilder()
                .where()
                .like(CharacterModel.FIELD_CHARACTER_NAME, character.getName())
                .prepare());

        if (result != null)
            getCharacterDao().delete(result);

        return getCharacterDao().create(character);
    }

    @Override
    public List<CharacterModel> selectLast5Characters() throws SQLException {
        return getCharacterDao().query(getCharacterDao()
                .queryBuilder().orderBy(CharacterModel.FIELD_CHARACTER_ID, false)
                .limit(ROWS)
                .prepare());
    }

    @Override
    public List<CharacterModel> selectAllCharacters() throws SQLException {
        return getCharacterDao().query(getCharacterDao().queryBuilder().prepare());
    }

    @Override
    public void close() {
        super.close();
        characterDao  = null;
    }
}
