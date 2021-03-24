package com.example.tfg.Entities.Concert;

import com.example.tfg.Entities.Artist.ArtistSimplified;

import java.util.ArrayList;

public class FullConcertDetails {

    private ConcertDetails concertDetails;
    private ConcertLocationReduced concertLocation;
    private ArrayList<ArtistSimplified> concertArtists;
    private int placesRemaining;
    private ArrayList<String> bookingsIds;
    private ArrayList<String> imagesUrls;

    public FullConcertDetails(){}

    public FullConcertDetails(ConcertDetails concertDetails,
                              ConcertLocationReduced concertLocationReduced,
                              ArrayList<ArtistSimplified> concertArtists,
                              int placesRemaining,
                              ArrayList<String> bookingsIds,
                              ArrayList<String> imagesUrls){

        this.concertDetails = concertDetails;
        this.concertLocation = concertLocationReduced;
        this.concertArtists = concertArtists;
        this.placesRemaining = placesRemaining;
        this.bookingsIds = bookingsIds;
        this.imagesUrls = imagesUrls;

    }

    public ConcertDetails getConcertDetails() {
        return concertDetails;
    }

    public void setConcertDetails(ConcertDetails concertDetails) {
        this.concertDetails = concertDetails;
    }

    public ConcertLocationReduced getConcertLocation() {
        return concertLocation;
    }

    public void setConcertLocation(ConcertLocationReduced concertLocation) {
        this.concertLocation = concertLocation;
    }

    public ArrayList<ArtistSimplified> getConcertArtists() {
        return concertArtists;
    }

    public void setConcertArtists(ArrayList<ArtistSimplified> concertArtists) {
        this.concertArtists = concertArtists;
    }

    public int getPlacesRemaining() {
        return placesRemaining;
    }

    public void setPlacesRemaining(int placesRemaining) {
        this.placesRemaining = placesRemaining;
    }

    public ArrayList<String> getBookingsIds() {
        return bookingsIds;
    }

    public void setBookingsIds(ArrayList<String> bookingsIds) {
        this.bookingsIds = bookingsIds;
    }

    public ArrayList<String> getImagesUrls() {
        return imagesUrls;
    }

    public void setImagesUrls(ArrayList<String> imagesUrls) {
        this.imagesUrls = imagesUrls;
    }
}
