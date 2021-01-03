package com.example.tfg.Controllers.Authentication;

import com.example.tfg.Entities.Authentication.AuthenticationData;
import com.example.tfg.Helpers.Helpers;
import com.example.tfg.Helpers.ResponseInfo;
import com.example.tfg.Repositories.User.UserRepository;
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

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationData authenticationData) {
        Map<Object, Object> model = new HashMap<>();

        String userEmail = authenticationData.getEmail();
        String password = authenticationData.getPassword();

        // User userRetrieved = userRepository.findUserByEmail(userEmail);

        if (Helpers.isNotNull(userEmail)){
            model.put(ResponseInfo.INFO, ResponseInfo.LOGIN_INTENT_SUCESS);
        } else {
            model.put(ResponseInfo.INFO, ResponseInfo.LOGIN_INTENT_FAILED);
        }

        return new ResponseEntity(model, HttpStatus.valueOf(200));
    }
}
