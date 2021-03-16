package com.example.tfg.Entities.User;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;


@Document(collection = "USER_PREFERENCES")
public class UserPreferences {

    @Id
    public String id;
    public String userId;
    public ArrayList<String> artistsIds;
    public ArrayList<String> musicStylesIds;

    public UserPreferences(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ArrayList<String> getArtistsIds() {
        return artistsIds;
    }

    public void setArtistsIds(ArrayList<String> artistsId) {
        this.artistsIds = artistsId;
    }

    public ArrayList<String> getMusicStylesIds() {
        return musicStylesIds;
    }

    public void setMusicStylesIds(ArrayList<String> musicStylesIds) {
        this.musicStylesIds = musicStylesIds;
    }
}
