package com.example.tfg.Controllers.Rating;

import com.example.tfg.Entities.Booking.Booking;
import com.example.tfg.Entities.Concert.Concert;
import com.example.tfg.Entities.Rating.Rating;
import com.example.tfg.Entities.Rating.RatingSimplified;
import com.example.tfg.Helpers.DateUtils;
import com.example.tfg.Helpers.ImageStorage;
import com.example.tfg.Repositories.Booking.BookingRepository;
import com.example.tfg.Repositories.Concert.ConcertRepository;
import com.example.tfg.Repositories.Rating.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/concert/rating")
public class RatingController {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ConcertRepository concertRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @GetMapping("/all/userId/{userId}/{currentDate}")
    public ResponseEntity getAssistedConcertsByUserId(@PathVariable String userId, @PathVariable String currentDate) {
        List<Booking> bookings = bookingRepository.findAllByUserId(userId);
        ArrayList<String> concertIds = new ArrayList<>();
        ArrayList<RatingSimplified> concertsRating = new ArrayList<>();

        for (int i = 0; i < bookings.size(); i++) {
            String concertId = bookings.get(i).getConcertId();

            if (!concertIds.contains(concertId)) {
                Concert concert = concertRepository.findConcertById(concertId);

                if (concert != null) {
                    String concertDate = concert.getDateStarts();
                    if (DateUtils.currentDateIsAfter(concertDate, currentDate)) {
                        Rating ratingConcert = ratingRepository.findRatingByUserIdAndConcertId(userId, concertId);
                        String concertName = concert.getName();
                        if (ratingConcert == null) {
                            ratingConcert = new Rating(userId, concertId, -1, "", "");
                            ratingRepository.insert(ratingConcert);
                        }

                        concertIds.add(concertId);
                        RatingSimplified ratingSimplified = getRating(ratingConcert, concertName);
                        concertsRating.add(ratingSimplified);
                    }
                }
            }
        }
        return new ResponseEntity(concertsRating, HttpStatus.valueOf(200));
    }

    private RatingSimplified getRating(Rating rating, String concertName) {
        return new RatingSimplified(rating, concertName);
    }


    @PutMapping("/post/{currentDate}")
    public ResponseEntity postRate(@PathVariable String currentDate, @RequestBody RatingSimplified ratingSimplified) {
        String ratingId = ratingSimplified.getId();

        Rating ratingRetrieved = ratingRepository.findRatingById(ratingId);
        ratingRetrieved.setComment(ratingSimplified.getComment());
        ratingRetrieved.setRate(ratingSimplified.getRate());
        ratingRetrieved.setRatingRatePosted(currentDate);

        ratingRepository.save(ratingRetrieved);
        return new ResponseEntity(ratingSimplified, HttpStatus.valueOf(200));
    }
}
