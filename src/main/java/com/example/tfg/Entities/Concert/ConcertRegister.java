package com.example.tfg.Entities.Concert;

import java.util.ArrayList;

public class ConcertRegister {

    public String name;
    public String dateStarts;
    public double price;
    public int numberAssistants;
    public String description;
    public String extraDescription;
    public boolean finished;
    public int numberImages;
    public ArrayList<String> artistsIds;

    public ConcertRegister(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateStarts() {
        return dateStarts;
    }

    public void setDateStarts(String dateStarts) {
        this.dateStarts = dateStarts;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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

    public ArrayList<String> getArtistsIds() {
        return artistsIds;
    }

    public void setArtistsIds(ArrayList<String> artistsIds) {
        this.artistsIds = artistsIds;
    }


}