package com.example.tfg.Repositories.Booking;

import com.example.tfg.Entities.Booking.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BookingRepository extends MongoRepository<Booking, String> {

    List<Booking> findAllByUserIdAndConcertId(String userId, String concertId);
    List<Booking> findAllByConcertId(String id);
}
