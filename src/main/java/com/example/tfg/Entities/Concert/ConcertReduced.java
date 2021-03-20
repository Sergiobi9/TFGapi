package com.example.tfg.Entities.Concert;

import com.example.tfg.Entities.Artist.ArtistInfo;

import java.util.ArrayList;

public class ConcertReduced {

    public String concertId;
    public String name;
    public double placeLatitude;
    public double placeLongitude;
    public String placeName;
    public String placeAddress;
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

    public ConcertReduced(String concertId, String name, double placeLatitude, double placeLongitude, String placeName, String placeAddress, double price, String dateStarts, int numberAssistants, String description, String placeDescription, String extraDescription, String concertCoverImage, ArrayList<String> imagesUrls, ArrayList<ArtistInfo> artists) {
        this.concertId = concertId;
        this.name = name;
        this.placeLatitude = placeLatitude;
        this.placeLongitude = placeLongitude;
        this.placeName = placeName;
        this.placeAddress = placeAddress;
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

    public double getPlaceLatitude() {
        return placeLatitude;
    }

    public void setPlaceLatitude(double placeLatitude) {
        this.placeLatitude = placeLatitude;
    }

    public double getPlaceLongitude() {
        return placeLongitude;
    }

    public void setPlaceLongitude(double placeLongitude) {
        this.placeLongitude = placeLongitude;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceAddress() {
        return placeAddress;
    }

    public void setPlaceAddress(String placeAddress) {
        this.placeAddress = placeAddress;
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
