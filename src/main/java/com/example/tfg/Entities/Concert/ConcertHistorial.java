package com.example.tfg.Entities.Concert;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;

public class ConcertHistorial {

    @Id
    public String id;
    public String gigId;
    public int numberAssistants;
    public double rate;
    public ArrayList<String> assistantsIds;

    public ConcertHistorial(){}
}
