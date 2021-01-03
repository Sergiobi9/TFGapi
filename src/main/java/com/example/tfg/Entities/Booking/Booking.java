package com.example.tfg.Entities.Booking;

import org.springframework.data.annotation.Id;

public class Booking {

    @Id
    public String id;
    public String userId;
    public String gigId;
    public double price;
    public String dateCreated;

    public Booking(){}
}
