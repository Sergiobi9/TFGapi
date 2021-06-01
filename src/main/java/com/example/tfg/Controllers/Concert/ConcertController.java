package com.example.tfg.Controllers.Concert;

import com.example.tfg.Entities.Artist.Artist;
import com.example.tfg.Entities.Artist.ArtistInfo;
import com.example.tfg.Entities.Artist.ArtistSimplified;
import com.example.tfg.Entities.Booking.Booking;
import com.example.tfg.Entities.Concert.*;
import com.example.tfg.Entities.Concert.Pricing.ConcertIntervalPricing;
import com.example.tfg.Entities.Rating.Rating;
import com.example.tfg.Entities.User.User;
import com.example.tfg.Entities.User.UserPreferences;
import com.example.tfg.Helpers.Constants;
import com.example.tfg.Helpers.DateUtils;
import com.example.tfg.Helpers.ImageStorage;
import com.example.tfg.Repositories.Artist.ArtistRepository;
import com.example.tfg.Repositories.Booking.BookingRepository;
import com.example.tfg.Repositories.Concert.ConcertHistoryRepository;
import com.example.tfg.Repositories.Concert.ConcertLocationRepository;
import com.example.tfg.Repositories.Concert.ConcertRepository;
import com.example.tfg.Repositories.Concert.Pricing.ConcertIntervalPricingRepository;
import com.example.tfg.Repositories.Rating.RatingRepository;
import com.example.tfg.Repositories.User.UserPreferencesRepository;
import com.example.tfg.Repositories.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.example.tfg.Helpers.Constants.USER_BOOKING;
import static com.example.tfg.Helpers.Constants.USER_RATING;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/concert")
public class ConcertController {

    @Autowired
    private ConcertLocationRepository concertLocationRepository;

    @Autowired
    private ConcertRepository concertRepository;

    @Autowired
    private ConcertHistoryRepository concertHistoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPreferencesRepository userPreferencesRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private ConcertIntervalPricingRepository concertIntervalPricingRepository;

    @PostMapping("/create")
    public ResponseEntity createConcert(@RequestBody ConcertRegister concertRegister) {

        Concert concert = new Concert(concertRegister.getName(),
                concertRegister.getDateCreated(),
                concertRegister.getDateStarts(),
                concertRegister.getUserId(),
                concertRegister.getDescription(),
                concertRegister.getExtraDescription(),
                false,
                concertRegister.getNumberImages(),
                concertRegister.getArtistsIds());

        concertRepository.save(concert);

        ConcertLocation concertLocation = new ConcertLocation(
                concert.getId(),
                concertRegister.getLatitude(),
                concertRegister.getLongitude(),
                concertRegister.getPlaceName(),
                concertRegister.getPlaceAddress(),
                concertRegister.getPlaceDescription());

        concertLocationRepository.save(concertLocation);

        ArrayList<ConcertIntervalPricing> concertIntervalPricings = concertRegister.getConcertIntervalPricing();

        if (concertIntervalPricings != null){
            for (ConcertIntervalPricing concertIntervalPricing : concertIntervalPricings){
                concertIntervalPricing.setConcertId(concert.getId());
                concertIntervalPricing.setDiscountApplied(0);
                concertIntervalPricingRepository.save(concertIntervalPricing);
            }
        }

        ConcertHistory concertHistory = new ConcertHistory(concert.getId());
        concertHistoryRepository.save(concertHistory);

        return new ResponseEntity(concert, HttpStatus.valueOf(200));
    }

