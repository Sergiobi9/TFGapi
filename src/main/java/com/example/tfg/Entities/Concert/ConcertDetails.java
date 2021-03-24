package com.example.tfg.Entities.Concert;

import com.example.tfg.Helpers.ImageStorage;

public class ConcertDetails {

    private String concertId;
    private String concertName;
    private String description;
    private String extraDescription;
    private String concertDate;
    private String concertCover;
    private double price;

    public ConcertDetails() { }

    public ConcertDetails(Concert concert){
        this.concertId = concert.getId();
        this.concertName = concert.getName();
        this.description = concert.getDescription();
        this.extraDescription = concert.getExtraDescription();
        this.concertDate = concert.getDateStarts();
        this.concertCover = ImageStorage.getConcertCoverImage(concertId);
        this.price = concert.getPrice();
    }

    public String getConcertId() {
        return concertId;
    }

    public void setConcertId(String concertId) {
        this.concertId = concertId;
    }

    public String getConcertName() {
        return concertName;
    }

    public void setConcertName(String concertName) {
        this.concertName = concertName;
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

    public String getConcertDate() {
        return concertDate;
    }

    public void setConcertDate(String concertDate) {
        this.concertDate = concertDate;
    }

    public String getConcertCover() {
        return concertCover;
    }

    public void setConcertCover(String concertCover) {
        this.concertCover = concertCover;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
