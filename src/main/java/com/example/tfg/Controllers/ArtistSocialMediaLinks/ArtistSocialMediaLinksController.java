package com.example.tfg.Controllers.ArtistSocialMediaLinks;

import com.example.tfg.Entities.Artist.Artist;
import com.example.tfg.Entities.ArtistSocialMediaLinks.ArtistSocialMediaLinks;
import com.example.tfg.Entities.Role.Role;
import com.example.tfg.Entities.User.User;
import com.example.tfg.Helpers.Constants;
import com.example.tfg.Helpers.ResponseInfo;
import com.example.tfg.Repositories.ArtistSocialMediaLinks.ArtistSocialMediaLinksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/artistSocialMediaLinks")
public class ArtistSocialMediaLinksController {

    @Autowired
    private ArtistSocialMediaLinksRepository artistSocialMediaLinksRepository;

    @PostMapping("/create")
    public ResponseEntity createArtistSocialMediaLinks(@RequestBody ArtistSocialMediaLinks artistSocialMediaLinks) {
        artistSocialMediaLinksRepository.save(artistSocialMediaLinks);
        return new ResponseEntity(artistSocialMediaLinks, HttpStatus.valueOf(200));
    }
}
