package com.example.tfg.Entities.Concert;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "CONCERT_LOCATION")
public class ConcertLocation {

    @Id
    public String id;
    public String concertId;
    public double latitude;
    public double longitude;
    public String placeName;
    public String address;
    public String placeDescription;

    public ConcertLocation(){}

    public ConcertLocation(String concertId, double latitude, double longitude, String placeName, String address, String placeDescription) {
        this.concertId = concertId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.placeName = placeName;
        this.address = address;
        this.placeDescription = placeDescription;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getConcertId() {
        return concertId;
    }

    public void setConcertId(String concertId) {
        this.concertId = concertId;
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

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPlaceDescription() {
        return placeDescription;
    }

    public void setPlaceDescription(String placeDescription) {
        this.placeDescription = placeDescription;
    }
}
