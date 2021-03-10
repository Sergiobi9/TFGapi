package com.example.tfg.Repositories.Concert;

import com.example.tfg.Entities.Concert.Concert;
import com.example.tfg.Entities.Role.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;
import java.util.List;

public interface ConcertRepository extends MongoRepository<Concert, String> {

    List<Concert> findAllByArtistsIds(ArrayList<String> artistIds);
}