    @GetMapping("/home/suggestions/{userId}/{currentDate}")
    public ResponseEntity getSuggestedConcertsForHome(@PathVariable String userId, @PathVariable String currentDate) {
        ArrayList<ConcertReduced> concertSuggestions = new ArrayList<>();

        List<Concert> concerts = concertRepository.findAll();
        List<Booking> userBookings = this.bookingRepository.findAllByUserId(userId);
        ArrayList<String> concertBookedIdsController = new ArrayList<>();
        ArrayList<String> concertIdsController = new ArrayList<>();

        UserPreferences userPreferences = userPreferencesRepository.findUserPreferencesByUserId(userId);
        ArrayList<String> artistIdsPreferences = userPreferences.getArtistsIds();
        ArrayList<String> musicStyleIdsPreferences = userPreferences.getMusicStylesIds();


        for (int j = 0; j < userBookings.size(); j++) {
            String concertId = userBookings.get(j).getConcertId();
            if (!concertBookedIdsController.contains(concertId)) concertBookedIdsController.add(concertId);
        }

        for (int i = 0; i < concerts.size(); i++) {
            Concert concert = concerts.get(i);
            String concertId = concert.getId();

            String concertDate = concert.getDateStarts();

            if (DateUtils.currentDateIsBefore(concertDate, currentDate)) {
                /* Add only non booked concerts */
                if (!concertBookedIdsController.contains(concertId)) {
                    String ownerId = concert.getUserId();
                    ArrayList<String> artistsParticipating = concert.getArtistsIds();
                    Artist artist = artistRepository.findByUserId(ownerId);

                    /* Check if user is following artist or at least one of the participating */
                    boolean userIsFollowingSomeParticipatingArtist = userIsFollowingSomeParticipatingArtist(artistIdsPreferences, artistsParticipating);
                    if (artistIdsPreferences.contains(artist.getUserId()) || userIsFollowingSomeParticipatingArtist) {

                        /* Check if concert already added */
                        if (!concertIdsController.contains(concertId)) {
                            ConcertLocation concertLocation = concertLocationRepository.findByConcertId(concert.getId());
                            ConcertReduced concertReduced = createConcertReduced(concert, concertLocation);
                            concertSuggestions.add(concertReduced);
                            concertIdsController.add(concertId);
                        }
                    } else {
                        String artistMusicStyle = artist.getMusicalStyleId();

                        /* Give user some suggestion of music style preferences matching */
                        if (musicStyleIdsPreferences.contains(artistMusicStyle)) {
                            if (!concertIdsController.contains(concertId)) {
                                ConcertLocation concertLocation = concertLocationRepository.findByConcertId(concert.getId());
                                ConcertReduced concertReduced = createConcertReduced(concert, concertLocation);
                                concertSuggestions.add(concertReduced);
                                concertIdsController.add(concertId);
                            }
                        } else {
                            /* Get participating artists music styles */
                            for (int x = 0; x < artistsParticipating.size(); x++) {
                                String artistId = artistsParticipating.get(x);
                                Artist participatingArtist = artistRepository.findByUserId(artistId);

                                String participatingArtistMusicStyle = participatingArtist.getMusicalStyleId();
                                if (musicStyleIdsPreferences.contains(participatingArtistMusicStyle)) {
                                    if (!concertIdsController.contains(concertId)) {
                                        ConcertLocation concertLocation = concertLocationRepository.findByConcertId(concert.getId());
                                        ConcertReduced concertReduced = createConcertReduced(concert, concertLocation);
                                        concertSuggestions.add(concertReduced);
                                        concertIdsController.add(concertId);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return new ResponseEntity(concertSuggestions, HttpStatus.valueOf(200));
    }

    @GetMapping("/home/popular/{userId}/{currentDate}")
    public ResponseEntity getPopularConcertsForHome(@PathVariable String userId, @PathVariable String currentDate) {
        ArrayList<ConcertReduced> popularConcerts = new ArrayList<>();

        List<Concert> concerts = concertRepository.findAll();
        List<Booking> userBookings = this.bookingRepository.findAllByUserId(userId);
        ArrayList<String> concertBookedIdsController = new ArrayList<>();
        ArrayList<String> concertIdsController = new ArrayList<>();

        for (int j = 0; j < userBookings.size(); j++) {
            String concertId = userBookings.get(j).getConcertId();
            if (!concertBookedIdsController.contains(concertId)) concertBookedIdsController.add(concertId);
        }

        for (int i = 0; i < concerts.size(); i++) {
            Concert concert = concerts.get(i);
            String concertId = concert.getId();

            String concertDate = concert.getDateStarts();
            if (DateUtils.currentDateIsBefore(concertDate, currentDate)) {

                /* Add only non booked concerts */
                if (!concertBookedIdsController.contains(concertId)) {

                    ConcertHistory concertHistory = concertHistoryRepository.findConcertHistoryByConcertId(concertId);
                    if (concertHistory != null) {
                        int concertBookings = bookingRepository.findAllByConcertId(concert.getId()).size();
                        int concertPlaces = 1000;

                        double concertPopularityRatio = getConcertPopularityRatio(concertBookings, concertPlaces);
                        if (isConcertPopular(concertPopularityRatio)) {
                            if (!concertIdsController.contains(concertId)) {
                                ConcertLocation concertLocation = concertLocationRepository.findByConcertId(concert.getId());
                                ConcertReduced concertReduced = createConcertReduced(concert, concertLocation);
                                popularConcerts.add(concertReduced);
                                concertIdsController.add(concertId);
                            }
                        }
                    }
                }
            }
        }

        return new ResponseEntity(popularConcerts, HttpStatus.valueOf(200));
    }

    private boolean isConcertPopular(double concertPopularityRatio) {
        double maxRatioInterval = 0.95;
        double minRatioInterval = 0.65;
        return concertPopularityRatio > minRatioInterval && concertPopularityRatio < maxRatioInterval;
    }

    private double getConcertPopularityRatio(int concertBookings, int concertPlaces) {
        return (double) concertBookings / concertPlaces;
    }

    private boolean userIsFollowingSomeParticipatingArtist(ArrayList<String> artistIdsPreferences, ArrayList<String> artistsParticipating) {
        for (String artistId : artistsParticipating) {
            if (artistIdsPreferences.contains(artistId)) {
                return true;
            }
        }
        return false;
    }

    @GetMapping("/artist/{artistId}/{currentDate}")
    public ResponseEntity getArtistConcerts(@PathVariable String artistId, @PathVariable String currentDate) {

        ArrayList<ConcertReduced> artistConcerts = new ArrayList<>();
        List<Concert> concertsMade = getArtistConcertsMade(artistId);
        List<Concert> concertsParticipating = getArtistConcertsCollaborating(artistId);

        List<Concert> mergedList = getAllConcerts(concertsMade, concertsParticipating);

        for (Concert concert : mergedList) {
            String concertDate = concert.getDateStarts();

            if (DateUtils.currentDateIsBefore(concertDate, currentDate)) {
                ConcertLocation concertLocation = concertLocationRepository.findByConcertId(concert.getId());
                ConcertReduced concertReduced = createConcertReduced(concert, concertLocation);
                artistConcerts.add(concertReduced);
            }
        }

        return new ResponseEntity(artistConcerts, HttpStatus.valueOf(200));
    }

    private List<Concert> getArtistConcertsCollaborating(@PathVariable String artistId) {
        return concertRepository.findAllByArtistsIdsContaining(artistId);
    }

    @GetMapping("/all/hosting/artistId/{artistId}/{currentDate}")
    public ResponseEntity getArtistCreatedConcerts(@PathVariable String artistId, @PathVariable String currentDate) {

        ArrayList<ConcertReduced> artistConcerts = new ArrayList<>();
        List<Concert> concertsMade = getArtistConcertsMade(artistId);

        for (Concert concert : concertsMade) {
            ConcertLocation concertLocation = concertLocationRepository.findByConcertId(concert.getId());
            ConcertReduced concertReduced = createConcertReduced(concert, concertLocation);
            artistConcerts.add(concertReduced);
        }

        return new ResponseEntity(artistConcerts, HttpStatus.valueOf(200));
    }

    @GetMapping("/all/finished/artistId/{artistId}/{currentDate}")
    public ResponseEntity getArtistFinishedConcerts(@PathVariable String artistId, @PathVariable String currentDate) {

        ArrayList<ConcertReduced> artistConcerts = new ArrayList<>();
        List<Concert> concertsMade = getArtistConcertsMade(artistId);
        List<Concert> concertsParticipating = getArtistConcertsCollaborating(artistId);

        List<Concert> mergedList = getAllConcerts(concertsMade, concertsParticipating);

        for (Concert concert : mergedList) {
            String concertDate = concert.getDateStarts();

            if (DateUtils.currentDateIsAfter(concertDate, currentDate)) {
                ConcertLocation concertLocation = concertLocationRepository.findByConcertId(concert.getId());
                ConcertReduced concertReduced = createConcertReduced(concert, concertLocation);
                artistConcerts.add(concertReduced);
            }
        }

        return new ResponseEntity(artistConcerts, HttpStatus.valueOf(200));
    }

    @GetMapping("/next/artistId/{artistId}/{currentDate}")
    public ResponseEntity getArtistNextConcert(@PathVariable String artistId, @PathVariable String currentDate) {

        ArrayList<ConcertReduced> artistConcerts = new ArrayList<>();
        List<Concert> concertsMade = getArtistConcertsMade(artistId);
        List<Concert> concertsParticipating = getArtistConcertsCollaborating(artistId);
        List<Concert> mergedList = getAllConcerts(concertsMade, concertsParticipating);

        for (Concert concert : mergedList) {
            String concertDate = concert.getDateStarts();

            if (DateUtils.currentDateIsBefore(concertDate, currentDate)) {
                ConcertLocation concertLocation = concertLocationRepository.findByConcertId(concert.getId());
                ConcertReduced concertReduced = createConcertReduced(concert, concertLocation);
                artistConcerts.add(concertReduced);
            }
        }

        Collections.sort(artistConcerts);
        Collections.reverse(artistConcerts);

        ConcertReduced nextConcert = new ConcertReduced();
        if (!artistConcerts.isEmpty()){ nextConcert = artistConcerts.get(0); }

        return new ResponseEntity(nextConcert, HttpStatus.valueOf(200));
    }

    private List<Concert> getArtistConcertsMade(@PathVariable String artistId) {
        return concertRepository.findAllByUserId(artistId);
    }

    @GetMapping("/all/featuring/artistId/{artistId}/{currentDate}")
    public ResponseEntity getArtistFeaturingConcerts(@PathVariable String artistId, @PathVariable String currentDate) {

        ArrayList<ConcertReduced> artistConcerts = new ArrayList<>();
        List<Concert> concertsParticipating = getArtistConcertsCollaborating(artistId);

        for (Concert concert : concertsParticipating) {
            String concertDate = concert.getDateStarts();

            if (DateUtils.currentDateIsBefore(concertDate, currentDate)) {
                ConcertLocation concertLocation = concertLocationRepository.findByConcertId(concert.getId());
                ConcertReduced concertReduced = createConcertReduced(concert, concertLocation);
                artistConcerts.add(concertReduced);
            }
        }

        return new ResponseEntity(artistConcerts, HttpStatus.valueOf(200));
    }

    /* Radius in km */
    @GetMapping("/map/{userLatitude}/{userLongitude}/{radius}")
    public ResponseEntity getConcertsNearUser(@PathVariable double userLatitude,
                                              @PathVariable double userLongitude,
                                              @PathVariable double radius) {

        ArrayList<ConcertReduced> nearConcerts = new ArrayList<>();
        List<Concert> concerts = concertRepository.findAll();

        for (int i = 0; i < concerts.size(); i++) {

            Concert currentConcert = concerts.get(i);
            ConcertLocation concertLocation = concertLocationRepository.findByConcertId(currentConcert.getId());

            double concertLatitude = concertLocation.getLatitude();
            double concertLongitude = concertLocation.getLongitude();
            double latitudeDistance = Math.toRadians(userLatitude - concertLatitude);
            double longitudeDistance = Math.toRadians(userLongitude - concertLongitude);
            double a = Math.sin(latitudeDistance / 2) * Math.sin(latitudeDistance / 2)
                    + Math.cos(Math.toRadians(concertLatitude)) * Math.cos(Math.toRadians(userLatitude))
                    * Math.sin(longitudeDistance / 2) * Math.sin(longitudeDistance / 2);

            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            Double distance = Constants.EARTH_RADIUS * c;

            distance = Math.pow(distance, 2);
            distance = Math.sqrt(distance);

            if (distance.intValue() <= radius) {
                ConcertReduced concertReduced = createConcertReduced(currentConcert, concertLocation);
                nearConcerts.add(concertReduced);
            }
        }

        return new ResponseEntity(nearConcerts, HttpStatus.valueOf(200));
    }

    @GetMapping("/all/{currentDate}")
    public ResponseEntity getAllConcertsActiveByCurrentDate(@PathVariable String currentDate) {

        ArrayList<ConcertReduced> concertsToReturn = new ArrayList<>();
        List<Concert> concerts = concertRepository.findAll();

        for (int i = 0; i < concerts.size(); i++) {
            String concertDate = concerts.get(i).getDateStarts();

            if (DateUtils.currentDateIsBefore(concertDate, currentDate)) {
                Concert concert = concerts.get(i);
                ConcertLocation concertLocation = concertLocationRepository.findByConcertId(concert.getId());

                ConcertReduced concertReduced = createConcertReduced(concert, concertLocation);

                concertsToReturn.add(concertReduced);
            }
        }

        return new ResponseEntity(concertsToReturn, HttpStatus.valueOf(200));
    }

    private ConcertReduced createConcertReduced(Concert currentConcert, ConcertLocation concertLocation) {
        ArrayList<String> concertImages = getConcertPlacesImages(currentConcert.getId(), currentConcert.getNumberImages());
        String concertCoverImage = ImageStorage.getConcertCoverImage(currentConcert.getId());

        currentConcert.getArtistsIds().add(0, currentConcert.getUserId());
        ArrayList<ArtistInfo> artistsInfoArrayList = getArtistsInfo(currentConcert.getArtistsIds());

        ConcertReduced concertReduced = new ConcertReduced(
                currentConcert.getId(),
                currentConcert.getName(),
                concertLocation.getLatitude(),
                concertLocation.getLongitude(),
                concertLocation.getPlaceName(),
                concertLocation.getAddress(),
                currentConcert.getDateStarts(),
                currentConcert.getDescription(),
                concertLocation.getPlaceDescription(),
                currentConcert.getExtraDescription(),
                concertCoverImage,
                concertImages,
                artistsInfoArrayList
        );

        return concertReduced;
    }

    private ArrayList<String> getConcertPlacesImages(String concertId, int numberPhotos) {
        ArrayList<String> concertImagesToReturn = new ArrayList<>();

        for (int i = 0; i < numberPhotos; i++) {
            String imageUrl = ImageStorage.getConcertPlaceImage(concertId, i);
            concertImagesToReturn.add(imageUrl);
        }

        return concertImagesToReturn;
    }

    private ArrayList<ArtistInfo> getArtistsInfo(ArrayList<String> artistsIds) {
        ArrayList<ArtistInfo> artistsInfoArrayList = new ArrayList<>();

        for (int i = 0; i < artistsIds.size(); i++) {
            String artistId = artistsIds.get(i);

            User userRetrieved = userRepository.findUserById(artistId);
            Artist artistRetrieved = artistRepository.findByUserId(artistId);

            String artistProfileImage = ImageStorage.getArtistImage(artistId);

            ArtistInfo artistInfo = new ArtistInfo(
                    userRetrieved.getId(),
                    artistRetrieved.getArtistName(),
                    userRetrieved.getCountry(),
                    userRetrieved.getGender(),
                    artistProfileImage,
                    artistRetrieved.getBio(),
                    artistRetrieved.getMusicalStyleId());

            artistsInfoArrayList.add(artistInfo);
        }

        return artistsInfoArrayList;
    }

    private ConcertDetails getConcertDetails(Concert concert) {
        return new ConcertDetails(concert);
    }

    @GetMapping("/info/{userId}/{concertId}")
    public ResponseEntity getConcertInfo(@PathVariable String userId, @PathVariable String concertId) {

        Concert concert = concertRepository.findConcertById(concertId);
        ConcertDetails concertDetails = getConcertDetails(concert);

        ConcertLocation concertLocation = concertLocationRepository.findByConcertId(concertId);
        ConcertLocationReduced concertLocationReduced = getConcertLocationReduced(concertLocation);

        ArrayList<String> concertArtistsIds = concert.getArtistsIds();
        concertArtistsIds.add(0, concert.getUserId());
        ArrayList<ArtistSimplified> concertArtists = getConcertArtistsSimplified(concertArtistsIds);

        int concertPhotos = concert.getNumberImages();
        ArrayList<String> concertPhotosToReturn = getConcertPlacesImages(concertId, concertPhotos);

        List<Booking> userConcertBookings = bookingRepository.findAllByUserIdAndConcertId(userId, concertId);
        ArrayList<String> userConcertBookingsIds = getUserBookingsIds(userConcertBookings);
        int placesRemaining = getConcertPlacesRemaining(concert);

        FullConcertDetails fullConcertDetails = getFullConcertDetails(concertDetails, concertLocationReduced, concertArtists, placesRemaining, userConcertBookingsIds, concertPhotosToReturn);
        return new ResponseEntity(fullConcertDetails, HttpStatus.valueOf(200));
    }

    @GetMapping("/all/activity/{artistId}")
    public ResponseEntity getUserConcertsActivityByArtist(@PathVariable String artistId) {

        List<Concert> concertsMade = getArtistConcertsMade(artistId);
        List<Concert> concertsParticipating = getArtistConcertsCollaborating(artistId);

        List<Concert> mergedList = getAllConcerts(concertsMade, concertsParticipating);
        ArrayList<ConcertActivity> activityToReturn = new ArrayList<>();

        for (Concert concert : mergedList) {
            String concertId = concert.getId();
            String concertName = concert.getName();

            List<Booking> concertBookings = bookingRepository.findAllByConcertId(concertId);
            List<Rating> concertRatings = ratingRepository.findAllByConcertId(concertId);

            ArrayList<String> userIdController = new ArrayList<>();

            for (int i = 0; i < concertBookings.size(); i++) {
                String userId = concertBookings.get(i).getUserId();
                if (!userIdController.contains(userId)) {
                    User user = userRepository.findUserById(userId);
                    if (user != null) {
                        int userBookings = bookingRepository.findAllByUserIdAndConcertId(userId, concertId).size();
                        String userBookingDate = concertBookings.get(i).dateBooked;
                        userIdController.add(userId);
                        activityToReturn.add(new ConcertActivity(user.getFirstName(), USER_BOOKING, "", userBookings, concertName, "", -1, userBookingDate));
                    }
                }
            }

            for (int j = 0; j < concertRatings.size(); j++) {
                Rating rating = concertRatings.get(j);
                String userId = concertRatings.get(j).getUserId();
                User user = userRepository.findUserById(userId);
                if (user != null) {
                    String userRatingDate = rating.getRatingRatePosted();
                    activityToReturn.add(new ConcertActivity(user.getFirstName(), USER_RATING, "", 0, concertName, rating.getComment(), rating.getRate(), userRatingDate));
                }
            }
        }

        /* Now short by date */
        Collections.sort(activityToReturn);
        Collections.reverse(activityToReturn);
        return new ResponseEntity(activityToReturn, HttpStatus.valueOf(200));
    }

    private ArrayList<Concert> getAllConcerts(List<Concert> concertsMade, List<Concert> concertsParticipating) {
        return new ArrayList<Concert>(new HashSet<Concert>(concertsMade) {{
            addAll(concertsParticipating);
        }});
    }

    private ArrayList<ArtistSimplified> getConcertArtistsSimplified(ArrayList<String> concertArtistsIds) {
        ArrayList<ArtistSimplified> concertArtists = new ArrayList<>();
        for (int i = 0; i < concertArtistsIds.size(); i++) {
            String artistId = concertArtistsIds.get(i);
            Artist artist = artistRepository.findByUserId(artistId);
            ArtistSimplified artistSimplified = getArtistsInfoSimplified(artist);
            concertArtists.add(artistSimplified);
        }

        return concertArtists;
    }

    private ArrayList<String> getUserBookingsIds(List<Booking> bookingsByUser) {
        ArrayList<String> userBookings = new ArrayList<>();

        for (int i = 0; i < bookingsByUser.size(); i++) {
            String bookingId = bookingsByUser.get(i).getId();
            userBookings.add(bookingId);
        }

        return userBookings;
    }

    private ConcertLocationReduced getConcertLocationReduced(ConcertLocation concertLocation) {
        return new ConcertLocationReduced(concertLocation);
    }

    private FullConcertDetails getFullConcertDetails(ConcertDetails concertDetails, ConcertLocationReduced concertLocationReduced, ArrayList<ArtistSimplified> concertArtists, int placesRemaining, ArrayList<String> userBookings, ArrayList<String> concertPhotosToReturn) {
        return new FullConcertDetails(concertDetails, concertLocationReduced, concertArtists, placesRemaining, userBookings, concertPhotosToReturn);
    }

    private ArtistSimplified getArtistsInfoSimplified(Artist artist) {
        ArtistSimplified artistSimplified = new ArtistSimplified(artist.getUserId(), artist.getArtistName(), ImageStorage.getArtistImage(artist.getUserId()), artist.getMusicalStyleId());
        return artistSimplified;
    }

    private int getConcertPlacesRemaining(Concert concert) {
        int concertBookings = bookingRepository.findAllByConcertId(concert.getId()).size();
        int concertNumberAssistants = 1000;

        return concertNumberAssistants - concertBookings;
    }

}
