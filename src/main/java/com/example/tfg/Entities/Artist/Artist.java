package com.example.tfg.Entities.Artist;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ARTIST")
public class    Artist {

    @Id
    private String id;
    private String userId;
    private String artistName;
    private String bio;
    private String musicalStyleId;
    private String artistSince;
    private String artistSocialMediaLinksId;

    public Artist() {
    }

    public Artist(String userId, String artistName, String bio, String musicalStyleId, String artistSocialMediaLinksId, String artistSince) {
        this.userId = userId;
        this.artistName = artistName;
        this.bio = bio;
        this.musicalStyleId = musicalStyleId;
        this.artistSocialMediaLinksId = artistSocialMediaLinksId;
        this.artistSince = artistSince;
    }

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

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getMusicalStyleId() {
        return musicalStyleId;
    }

    public String getArtistSince() {
        return artistSince;
    }

    public void setArtistSince(String artistSince) {
        this.artistSince = artistSince;
    }

    public void setMusicalStyleId(String musicalStyleId) {
        this.musicalStyleId = musicalStyleId;
    }

    public String getArtistSocialMediaLinksId() {
        return artistSocialMediaLinksId;
    }

    public void setArtistSocialMediaLinksId(String artistSocialMediaLinksId) {
        this.artistSocialMediaLinksId = artistSocialMediaLinksId;
    }
}
