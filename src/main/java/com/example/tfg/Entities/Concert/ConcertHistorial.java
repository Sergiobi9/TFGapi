package com.example.tfg.Entities.Concert;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;

public class ConcertHistorial {

    @Id
    public String id;
    public String concertId;
    public int numberAssistants;
    public double rate;
    public ArrayList<String> assistantsIds;

    public ConcertHistorial(){}

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
