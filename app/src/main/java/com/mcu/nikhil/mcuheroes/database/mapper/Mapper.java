package com.mcu.nikhil.mcuheroes.database.mapper;

import com.mcu.nikhil.mcuheroes.database.model.CharacterModel;
import com.mcu.nikhil.mcuheroes.domain.model.MarvelCharactersResponse;
import com.mcu.nikhil.mcuheroes.util.Constants;

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
