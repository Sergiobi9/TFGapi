package com.example.tfg.Entities.Artist;

import com.example.tfg.Entities.Concert.ArtistProfileConcertInfo;
import com.example.tfg.Entities.Concert.ConcertReduced;

import java.util.ArrayList;

public class ArtistProfileInfo {

    private String artistId;
    private String artistName;
    private String country;
    private int gender;
    private String profileUrl;
    private String bio;
    private String musicalStyle;
    private String spotifyLink;
    private String facebookLink;
    private String twitterLink;
    private String instagramLink;
    private String youtubeLink;
    private String snapchatLink;
    private int followers;
    private String musicStyleName;
    private String memberSince;
    private ArrayList<ArtistProfileConcertInfo> numberOfConcerts;
    private boolean following;

    public ArtistProfileInfo() { }

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

    public String getSpotifyLink() {
        return spotifyLink;
    }

    public void setSpotifyLink(String spotifyLink) {
        this.spotifyLink = spotifyLink;
    }

    public String getFacebookLink() {
        return facebookLink;
    }

    public void setFacebookLink(String facebookLink) {
        this.facebookLink = facebookLink;
    }

    public String getTwitterLink() {
        return twitterLink;
    }

    public void setTwitterLink(String twitterLink) {
        this.twitterLink = twitterLink;
    }

    public String getInstagramLink() {
        return instagramLink;
    }

    public void setInstagramLink(String instagramLink) {
        this.instagramLink = instagramLink;
    }

    public String getYoutubeLink() {
        return youtubeLink;
    }

    public void setYoutubeLink(String youtubeLink) {
        this.youtubeLink = youtubeLink;
    }

    public String getSnapchatLink() {
        return snapchatLink;
    }

    public void setSnapchatLink(String snapchatLink) {
        this.snapchatLink = snapchatLink;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public String getMemberSince() {
        return memberSince;
    }

    public void setMemberSince(String memberSince) {
        this.memberSince = memberSince;
    }

    public ArrayList<ArtistProfileConcertInfo> getNumberOfConcerts() {
        return numberOfConcerts;
    }

    public void setNumberOfConcerts(ArrayList<ArtistProfileConcertInfo> numberOfConcerts) {
        this.numberOfConcerts = numberOfConcerts;
    }

    public String getMusicStyleName() {
        return musicStyleName;
    }

    public void setMusicStyleName(String musicStyleName) {
        this.musicStyleName = musicStyleName;
    }

    public boolean isFollowing() {
        return following;
    }

    public void setFollowing(boolean following) {
        this.following = following;
    }
}
