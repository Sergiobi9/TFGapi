package com.example.tfg.Controllers.User;

import com.example.tfg.Entities.Artist.Artist;
import com.example.tfg.Entities.ArtistSocialMediaLinks.ArtistSocialMediaLinks;
import com.example.tfg.Entities.Role.Role;
import com.example.tfg.Entities.User.User;
import com.example.tfg.Entities.User.UserArtist;
import com.example.tfg.Entities.User.UserPreferences;
import com.example.tfg.Helpers.Constants;
import com.example.tfg.Repositories.Artist.ArtistRepository;
import com.example.tfg.Repositories.ArtistSocialMediaLinks.ArtistSocialMediaLinksRepository;
import com.example.tfg.Repositories.Role.RoleRepository;
import com.example.tfg.Repositories.User.UserPreferencesRepository;
import com.example.tfg.Repositories.User.UserRepository;
import com.example.tfg.Security.JwtTokenProvider;
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
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserPreferencesRepository userPreferencesRepository;

    @Autowired
    private ArtistSocialMediaLinksRepository artistSocialMediaLinksRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/create")
    public ResponseEntity createUser(@RequestBody User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByRole(Constants.USER_ROLE);
        user.setRoles(new HashSet<>(Collections.singletonList(userRole)));
        user.setUserRole(Constants.USER_ROLE);

        userRepository.insert(user);

        return new ResponseEntity(user, HttpStatus.valueOf(200));
    }

    @PostMapping("/create/artist")
    public ResponseEntity createUserAsArtist(@RequestBody UserArtist userArtist) {

        User user = userRepository.findUserByEmail(userArtist.getEmail());

        if (user == null) user = new User(userArtist);
        else user = new User(user.getId(), userArtist);

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByRole(Constants.ARTIST_ROLE);
        user.setRoles(new HashSet<>(Collections.singletonList(userRole)));
        user.setUserRole(Constants.ARTIST_ROLE);

        userRepository.save(user);

        ArtistSocialMediaLinks artistSocialMediaLinks = new ArtistSocialMediaLinks(user.getId());
        artistSocialMediaLinksRepository.insert(artistSocialMediaLinks);

        Artist artist = new Artist(user.getId(), userArtist.getArtistName(), userArtist.getBio(), userArtist.getMusicalStyleId(), artistSocialMediaLinks.getId(), userArtist.getArtistSince());
        artistRepository.save(artist);

        Map<Object, Object> model = new HashMap<>();
        String token = jwtTokenProvider.createToken(user.getEmail(), user.getRoles());
        model.put("user", user);
        model.put("artist", artist);
        model.put("token", token);

        return new ResponseEntity(model, HttpStatus.valueOf(200));
    }

    @PutMapping("/update")
    public ResponseEntity updateUser(@RequestBody User user) {
        User currentUser = userRepository.findUserById(user.getId());
        String password = currentUser.getPassword();
        currentUser = user;
        currentUser.setPassword(password);
        userRepository.save(user);
        return new ResponseEntity(user, HttpStatus.valueOf(200));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity deleteUser(@PathVariable String userId) {
        User userToBeDeleted = userRepository.findUserById(userId);
        userRepository.delete(userToBeDeleted);

        return new ResponseEntity(HttpStatus.valueOf(200));
    }

    @GetMapping("/existing/{email}")
    public ResponseEntity checkUserAlreadyExists(@PathVariable String email) {
        Map<Object, Object> model = new HashMap<>();
        User existingUser = userRepository.findUserByEmail(email);

        if (existingUser != null) {
            model.put("info", Constants.USER_EXISTS);
        } else {
            model.put("info", Constants.USER_NOT_EXIST);
        }
        return new ResponseEntity(model, HttpStatus.valueOf(200));
    }

    @GetMapping("/artist/existing/{email}")
    public ResponseEntity checkUserArtistAlreadyExists(@PathVariable String email) {
        Map<Object, Object> model = new HashMap<>();
        User existingUser = userRepository.findUserByEmail(email);

        if (existingUser != null) {
            if (existingUser.getUserRole().equals(Constants.ARTIST_ROLE)){
                model.put("info", Constants.ARTIST_EXIST);
            } else {
                model.put("info", Constants.USER_IS_USER_ROLE);
            }
        } else {
            model.put("info", Constants.USER_NOT_EXIST);
        }
        return new ResponseEntity(model, HttpStatus.valueOf(200));
    }

    @PostMapping("/register/preferences")
    public ResponseEntity registerUserPreferences(@RequestBody UserPreferences userPreferences) {

        return new ResponseEntity(userPreferences, HttpStatus.valueOf(200));
    }

    @GetMapping("/{userId}")
    public ResponseEntity getUserById(@PathVariable String userId) {
        User user = userRepository.findUserById(userId);
        return new ResponseEntity(user, HttpStatus.valueOf(200));
    }
}
