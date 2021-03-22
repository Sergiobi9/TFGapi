package com.example.tfg.Entities.ArtistSocialMediaLinks;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ARTIST_SOCIAL_MEDIA_LINKS")
public class ArtistSocialMediaLinks {

    @Id
    private String id;
    private String userId;
    private String spotifyLink;
    private String facebookLink;
    private String twitterLink;
    private String instagramLink;
    private String youtubeLink;
    private String snapchatLink;

    public ArtistSocialMediaLinks(){}

    public ArtistSocialMediaLinks(String userId){
        this.userId = userId;
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
}
