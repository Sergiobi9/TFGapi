package com.example.tfg.Entities.MusicStyle;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "MUSIC_STYLE")
public class MusicStyle {

    @Id
    public String id;
    public String name;
    public String imageUrl;

    public MusicStyle() {
    }

    public MusicStyle(String name){
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
