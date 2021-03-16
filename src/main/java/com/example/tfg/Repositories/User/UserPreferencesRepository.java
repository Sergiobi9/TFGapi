package com.example.tfg.Repositories.User;

import com.example.tfg.Entities.User.User;
import com.example.tfg.Entities.User.UserPreferences;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserPreferencesRepository extends MongoRepository<UserPreferences, String> {

    UserPreferences findUserPreferencesByArtistsId(String artistsId);
}
