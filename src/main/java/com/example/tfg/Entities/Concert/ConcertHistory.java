package com.example.tfg.Entities.Concert;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document(collection = "CONCERT_HISTORY")
public class ConcertHistory {

    @Id
    public String id;
    public String concertId;
    public int numberAssistants;
    public double rate;
    public ArrayList<String> assistantsIds;

    public ConcertHistory(){}

    public ConcertHistory(String concertId) {
        this.concertId = concertId;
        this.numberAssistants = 0;
        this.rate = 0.0;
        this.assistantsIds = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getConcertId() {
        return concertId;
    }

    public void setConcertId(String concertId) {
        this.concertId = concertId;
    }

    public int getNumberAssistants() {
        return numberAssistants;
    }

    public void setNumberAssistants(int numberAssistants) {
        this.numberAssistants = numberAssistants;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public ArrayList<String> getAssistantsIds() {
        return assistantsIds;
    }

    public void setAssistantsIds(ArrayList<String> assistantsIds) {
        this.assistantsIds = assistantsIds;
    }
}
