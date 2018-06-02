package com.mcu.nikhil.core_lib.database;

import com.mcu.nikhil.core_lib.database.model.CharacterModel;

import java.sql.SQLException;
import java.util.List;

public interface DatabaseHelper {

    int addCharacter(CharacterModel character) throws SQLException;

    List<CharacterModel> selectLast5Characters() throws SQLException;

    List<CharacterModel> selectAllCharacters() throws SQLException;
}
