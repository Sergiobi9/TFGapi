package com.example.tfg.Entities.Artist;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

    @Document(collection = "ARTISTS")
public class Artist {

    @Id
    public String id;
    public String name;
    public String country;
    public int gender;
    public String birthday;
    public String email;
    public String password;
    public String profileUrl;
    public String bio;
    public String musicalStyle;

    public Artist(){}
}
