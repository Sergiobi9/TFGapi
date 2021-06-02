package com.example.tfg.Entities.Concert;

import com.example.tfg.Entities.Artist.ArtistSimplified;
import com.example.tfg.Entities.Concert.Pricing.ConcertIntervalPricing;
import com.example.tfg.Entities.Concert.Pricing.ConcertIntervalPricingDetails;

import java.util.ArrayList;

public class FullConcertDetails {

    private ConcertDetails concertDetails;
    private ConcertLocationReduced concertLocation;
    private ArrayList<ArtistSimplified> concertArtists;
    private ArrayList<ConcertIntervalPricingDetails> concertTickets;
    private ArrayList<String> bookingsIds;
    private ArrayList<String> imagesUrls;

    public FullConcertDetails(){}

    public FullConcertDetails(ConcertDetails concertDetails,
                              ConcertLocationReduced concertLocationReduced,
                              ArrayList<ArtistSimplified> concertArtists,
                              ArrayList<String> bookingsIds,
                              ArrayList<ConcertIntervalPricingDetails> concertTickets,
                              ArrayList<String> imagesUrls){

        this.concertDetails = concertDetails;
        this.concertLocation = concertLocationReduced;
        this.concertArtists = concertArtists;
        this.bookingsIds = bookingsIds;
        this.concertTickets = concertTickets;
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

    public ArrayList<String> getBookingsIds() {
        return bookingsIds;
    }

    public void setBookingsIds(ArrayList<String> bookingsIds) {
        this.bookingsIds = bookingsIds;
    }

    public ArrayList<ConcertIntervalPricingDetails> getConcertTickets() {
        return concertTickets;
    }

    public void setConcertTickets(ArrayList<ConcertIntervalPricingDetails> concertTickets) {
        this.concertTickets = concertTickets;
    }

    public ArrayList<String> getImagesUrls() {
        return imagesUrls;
    }

    public void setImagesUrls(ArrayList<String> imagesUrls) {
        this.imagesUrls = imagesUrls;
    }
}
