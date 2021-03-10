package com.example.tfg.Controllers.Concert;

import com.example.tfg.Entities.Concert.Concert;
import com.example.tfg.Entities.Concert.ConcertHistory;
import com.example.tfg.Entities.Concert.ConcertLocation;
import com.example.tfg.Entities.Concert.ConcertRegister;
import com.example.tfg.Repositories.Concert.ConcertHistoryRepository;
import com.example.tfg.Repositories.Concert.ConcertLocationRepository;
import com.example.tfg.Repositories.Concert.ConcertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/create")
    public ResponseEntity createConcert(@RequestBody ConcertRegister concertRegister) {

        ConcertLocation concertLocation = new ConcertLocation(concertRegister.getLatitude(),
                concertRegister.getLongitude(),
                concertRegister.getStreet(),
                concertRegister.getPlaceDescription());

        concertLocationRepository.save(concertLocation);

        Concert concert = new Concert(concertRegister.getName(),
                concertLocation.getId(),
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

        ConcertHistory concertHistory = new ConcertHistory(concert.getId());
        concertHistoryRepository.save(concertHistory);

        return new ResponseEntity(concert, HttpStatus.valueOf(200));
    }
}
