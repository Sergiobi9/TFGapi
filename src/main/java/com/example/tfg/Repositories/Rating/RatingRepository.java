package com.example.tfg.Repositories.Rating;

import com.example.tfg.Entities.Rating.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RatingRepository extends MongoRepository<Rating, String> {

    Rating findRatingById(String ratingId);
    List<Rating> findAllByUserId(String userId);
    List<Rating> findAllByConcertId(String concertId);
    Rating findRatingByUserIdAndConcertId(String userId, String concertId);

}
