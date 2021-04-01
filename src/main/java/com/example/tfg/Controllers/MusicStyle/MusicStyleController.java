package com.example.tfg.Controllers.MusicStyle;

import com.example.tfg.Entities.MusicStyle.MusicStyle;
import com.example.tfg.Entities.User.UserPreferences;
import com.example.tfg.Helpers.ImageStorage;
import com.example.tfg.Repositories.MusicStyle.MusicStyleRepository;
import com.example.tfg.Repositories.User.UserPreferencesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/music/style")
public class MusicStyleController {

    @Autowired
    private MusicStyleRepository musicStyleRepository;

    @Autowired
    private UserPreferencesRepository userPreferencesRepository;

    @PostMapping("/create/{musicStyleName}")
    public ResponseEntity createMusicStyle(@PathVariable String musicStyleName) {

        MusicStyle musicStyle = new MusicStyle(musicStyleName);
        musicStyleRepository.save(musicStyle);

        return new ResponseEntity(musicStyle, HttpStatus.valueOf(200));
    }

    @GetMapping("/all")
    public ResponseEntity getMusicalStyles() {

        List<MusicStyle> allMusicStyles = musicStyleRepository.findAll();

        for (int i = 0; i < allMusicStyles.size(); i++){
            String musicStyleId = allMusicStyles.get(i).getId();
            String musicStyleImage = ImageStorage.getMusicStyleImage(musicStyleId);

            allMusicStyles.get(i).setImageUrl(musicStyleImage);
        }

        return new ResponseEntity(allMusicStyles, HttpStatus.valueOf(200));
    }

    @GetMapping("/following/userId/{userId}")
    public ResponseEntity getMusicStylesFollowingByUserId(@PathVariable String userId) {

        UserPreferences userPreferences = userPreferencesRepository.findUserPreferencesByUserId(userId);
        ArrayList<MusicStyle> musicStylesFollowing = new ArrayList<>();

        if (userPreferences != null){
            ArrayList<String> musicStyles = userPreferences.getMusicStylesIds();

            for (int i = 0; i < musicStyles.size(); i++){
                String musicStyleId = musicStyles.get(i);

                MusicStyle musicStyle = musicStyleRepository.findMusicStyleById(musicStyleId);
                musicStyle.setImageUrl(ImageStorage.getMusicStyleImage(musicStyleId));

                musicStylesFollowing.add(musicStyle);
            }
        }

        return new ResponseEntity(musicStylesFollowing, HttpStatus.valueOf(200));
    }
}
