package com.example.tfg.Entities.User;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;

public class UserArtistsPreferences {

    @Id
    public String id;
    public String userId;
    public ArrayList<String> artistsId;

    public UserArtistsPreferences(){}
}
