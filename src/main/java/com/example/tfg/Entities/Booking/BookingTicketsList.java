package com.example.tfg.Entities.Booking;

import com.example.tfg.Entities.Concert.Concert;
import com.example.tfg.Entities.Concert.ConcertLocation;
import com.example.tfg.Helpers.ImageStorage;

import java.util.ArrayList;

public class BookingTicketsList {

    private String concertId;
    private double concertLatitude;
    private double concertLongitude;
    private String concertPlaceName;
    private String concertName;
    private String concertCover;
    private String concertStarts;
    private ArrayList<Booking> bookings;

    public BookingTicketsList(){}

    public BookingTicketsList(ConcertLocation concertLocation, Concert concert, ArrayList<Booking> bookings){
        this.concertId = concert.getId();
        this.concertLatitude = concertLocation.getLatitude();
        this.concertLongitude = concertLocation.getLongitude();
        this.concertPlaceName = concertLocation.getPlaceName();
        this.concertName = concert.getName();
        this.concertCover = ImageStorage.getConcertCoverImage(concertId);
        this.concertStarts = concert.getDateStarts();
        this.bookings = bookings;
    }

    public String getConcertId() {
        return concertId;
    }

    public void setConcertId(String concertId) {
        this.concertId = concertId;
    }

    public double getConcertLatitude() {
        return concertLatitude;
    }

    public void setConcertLatitude(double concertLatitude) {
        this.concertLatitude = concertLatitude;
    }

    public double getConcertLongitude() {
        return concertLongitude;
    }

    public void setConcertLongitude(double concertLongitude) {
        this.concertLongitude = concertLongitude;
    }

    public String getConcertPlaceName() {
        return concertPlaceName;
    }

    public void setConcertPlaceName(String concertPlaceName) {
        this.concertPlaceName = concertPlaceName;
    }

    public String getConcertName() {
        return concertName;
    }

    public void setConcertName(String concertName) {
        this.concertName = concertName;
    }

    public String getConcertCover() {
        return concertCover;
    }

    public void setConcertCover(String concertCover) {
        this.concertCover = concertCover;
    }

    public String getConcertStarts() {
        return concertStarts;
    }

    public void setConcertStarts(String concertStarts) {
        this.concertStarts = concertStarts;
    }

    public ArrayList<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(ArrayList<Booking> bookings) {
        this.bookings = bookings;
    }
}
