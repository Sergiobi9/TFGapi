package com.example.tfg.Entities.Concert;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document(collection = "CONCERT")
public class Concert {

    @Id
    public String id;
    public String name;
    public String dateCreated;
    public String dateStarts;
    public double price;
    public int numberAssistants;
    public String description;
    public String extraDescription;
    public boolean finished;
    public int numberImages;
    public ArrayList<String> artistsIds;

    public Concert() {}

    public Concert(String name, String dateCreated, String dateStarts, double price, int numberAssistants, String description, String extraDescription, boolean finished, int numberImages, ArrayList<String> artistsIds) {
        this.name = name;
        this.dateCreated = dateCreated;
        this.dateStarts = dateStarts;
        this.price = price;
        this.numberAssistants = numberAssistants;
        this.description = description;
        this.extraDescription = extraDescription;
        this.finished = finished;
        this.numberImages = numberImages;
        this.artistsIds = artistsIds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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
