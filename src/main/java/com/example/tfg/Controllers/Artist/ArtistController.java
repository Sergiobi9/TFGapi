package com.example.tfg.Controllers.Artist;

import com.example.tfg.Entities.Artist.Artist;
import com.example.tfg.Entities.Artist.ArtistSimplified;
import com.example.tfg.Entities.Role.Role;
import com.example.tfg.Entities.User.User;
import com.example.tfg.Entities.User.UserPreferences;
import com.example.tfg.Helpers.Constants;
import com.example.tfg.Helpers.ImageStorage;
import com.example.tfg.Helpers.ResponseInfo;
import com.example.tfg.Repositories.Artist.ArtistRepository;
import com.example.tfg.Repositories.Role.RoleRepository;
import com.example.tfg.Repositories.User.UserPreferencesRepository;
import com.example.tfg.Repositories.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

    @Autowired
    private UserPreferencesRepository userPreferencesRepository;

    @PostMapping("/create")
    public ResponseEntity createNewArtist(@RequestBody Artist artist) {
        User userToBecomeArtist = userRepository.findUserById(artist.getUserId());

        Map<Object, Object> model = new HashMap<>();
        if (userToBecomeArtist == null) {
            model.put(ResponseInfo.INFO, ResponseInfo.USER_DO_NOT_EXIST);
            return new ResponseEntity(model, HttpStatus.valueOf(200));
        }

        Role userRole = roleRepository.findByRole(Constants.ARTIST_ROLE);
        userToBecomeArtist.setRoles(new HashSet<>(Collections.singletonList(userRole)));
        userToBecomeArtist.setUserRole(Constants.ARTIST_ROLE);

        userRepository.save(userToBecomeArtist);
        artistRepository.insert(artist);

        model.put(ResponseInfo.INFO, ResponseInfo.USER_BECAME_ARTIST_SUCCESS);
        model.put("artist", artist);

        return new ResponseEntity(model, HttpStatus.valueOf(200));
    }

    @PostMapping("/all/styles")
    public ResponseEntity getArtistsByMusicStylesSelected(@RequestBody ArrayList<String> musicStylesIds) {
        ArrayList<ArtistSimplified> artistsToReturn = new ArrayList<>();

        for (int i = 0; i < musicStylesIds.size(); i++) {
            String musicStyleId = musicStylesIds.get(i);
            List<Artist> artistsListByMusicStyles = artistRepository.getArtistsByMusicalStyleId(musicStyleId);

            for (int j = 0; j < artistsListByMusicStyles.size(); j++) {
                Artist currentArtist = artistsListByMusicStyles.get(j);
                User currentUserArtist = userRepository.findUserById(currentArtist.getUserId());

                String artistProfileImage = getArtistImage(currentUserArtist.getId());

                ArtistSimplified artistUserRegisterSelection = new ArtistSimplified(currentArtist.getUserId(), currentArtist.getArtistName(), artistProfileImage, currentArtist.getMusicalStyleId());
                artistsToReturn.add(artistUserRegisterSelection);
            }
        }

        return new ResponseEntity(artistsToReturn, HttpStatus.valueOf(200));
    }

    @GetMapping("/all/{userId}")
    public ResponseEntity getArtists(@PathVariable("userId") String userId) {

        List<Artist> allArtists = artistRepository.findAll();
        allArtists.removeIf(x -> x.getUserId().equals(userId));

        ArrayList<ArtistSimplified> artistsToReturn = new ArrayList<>();

        for (int i = 0; i < allArtists.size(); i++){
            Artist currentArtist = allArtists.get(i);
            String artistProfileImage = getArtistImage(currentArtist.getUserId());

            ArtistSimplified artistUserRegisterSelection = new ArtistSimplified(currentArtist.getUserId(), currentArtist.getArtistName(), artistProfileImage, currentArtist.getMusicalStyleId());
            artistsToReturn.add(artistUserRegisterSelection);
        }

        return new ResponseEntity(artistsToReturn, HttpStatus.valueOf(200));
    }

    @GetMapping("/home/suggested/{userId}")
    public ResponseEntity getSuggestedArtistsToFollow(@PathVariable("userId") String userId) {

        List<Artist> allArtists = artistRepository.findAll();
        UserPreferences userPreferences = userPreferencesRepository.findUserPreferencesByUserId(userId);

        ArrayList<ArtistSimplified> artistsToReturn = new ArrayList<>();
        ArrayList<String> artistsFollowing = userPreferences.getArtistsIds();
        ArrayList<String> musicStylesFollowing = userPreferences.getMusicStylesIds();

        for (int i = 0; i < allArtists.size(); i++){
            Artist currentArtist = allArtists.get(i);

            if (musicStylesFollowing.contains(currentArtist.getMusicalStyleId())){
                if (!artistsFollowing.contains(currentArtist.getUserId())){
                    String artistProfileImage = getArtistImage(currentArtist.getUserId());

                    ArtistSimplified artistUserRegisterSelection = new ArtistSimplified(currentArtist.getUserId(), currentArtist.getArtistName(), artistProfileImage, currentArtist.getMusicalStyleId());
                    artistsToReturn.add(artistUserRegisterSelection);
                }
            }
        }

        return new ResponseEntity(artistsToReturn, HttpStatus.valueOf(200));
    }

    private String getArtistImage(String userId) {
        return ImageStorage.ARTIST_STORAGE + userId + ImageStorage.PNG_SUFFIX;
    }
}
