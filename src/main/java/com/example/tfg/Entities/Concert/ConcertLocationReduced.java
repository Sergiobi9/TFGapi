package com.example.tfg.Entities.Concert;

public class ConcertLocationReduced {

    private double latitude;
    private double longitude;
    private String placeName;
    private String address;
    private String placeDescription;

    public ConcertLocationReduced() {}

    public ConcertLocationReduced(ConcertLocation concertLocation) {
        this.latitude = concertLocation.getLatitude();
        this.longitude = concertLocation.getLongitude();
        this.placeName = concertLocation.getPlaceName();
        this.address = concertLocation.getAddress();
        this.placeDescription = concertLocation.getPlaceDescription();
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
