package com.example.tfg.Controllers.Artist;

import com.example.tfg.Entities.Artist.Artist;
import com.example.tfg.Entities.Authentication.AuthenticationData;
import com.example.tfg.Helpers.Helpers;
import com.example.tfg.Helpers.ResponseInfo;
import com.example.tfg.Repositories.Artist.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/artist")
public class ArtistsController {

    @Autowired
    private ArtistRepository artistRepository;

    @PostMapping("/create")
    public ResponseEntity createNewArtist(@RequestBody Artist artist) {
        artistRepository.insert(artist);

        return new ResponseEntity(artist, HttpStatus.valueOf(200));
    }
}
