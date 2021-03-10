package com.example.tfg.Controllers.Concert;

import com.example.tfg.Entities.Artist.Artist;
import com.example.tfg.Entities.Artist.ArtistInfo;
import com.example.tfg.Entities.Concert.*;
import com.example.tfg.Entities.User.User;
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
import java.util.List;

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

    @PostMapping("/home/{userId}")
    public ResponseEntity getConcertsForHome(@PathVariable String userId) {
        ArrayList<ConcertReduced> concertSuggestions = new ArrayList<>();

        List<Concert> concerts = concertRepository.findAll();

        for (int i = 0; i < concerts.size(); i++){
            Concert concert = concerts.get(i);
            ConcertLocation concertLocation = concertLocationRepository.findByConcertId(concert.getId());

            ArrayList<String> concertImages = getConcertImages(concert.getNumberImages());
            String concertCoverImage = getConcertCoverImage(concert.getId());
            ArrayList<ArtistInfo> artistsInfoArrayList = getArtistsInfo(concert.getArtistsIds());

            ConcertReduced concertReduced = new ConcertReduced(
                    concert.getId(),
                    concert.getName(),
                    concertLocation.getLatitude(),
                    concertLocation.getLongitude(),
                    concertLocation.getStreet(),
                    concert.getPrice(),
                    concert.getDateStarts(),
                    concert.getNumberAssistants(),
                    concert.getDescription(),
                    concertLocation.getPlaceDescription(),
                    concert.getExtraDescription(),
                    concertCoverImage,
                    concertImages,
                    artistsInfoArrayList
            );

            concertSuggestions.add(concertReduced);
        }


        return new ResponseEntity(concertSuggestions, HttpStatus.valueOf(200));
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
