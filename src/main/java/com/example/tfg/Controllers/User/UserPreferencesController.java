package com.example.tfg.Controllers.User;

import com.example.tfg.Entities.Artist.Artist;
import com.example.tfg.Entities.Role.Role;
import com.example.tfg.Entities.User.User;
import com.example.tfg.Entities.User.UserArtist;
import com.example.tfg.Entities.User.UserPreferences;
import com.example.tfg.Helpers.Constants;
import com.example.tfg.Repositories.Artist.ArtistRepository;
import com.example.tfg.Repositories.Role.RoleRepository;
import com.example.tfg.Repositories.User.UserPreferencesRepository;
import com.example.tfg.Repositories.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/user/preferences")
public class UserPreferencesController {

    @Autowired
    private UserPreferencesRepository userPreferencesRepository;

    @PutMapping("/save")
    public ResponseEntity registerUserPreferences(@RequestBody UserPreferences userPreferences) {
        userPreferencesRepository.save(userPreferences);
        return new ResponseEntity(userPreferences, HttpStatus.valueOf(200));
    }
}
