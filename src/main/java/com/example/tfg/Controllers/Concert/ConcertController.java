package com.example.tfg.Controllers.Concert;

import com.example.tfg.Entities.Artist.Artist;
import com.example.tfg.Entities.Artist.ArtistInfo;
import com.example.tfg.Entities.Artist.ArtistSimplified;
import com.example.tfg.Entities.Booking.Booking;
import com.example.tfg.Entities.Concert.*;
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
import com.example.tfg.Repositories.User.UserPreferencesRepository;
import com.example.tfg.Repositories.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

    @PostMapping("/create")
    public ResponseEntity createConcert(@RequestBody ConcertRegister concertRegister) {

        Concert concert = new Concert(concertRegister.getName(),
                concertRegister.getDateCreated(),
                concertRegister.getDateStarts(),
                concertRegister.getUserId(),
                concertRegister.getPrice(),
                concertRegister.getNumberAssistants(),
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

        ConcertHistory concertHistory = new ConcertHistory(concert.getId());
        concertHistoryRepository.save(concertHistory);

        return new ResponseEntity(concert, HttpStatus.valueOf(200));
    }

    @GetMapping("/home/suggestions/{userId}")
    public ResponseEntity getConcertsForHome(@PathVariable String userId) {
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

            /* Add only non booked concerts */
            if (!concertBookedIdsController.contains(concertId)) {
                String ownerId = concert.getUserId();
                ArrayList<String> artistsParticipating = concert.getArtistsIds();
                Artist artist = artistRepository.findByUserId(ownerId);

                /* Check if user is following artist or at least one of the participating */
                boolean userIsFollowingSomeParticipatingArtist = userIsFollowingSomeParticipatingArtist(artistIdsPreferences, artistsParticipating);
                if (artistIdsPreferences.contains(artist.getUserId()) || userIsFollowingSomeParticipatingArtist) {

                    /* Check if concert already added */
                    addConcert(concertSuggestions, concertIdsController, concert, concertId);
                } else {
                    String artistMusicStyle = artist.getMusicalStyleId();

                    /* Give user some suggestion of music style preferences matching */
                    if (musicStyleIdsPreferences.contains(artistMusicStyle)) {
                        addConcert(concertSuggestions, concertIdsController, concert, concertId);
                    } else {
                        /* Get participating artists music styles */
                        for (int x = 0; x < artistsParticipating.size(); x++){
                            String artistId = artistsParticipating.get(x);
                            Artist participatingArtist = artistRepository.findByUserId(artistId);

                            String participatingArtistMusicStyle = participatingArtist.getMusicalStyleId();
                            if (musicStyleIdsPreferences.contains(participatingArtistMusicStyle)) {
                                addConcert(concertSuggestions, concertIdsController, concert, concertId);
                            }
                        }
                    }
                }
            }
        }

        return new ResponseEntity(concertSuggestions, HttpStatus.valueOf(200));
    }

    private void addConcert(ArrayList<ConcertReduced> concertSuggestions, ArrayList<String> concertIdsController, Concert concert, String concertId) {
        if (!concertIdsController.contains(concertId)) {
            ConcertLocation concertLocation = concertLocationRepository.findByConcertId(concert.getId());
            ConcertReduced concertReduced = createConcertReduced(concert, concertLocation);
            concertSuggestions.add(concertReduced);
            concertIdsController.add(concertId);
        }
    }

    private boolean userIsFollowingSomeParticipatingArtist(ArrayList<String> artistIdsPreferences, ArrayList<String> artistsParticipating) {
        for (String artistId : artistsParticipating) {
            if (artistIdsPreferences.contains(artistId)) {
                return true;
            }
        }
        return false;
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
                currentConcert.getPrice(),
                currentConcert.getDateStarts(),
                currentConcert.getNumberAssistants(),
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
        int concertNumberAssistants = concert.getNumberAssistants();

        return concertNumberAssistants - concertBookings;
    }

}
