package com.example.tfg.Entities.Rating;

import com.example.tfg.Helpers.ImageStorage;

public class RatingSimplified {

    private String id;
    private double rate;
    private String comment;
    private String concertCover;
    private String concertName;
    private String ratingDatePosted;

    public RatingSimplified(){}

    public RatingSimplified(Rating rating, String concertName){
        this.rate = rating.getRate();
        this.comment = rating.getComment();
        this.concertCover = ImageStorage.getConcertCoverImage(rating.getConcertId());
        this.concertName = concertName;
        this.ratingDatePosted = rating.getRatingRatePosted();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getConcertCover() {
        return concertCover;
    }

    public void setConcertCover(String concertCover) {
        this.concertCover = concertCover;
    }

    public String getRatingDatePosted() {
        return ratingDatePosted;
    }

    public void setRatingDatePosted(String ratingDatePosted) {
        this.ratingDatePosted = ratingDatePosted;
    }
}
