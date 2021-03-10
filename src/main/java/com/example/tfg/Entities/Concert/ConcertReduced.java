package com.example.tfg.Entities.Concert;

import com.example.tfg.Entities.Artist.ArtistInfo;

import java.util.ArrayList;

public class ConcertReduced {

    public String concertId;
    public String name;
    public double latitude;
    public double longitude;
    public String street;
    public double price;
    public String dateStarts;
    public int numberAssistants;
    public String description;
    public String placeDescription;
    public String extraDescription;
    public String concertCoverImage;
    public ArrayList<String> imagesUrls;
    public ArrayList<ArtistInfo> artists;

    public ConcertReduced(){}

    public ConcertReduced(String concertId, String name, double latitude, double longitude, String street, double price, String dateStarts, int numberAssistants, String description, String placeDescription, String extraDescription, String concertCoverImage, ArrayList<String> imagesUrls, ArrayList<ArtistInfo> artists) {
        this.concertId = concertId;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.street = street;
        this.price = price;
        this.dateStarts = dateStarts;
        this.numberAssistants = numberAssistants;
        this.description = description;
        this.placeDescription = placeDescription;
        this.extraDescription = extraDescription;
        this.concertCoverImage = concertCoverImage;
        this.imagesUrls = imagesUrls;
        this.artists = artists;
    }

    public String getConcertId() {
        return concertId;
    }

    public void setConcertId(String concertId) {
        this.concertId = concertId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDateStarts() {
        return dateStarts;
    }

    public void setDateStarts(String dateStarts) {
        this.dateStarts = dateStarts;
    }

    public int getNumberAssistants() {
        return numberAssistants;
    }

    public void setNumberAssistants(int numberAssistants) {
        this.numberAssistants = numberAssistants;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlaceDescription() {
        return placeDescription;
    }

    public void setPlaceDescription(String placeDescription) {
        this.placeDescription = placeDescription;
    }

    public String getExtraDescription() {
        return extraDescription;
    }

    public void setExtraDescription(String extraDescription) {
        this.extraDescription = extraDescription;
    }

    public String getConcertCoverImage() {
        return concertCoverImage;
    }

    public void setConcertCoverImage(String concertCoverImage) {
        this.concertCoverImage = concertCoverImage;
    }

    public ArrayList<String> getImagesUrls() {
        return imagesUrls;
    }

    public void setImagesUrls(ArrayList<String> imagesUrls) {
        this.imagesUrls = imagesUrls;
    }

    public ArrayList<ArtistInfo> getArtists() {
        return artists;
    }

    public void setArtists(ArrayList<ArtistInfo> artists) {
        this.artists = artists;
    }
}
