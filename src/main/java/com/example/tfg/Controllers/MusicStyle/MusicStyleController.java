package com.example.tfg.Controllers.MusicStyle;

import com.example.tfg.Entities.MusicStyle.MusicStyle;
import com.example.tfg.Repositories.MusicStyle.MusicStyleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/music/style")
public class MusicStyleController {

    @Autowired
    private MusicStyleRepository musicStyleRepository;

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
            String musicStyleImage = getMusicStyleImage(musicStyleId);

            allMusicStyles.get(i).setImageUrl(musicStyleImage);
        }

        return new ResponseEntity(allMusicStyles, HttpStatus.valueOf(200));
    }


    private String getMusicStyleImage(String musicStyleId){
        return "";
    }
}
