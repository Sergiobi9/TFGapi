package com.example.tfg.Entities.Artist;

public class ArtistUserRegisterSelection {

    public String artistId;
    public String artistName;
    public String profileUrl;
    public String musicalStyle;

    public ArtistUserRegisterSelection(String artistId, String artistName, String profileUrl, String musicalStyle) {
        this.artistId = artistId;
        this.artistName = artistName;
        this.profileUrl = profileUrl;
        this.musicalStyle = musicalStyle;
    }

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getMusicalStyle() {
        return musicalStyle;
    }

    public void setMusicalStyle(String musicalStyle) {
        this.musicalStyle = musicalStyle;
    }
}
