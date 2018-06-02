package com.mcu.nikhil.core_lib.database.mapper;

import com.mcu.nikhil.core_lib.database.model.CharacterModel;
import com.mcu.nikhil.core_lib.domain.model.MarvelCharactersResponse;
import com.mcu.nikhil.core_lib.util.Constants;

public class Mapper {

    public static CharacterModel mapCharacterResponseToCharacter(MarvelCharactersResponse charactersResponse){
        CharacterModel character = new CharacterModel();

        character.setName(charactersResponse.getData().getResults().get(0).getName());
        character.setDescription(charactersResponse.getData().getResults().get(0).getDescription());
        character.setThumbnail(String.format("%s/%s.%s",
                charactersResponse.getData().getResults().get(0).getThumbnail().getPath(),
                Constants.STANDARD_XLARGE,
                charactersResponse.getData().getResults().get(0).getThumbnail().getExtension()));
        character.setImage(String.format("%s/%s.%s",
                charactersResponse.getData().getResults().get(0).getThumbnail().getPath(),
                Constants.PORTRAIT_XLARGE,
                charactersResponse.getData().getResults().get(0).getThumbnail().getExtension()));
        return character;
    }

}
