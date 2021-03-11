package com.example.tfg.Controllers.Concert;

import com.example.tfg.Entities.Artist.Artist;
import com.example.tfg.Entities.Artist.ArtistInfo;
import com.example.tfg.Entities.Concert.*;
import com.example.tfg.Entities.User.User;
import com.example.tfg.Helpers.Constants;
import com.example.tfg.Repositories.Artist.ArtistRepository;
import com.example.tfg.Repositories.Concert.ConcertHistoryRepository;
import com.example.tfg.Repositories.Concert.ConcertLocationRepository;
import com.example.tfg.Repositories.Concert.ConcertRepository;
import com.example.tfg.Repositories.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private ArtistRepository artistRepository;

    @PostMapping("/create")
    public ResponseEntity createConcert(@RequestBody ConcertRegister concertRegister) {

        Concert concert = new Concert(concertRegister.getName(),
                concertRegister.getDateCreated(),
                concertRegister.getDateStarts(),
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
                concertRegister.getStreet(),
                concertRegister.getPlaceDescription());

        concertLocationRepository.save(concertLocation);

        ConcertHistory concertHistory = new ConcertHistory(concert.getId());
        concertHistoryRepository.save(concertHistory);

        return new ResponseEntity(concert, HttpStatus.valueOf(200));
    }

    @GetMapping("/home/{userId}")
    public ResponseEntity getConcertsForHome(@PathVariable String userId) {
        ArrayList<ConcertReduced> concertSuggestions = new ArrayList<>();

        List<Concert> concerts = concertRepository.findAll();

        for (int i = 0; i < concerts.size(); i++){
            Concert concert = concerts.get(i);
            ConcertLocation concertLocation = concertLocationRepository.findByConcertId(concert.getId());

            ConcertReduced concertReduced = createConcertReduced(concert, concertLocation);

            concertSuggestions.add(concertReduced);
        }


        return new ResponseEntity(concertSuggestions, HttpStatus.valueOf(200));
    }

    @GetMapping("/map/{userLatitude}/{userLongitude}/{radius}")
    public ResponseEntity getConcertsNearUser(@PathVariable double userLatitude,
                                              @PathVariable double userLongitude,
                                              @PathVariable double radius){

        ArrayList<ConcertReduced> nearConcerts = new ArrayList<>();
        List<Concert> concerts = concertRepository.findAll();

        for(int i = 0; i < concerts.size(); i++) {

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

    private ConcertReduced createConcertReduced(Concert currentConcert, ConcertLocation concertLocation) {
        ArrayList<String> concertImages = getConcertImages(currentConcert.getNumberImages());
        String concertCoverImage = getConcertCoverImage(currentConcert.getId());
        ArrayList<ArtistInfo> artistsInfoArrayList = getArtistsInfo(currentConcert.getArtistsIds());

        ConcertReduced concertReduced = new ConcertReduced(
                currentConcert.getId(),
                currentConcert.getName(),
                concertLocation.getLatitude(),
                concertLocation.getLongitude(),
                concertLocation.getStreet(),
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

    private String getConcertCoverImage(String concertId){
        return "https://www.edmradio.es/wp-content/uploads/2020/03/THE-WEEKND.jpg";
    }

    private ArrayList<String> getConcertImages(int numberImages){
        ArrayList<String> concertImagesArrayList = new ArrayList<>();

        return concertImagesArrayList;
    }

    private ArrayList<ArtistInfo> getArtistsInfo(ArrayList<String> artistsIds) {
        ArrayList<ArtistInfo> artistsInfoArrayList = new ArrayList<>();

        for (int i = 0; i < artistsIds.size(); i++){
            String artistId = artistsIds.get(i);

            User userRetrieved = userRepository.findUserById(artistId);
            Artist artistRetrieved = artistRepository.findByUserId(artistId);

            String artistProfileImage = getArtistProfileImage(artistId);

            ArtistInfo artistInfo = new ArtistInfo(
                    userRetrieved.getId(),
                    artistRetrieved.getArtistName(),
                    userRetrieved.getCountry(),
                    userRetrieved.getGender(),
                    artistProfileImage,
                    artistRetrieved.getBio(),
                    artistRetrieved.getMusicalStyle());

            artistsInfoArrayList.add(artistInfo);
        }

        return artistsInfoArrayList;
    }

    private String getArtistProfileImage(String artistId){
        return "";
    }
}
