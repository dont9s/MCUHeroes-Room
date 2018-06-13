package com.mcu.nikhil.mcuheroes.database.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = CharacterModel.TABLE_NAME_CHARACTER)
public class CharacterModel implements Serializable {
    public static final String TABLE_NAME_CHARACTER = "characters";
    public static final String FIELD_CHARACTER_ID = "_id";
    public static final String FIELD_CHARACTER_NAME = "name";
    public static final String FIELD_CHARACTER_DESCRIPTION = "description";
    public static final String FIELD_CHARACTER_THUMBNAIL = "thumbnail";
    public static final String FIELD_CHARACTER_IMAGE = "image";

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = FIELD_CHARACTER_NAME)
    private String name;
    @ColumnInfo(name = FIELD_CHARACTER_DESCRIPTION)
    private String description;
    @ColumnInfo(name = FIELD_CHARACTER_THUMBNAIL)
    private String thumbnail;
    @ColumnInfo(name = FIELD_CHARACTER_IMAGE)
    private String image;

    public CharacterModel(){}

    @Ignore
    public CharacterModel(int id, String name, String description, String thumbnail) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {return name;}
}
