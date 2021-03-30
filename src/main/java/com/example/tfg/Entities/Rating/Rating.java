package com.example.tfg.Entities.Rating;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "CONCERT_RATING")
public class Rating {

    @Id
    private String id;
    private String userId;
    private String concertId;
    private double rate;
    private String comment;

    public Rating() { }

    public Rating(String userId, String concertId, double rate, String comment) {
        this.userId = userId;
        this.concertId = concertId;
        this.rate = rate;
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
}
