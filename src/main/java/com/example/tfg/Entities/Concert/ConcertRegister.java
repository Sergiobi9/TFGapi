package com.example.tfg.Entities.Concert;

import com.example.tfg.Entities.Concert.Pricing.ConcertIntervalPricing;

import java.util.ArrayList;

public class ConcertRegister {

    public String name;
    public double latitude;
    public double longitude;
    public String placeName;
    public String placeAddress;
    public String placeDescription;
    public String dateCreated;
    public String dateStarts;
    private String userId;
    public String description;
    public String extraDescription;
    public boolean finished;
    public int numberImages;
    public ArrayList<ConcertIntervalPricing> concertIntervalPricing;
    public ArrayList<String> artistsIds;

    public ConcertRegister(){}

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

    public String getPlaceName() {
        return placeName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getPlaceDescription() {
        return placeDescription;
    }

    public void setPlaceDescription(String placeDescription) {
        this.placeDescription = placeDescription;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateStarts() {
        return dateStarts;
    }

    public void setDateStarts(String dateStarts) {
        this.dateStarts = dateStarts;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExtraDescription() {
        return extraDescription;
    }

    public void setExtraDescription(String extraDescription) {
        this.extraDescription = extraDescription;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public int getNumberImages() {
        return numberImages;
    }

    public void setNumberImages(int numberImages) {
        this.numberImages = numberImages;
    }

    public ArrayList<ConcertIntervalPricing> getConcertIntervalPricing() {
        return concertIntervalPricing;
    }

    public void setConcertIntervalPricing(ArrayList<ConcertIntervalPricing> concertIntervalPricing) {
        this.concertIntervalPricing = concertIntervalPricing;
    }

    public ArrayList<String> getArtistsIds() {
        return artistsIds;
    }

    public void setArtistsIds(ArrayList<String> artistsIds) {
        this.artistsIds = artistsIds;
    }
}
