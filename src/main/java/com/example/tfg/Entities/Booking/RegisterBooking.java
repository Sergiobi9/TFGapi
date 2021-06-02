package com.example.tfg.Entities.Booking;

import com.example.tfg.Entities.Concert.Pricing.ConcertIntervalPricingDetails;

import java.lang.ref.PhantomReference;
import java.util.ArrayList;

public class RegisterBooking {

    private String userId;
    private String concertId;
    private ArrayList<ConcertIntervalPricingDetails> bookings;
    private String dateBooked;

    public RegisterBooking() { }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getConcertId() {
        return concertId;
    }

    public void setConcertId(String concertId) {
        this.concertId = concertId;
    }

    public ArrayList<ConcertIntervalPricingDetails> getBookings() {
        return bookings;
    }

    public void setBookings(ArrayList<ConcertIntervalPricingDetails> bookings) {
        this.bookings = bookings;
    }

    public String getDateBooked() {
        return dateBooked;
    }

    public void setDateBooked(String dateBooked) {
        this.dateBooked = dateBooked;
    }
}
