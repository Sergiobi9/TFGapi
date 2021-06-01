package com.example.tfg.Repositories.Concert.Pricing;

import com.example.tfg.Entities.Concert.ConcertHistory;
import com.example.tfg.Entities.Concert.Pricing.ConcertIntervalPricing;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ConcertIntervalPricingRepository extends MongoRepository<ConcertIntervalPricing, String> {

    ConcertIntervalPricing findConcertIntervalPricingById(String concertIntervalPricingId);
    List<ConcertIntervalPricing> findConcertIntervalPricingByConcertId(String concertId);
}