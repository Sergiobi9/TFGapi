package com.example.tfg.Entities.Concert;

public class ArtistProfileConcertInfo {

    public String concertId;
    public String concertName;
    public String dateStarts;
    public String coverImage;

    public ArtistProfileConcertInfo(Concert concert){
        concertId = concert.getId();
        concertName = concert.getName();
        dateStarts = concert.getDateStarts();
    }

    public ArtistProfileConcertInfo() {
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

    public String getDateStarts() {
        return dateStarts;
    }

    public void setDateStarts(String dateStarts) {
        this.dateStarts = dateStarts;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }
}
