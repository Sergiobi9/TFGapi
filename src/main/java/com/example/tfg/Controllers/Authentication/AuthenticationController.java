package com.example.tfg.Controllers.Authentication;

import com.example.tfg.Entities.Artist.Artist;
import com.example.tfg.Entities.Authentication.AuthenticationData;
import com.example.tfg.Entities.User.User;
import com.example.tfg.Helpers.Constants;
import com.example.tfg.Helpers.Helpers;
import com.example.tfg.Helpers.ResponseInfo;
import com.example.tfg.Repositories.Artist.ArtistRepository;
import com.example.tfg.Repositories.User.UserRepository;
import com.example.tfg.Security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationData authenticationData) {
        Map<Object, Object> model = new HashMap<>();

        String userEmail = authenticationData.getEmail();
        String password = authenticationData.getPassword();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userEmail, password));
        User user = userRepository.findUserByEmail(userEmail);

        if (Helpers.isNotNull(user)){
            String token = jwtTokenProvider.createToken(userEmail, user.getRoles());
            model.put("user", user);
            model.put("token", token);
        }

        return new ResponseEntity(model, HttpStatus.valueOf(200));
    }

    @PostMapping("/dashboard/login")
    public ResponseEntity loginDashboard(@RequestBody AuthenticationData authenticationData) {
        Map<Object, Object> model = new HashMap<>();

        String userEmail = authenticationData.getEmail();
        String password = authenticationData.getPassword();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userEmail, password));
        User user = userRepository.findUserByEmail(userEmail);

        if (user != null){
            if (user.getUserRole().equals(Constants.USER_ROLE)){
                model.put("info", "user has to register as artist");
            } else {
                Artist artist = artistRepository.findByUserId(user.getId());
                String token = jwtTokenProvider.createToken(userEmail, user.getRoles());
                model.put("user", user);
                model.put("token", token);
                model.put("artist", artist);
            }
        } else {
            model.put("info", "user do not exist");
        }

        return new ResponseEntity(model, HttpStatus.valueOf(200));
    }


}
