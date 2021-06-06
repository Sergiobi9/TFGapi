package com.example.tfg.Controllers.Artist;

import com.example.tfg.Entities.Artist.Artist;
import com.example.tfg.Entities.Artist.ArtistProfileInfo;
import com.example.tfg.Entities.Artist.ArtistSimplified;
import com.example.tfg.Entities.Artist.ArtistUserProfile;
import com.example.tfg.Entities.ArtistSocialMediaLinks.ArtistSocialMediaLinks;
import com.example.tfg.Entities.Concert.ArtistProfileConcertInfo;
import com.example.tfg.Entities.Concert.Concert;
import com.example.tfg.Entities.MusicStyle.MusicStyle;
import com.example.tfg.Entities.Role.Role;
import com.example.tfg.Entities.User.User;
import com.example.tfg.Entities.User.UserPreferences;
import com.example.tfg.Helpers.Constants;
import com.example.tfg.Helpers.DateUtils;
import com.example.tfg.Helpers.ImageStorage;
import com.example.tfg.Helpers.ResponseInfo;
import com.example.tfg.Repositories.Artist.ArtistRepository;
import com.example.tfg.Repositories.ArtistSocialMediaLinks.ArtistSocialMediaLinksRepository;
import com.example.tfg.Repositories.Concert.ConcertRepository;
import com.example.tfg.Repositories.MusicStyle.MusicStyleRepository;
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
    private ConcertRepository concertRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MusicStyleRepository musicStyleRepository;

    @Autowired
    private UserPreferencesRepository userPreferencesRepository;

    @Autowired
    private ArtistSocialMediaLinksRepository artistSocialMediaLinksRepository;

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

                String artistProfileImage = ImageStorage.getArtistImage(currentUserArtist.getId());

                ArtistSimplified artistUserRegisterSelection = new ArtistSimplified(currentArtist.getUserId(), currentArtist.getArtistName(), artistProfileImage, currentArtist.getMusicalStyleId());
                artistsToReturn.add(artistUserRegisterSelection);
            }
        }

        return new ResponseEntity(artistsToReturn, HttpStatus.valueOf(200));
    }

    @GetMapping("/all/{userId}")
    public ResponseEntity getAllArtistsExceptUser(@PathVariable("userId") String userId) {

        List<Artist> allArtists = artistRepository.findAll();
        allArtists.removeIf(x -> x.getUserId().equals(userId));

        ArrayList<ArtistSimplified> artistsToReturn = new ArrayList<>();

        for (int i = 0; i < allArtists.size(); i++){
            Artist currentArtist = allArtists.get(i);
            String artistProfileImage = ImageStorage.getArtistImage(currentArtist.getUserId());

            ArtistSimplified artistUserRegisterSelection = new ArtistSimplified(currentArtist.getUserId(), currentArtist.getArtistName(), artistProfileImage, currentArtist.getMusicalStyleId());
            artistsToReturn.add(artistUserRegisterSelection);
        }


        return new ResponseEntity(artistsToReturn, HttpStatus.valueOf(200));
    }

    @GetMapping("/all")
    public ResponseEntity getAllArtists() {

        List<Artist> allArtists = artistRepository.findAll();
        ArrayList<ArtistSimplified> artistsToReturn = new ArrayList<>();

        for (int i = 0; i < allArtists.size(); i++){
            Artist currentArtist = allArtists.get(i);
            String artistProfileImage = ImageStorage.getArtistImage(currentArtist.getUserId());

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
                    String artistProfileImage = ImageStorage.getArtistImage(currentArtist.getUserId());

                    ArtistSimplified artistUserRegisterSelection = new ArtistSimplified(currentArtist.getUserId(), currentArtist.getArtistName(), artistProfileImage, currentArtist.getMusicalStyleId());
                    artistsToReturn.add(artistUserRegisterSelection);
                }
            }
        }

        return new ResponseEntity(artistsToReturn, HttpStatus.valueOf(200));
    }

    @GetMapping("/info/{currentDate}/{artistId}/{userId}")
    public ResponseEntity getArtistInfo(@PathVariable("currentDate") String currentDate, @PathVariable("artistId") String artistId, @PathVariable("userId") String userId) {

        ArtistSocialMediaLinks artistSocialMediaLinks = artistSocialMediaLinksRepository.findArtistSocialMediaLinksByUserId(artistId);

        Artist artist = artistRepository.findByUserId(artistId);
        User artistAsUser = userRepository.findUserById(artistId);
        UserPreferences userPreferences = userPreferencesRepository.findUserPreferencesByUserId(userId);

        if (artistSocialMediaLinks == null) {
            artistSocialMediaLinks = new ArtistSocialMediaLinks(artistId);
            artistSocialMediaLinksRepository.insert(artistSocialMediaLinks);
            artist.setArtistSocialMediaLinksId(artistSocialMediaLinks.getId());
            artistRepository.save(artist);
        }

        if (userPreferences == null){
            userPreferences = new UserPreferences(userId);
            userPreferencesRepository.insert(userPreferences);
        }

        ArrayList<String> userFollowingArtistsIds = userPreferences.getArtistsIds();
        boolean followingArtist = userFollowingArtistsIds.contains(artistId);

        MusicStyle artistMusicStyle = musicStyleRepository.findMusicStyleById(artist.getMusicalStyleId());
        String artistMusicStyleName = artistMusicStyle.getName();

        List<Concert> artistConcerts = concertRepository.findAllByUserId(artistId);
        List<Concert> artistConcertsContributing = concertRepository.findAllByArtistsIdsContaining(artistId);

        artistConcerts.addAll(artistConcertsContributing);

        ArtistProfileInfo artistProfileInfo = getArtistProfileInfo(artist,
                artistAsUser, artistSocialMediaLinks, followingArtist,
                artistMusicStyleName, artistConcerts, currentDate);

        return new ResponseEntity(artistProfileInfo, HttpStatus.valueOf(200));
    }

    @GetMapping("/info/artistId/{artistId}")
    public ResponseEntity getArtistUserProfile(@PathVariable("artistId") String artistId) {

        ArtistSocialMediaLinks artistSocialMediaLinks = artistSocialMediaLinksRepository.findArtistSocialMediaLinksByUserId(artistId);

        Artist artist = artistRepository.findByUserId(artistId);
        User artistAsUser = userRepository.findUserById(artistId);

        MusicStyle artistMusicStyle = musicStyleRepository.findMusicStyleById(artist.getMusicalStyleId());
        String artistMusicStyleName = artistMusicStyle.getName();

        ArtistUserProfile artistUserProfile = getArtistUserProfile(artist,
                artistAsUser, artistSocialMediaLinks,
                artistMusicStyleName);

        return new ResponseEntity(artistUserProfile, HttpStatus.valueOf(200));
    }

    @GetMapping("/follow/{artistId}/{userId}/{follow}")
    public ResponseEntity followArtist(@PathVariable("artistId") String artistId,
                                       @PathVariable("userId") String userId,
                                       @PathVariable("follow") boolean follow) {

        UserPreferences userPreferences = userPreferencesRepository.findUserPreferencesByUserId(userId);

        if (userPreferences == null){
            userPreferences = new UserPreferences();
            userPreferences.setUserId(userId);
            userPreferencesRepository.save(userPreferences);
        }

        ArrayList<String> userArtistIdsPreferences = userPreferences.getArtistsIds();
        if (userArtistIdsPreferences == null) userArtistIdsPreferences = new ArrayList<>();

        if (follow){
            Artist artist = artistRepository.findByUserId(artistId);
            ArrayList<String> musicStylesPreferences = userPreferences.getMusicStylesIds();

            /* Following new artist that user would like his/her style */
            String artistMusicStyle = artist.getMusicalStyleId();
            if (!musicStylesPreferences.contains(artist.getMusicalStyleId())){
                musicStylesPreferences.add(artistMusicStyle);
                userPreferences.setMusicStylesIds(musicStylesPreferences);
            }

            if (!userArtistIdsPreferences.contains(artistId)){
                userArtistIdsPreferences.add(artistId);
                userPreferences.setArtistsIds(userArtistIdsPreferences);
            }
        } else {
            userArtistIdsPreferences.remove(artistId);
        }

        userPreferences.setArtistsIds(userArtistIdsPreferences);
        userPreferencesRepository.save(userPreferences);

        return new ResponseEntity(HttpStatus.valueOf(200));
    }

    @GetMapping("/following/userId/{userId}")
    public ResponseEntity getArtistsFollowingByUserId(@PathVariable String userId) {

        UserPreferences userPreferences = userPreferencesRepository.findUserPreferencesByUserId(userId);
        ArrayList<ArtistSimplified> artistsFollowing = new ArrayList<>();

        if (userPreferences != null){
            ArrayList<String> artistsIds = userPreferences.getArtistsIds();

            for (int i = 0; i < artistsIds.size(); i++){
                String artistId = artistsIds.get(i);

                Artist artist = artistRepository.findByUserId(artistId);
                ArtistSimplified artistSimplified = getArtistSimplified(artist);

                artistsFollowing.add(artistSimplified);
            }
        }

        return new ResponseEntity(artistsFollowing, HttpStatus.valueOf(200));
    }

    private ArtistSimplified getArtistSimplified(Artist artist){
        return new ArtistSimplified(artist.getUserId(), artist.getArtistName(), ImageStorage.getArtistImage(artist.getUserId()), artist.getMusicalStyleId());
    }

    private ArtistProfileInfo getArtistProfileInfo(Artist artist,
                                                   User artistAsUser,
                                                   ArtistSocialMediaLinks artistSocialMediaLinks,
                                                   boolean followingArtist,
                                                   String artistMusicStyleName,
                                                   List<Concert> artistConcert,
                                                   String currentDate){

        List<String> followers = userPreferencesRepository.findAllByArtistsIdsContaining(artist.getUserId());

        ArtistProfileInfo artistProfileInfo = new ArtistProfileInfo();
        artistProfileInfo.setArtistId(artist.getUserId());
        artistProfileInfo.setArtistName(artist.getArtistName());
        artistProfileInfo.setCountry(artistAsUser.getCountry());
        artistProfileInfo.setGender(artistAsUser.getGender());
        artistProfileInfo.setBio(artist.getBio());
        artistProfileInfo.setProfileUrl(ImageStorage.getArtistImage(artist.getUserId()));
        artistProfileInfo.setMusicalStyle(artist.getMusicalStyleId());
        artistProfileInfo.setSpotifyLink(artistSocialMediaLinks.getSpotifyLink());
        artistProfileInfo.setFacebookLink(artistSocialMediaLinks.getFacebookLink());
        artistProfileInfo.setTwitterLink(artistSocialMediaLinks.getTwitterLink());
        artistProfileInfo.setInstagramLink(artistSocialMediaLinks.getInstagramLink());
        artistProfileInfo.setYoutubeLink(artistSocialMediaLinks.getYoutubeLink());
        artistProfileInfo.setSnapchatLink(artistSocialMediaLinks.getSnapchatLink());
        artistProfileInfo.setFollowers(followers.size());
        artistProfileInfo.setMemberSince(artist.getArtistSince());
        ArrayList<ArtistProfileConcertInfo> artistConcertsToReturn = new ArrayList<>();
        for (int i = 0; i < artistConcert.size(); i++){
            Concert concert = artistConcert.get(i);
            if (DateUtils.currentDateIsBefore(concert.getDateStarts(), currentDate)){
                ArtistProfileConcertInfo artistProfileConcertInfo = new ArtistProfileConcertInfo(concert);
                artistProfileConcertInfo.setCoverImage(ImageStorage.getConcertCoverImage(artistProfileConcertInfo.getConcertId()));
                artistConcertsToReturn.add(artistProfileConcertInfo);
            }
        }
        artistProfileInfo.setNumberOfConcerts(artistConcertsToReturn);
        artistProfileInfo.setMusicStyleName(artistMusicStyleName);
        artistProfileInfo.setFollowing(followingArtist);

        return artistProfileInfo;
    }

    private ArtistUserProfile getArtistUserProfile(Artist artist,
                                                   User artistAsUser,
                                                   ArtistSocialMediaLinks artistSocialMediaLinks,
                                                   String artistMusicStyleName){

        ArtistUserProfile artistUserProfile = new ArtistUserProfile();
        artistUserProfile.setArtistId(artist.getUserId());
        artistUserProfile.setArtistName(artist.getArtistName());
        artistUserProfile.setCountry(artistAsUser.getCountry());
        artistUserProfile.setGender(artistAsUser.getGender());
        artistUserProfile.setBio(artist.getBio());
        artistUserProfile.setProfileUrl(ImageStorage.getArtistImage(artist.getUserId()));
        artistUserProfile.setMusicalStyle(artist.getMusicalStyleId());
        artistUserProfile.setSpotifyLink(artistSocialMediaLinks.getSpotifyLink());
        artistUserProfile.setFacebookLink(artistSocialMediaLinks.getFacebookLink());
        artistUserProfile.setTwitterLink(artistSocialMediaLinks.getTwitterLink());
        artistUserProfile.setInstagramLink(artistSocialMediaLinks.getInstagramLink());
        artistUserProfile.setYoutubeLink(artistSocialMediaLinks.getYoutubeLink());
        artistUserProfile.setSnapchatLink(artistSocialMediaLinks.getSnapchatLink());
        artistUserProfile.setMemberSince(artist.getArtistSince());
        artistUserProfile.setMusicStyleName(artistMusicStyleName);

        return artistUserProfile;
    }
}
