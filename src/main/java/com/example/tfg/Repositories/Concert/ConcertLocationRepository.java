package com.example.tfg.Repositories.Concert;

import com.example.tfg.Entities.Concert.Concert;
import com.example.tfg.Entities.Concert.ConcertLocation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;
import java.util.List;

public interface ConcertLocationRepository extends MongoRepository<ConcertLocation, String>  {

}
