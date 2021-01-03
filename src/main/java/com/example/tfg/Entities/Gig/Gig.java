package com.example.tfg.Entities.Gig;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;

public class Gig {

    @Id
    public String id;
    public String name;
    public double latitude;
    public double longitude;
    public String street;
    public String price;
    public String dateCreated;
    public String dateStarts;
    public int numberAssistants;
    public String description;
    public String placeDescription;
    public String extraDescription;
    public boolean finished;
    public ArrayList<String> imagesUrls;
    public ArrayList<String> artistsIds;
}
