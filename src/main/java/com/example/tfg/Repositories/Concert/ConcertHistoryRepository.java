package com.example.tfg.Repositories.Concert;

import com.example.tfg.Entities.Concert.Concert;
import com.example.tfg.Entities.Concert.ConcertHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConcertHistoryRepository extends MongoRepository<ConcertHistory, String> {

    ConcertHistory findConcertHistoryByConcertId(String concertId);

}
