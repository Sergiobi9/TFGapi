package com.example.tfg.Entities.Artist;

public class ArtistInfo {

    public String userId;
    public String name;
    public String country;
    public int gender;
    public String profileUrl;
    public String bio;
    public String musicalStyle;

    public ArtistInfo(){}

    public ArtistInfo(String userId, String name, String country, int gender, String profileUrl, String bio, String musicalStyle) {
        this.userId = userId;
        this.name = name;
        this.country = country;
        this.gender = gender;
        this.profileUrl = profileUrl;
        this.bio = bio;
        this.musicalStyle = musicalStyle;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getMusicalStyle() {
        return musicalStyle;
    }

    public void setMusicalStyle(String musicalStyle) {
        this.musicalStyle = musicalStyle;
    }
}
