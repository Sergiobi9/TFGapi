package com.example.tfg.Controllers.Artist;

import com.example.tfg.Entities.Artist.Artist;
import com.example.tfg.Entities.Authentication.AuthenticationData;
import com.example.tfg.Entities.Role.Role;
import com.example.tfg.Entities.User.User;
import com.example.tfg.Helpers.Constants;
import com.example.tfg.Helpers.Helpers;
import com.example.tfg.Helpers.ResponseInfo;
import com.example.tfg.Repositories.Artist.ArtistRepository;
import com.example.tfg.Repositories.Role.RoleRepository;
import com.example.tfg.Repositories.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/artist")
public class ArtistController {

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @PostMapping("/create")
    public ResponseEntity createNewArtist(@RequestBody Artist artist) {
        User userToBecomeArtist = userRepository.findUserById(artist.getUserId());

        Map<Object, Object> model = new HashMap<>();
        if (userToBecomeArtist == null){
            model.put(ResponseInfo.INFO, ResponseInfo.USER_DO_NOT_EXIST);
            return new ResponseEntity(model, HttpStatus.valueOf(200));
        }

        Role userRole = roleRepository.findByRole(Constants.ARTIST_ROLE);
        userToBecomeArtist.setRoles(new HashSet<>(Collections.singletonList(userRole)));
        userToBecomeArtist.setUserRole(Constants.ARTIST_ROLE);

        userRepository.save(userToBecomeArtist);
        artistRepository.insert(artist);

        model.put(ResponseInfo.INFO, ResponseInfo.USER_BECAME_ARTIST_SUCCESS);
        return new ResponseEntity(artist, HttpStatus.valueOf(200));
    }


}
