package com.example.tfg.Entities.Token;

import com.example.tfg.Entities.Artist.Artist;
import com.example.tfg.Entities.User.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.UUID;

@Document(collection = "ConfirmationTokenEmailVerification")
public class ConfirmationTokenArtist {

    @Id
    private String tokenId;
    private String confirmationToken;
    private Date createdDate;
    private Artist artist;

    public ConfirmationTokenArtist() {}

    public ConfirmationTokenArtist(Artist artist) {
        this.artist = artist;
        createdDate = new Date();
        confirmationToken = UUID.randomUUID().toString();
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getConfirmationToken() {
        return confirmationToken;
    }

    public void setConfirmationToken(String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }
}