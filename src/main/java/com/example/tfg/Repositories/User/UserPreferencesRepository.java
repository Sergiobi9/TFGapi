package com.example.tfg.Repositories.User;

import com.example.tfg.Entities.User.User;
import com.example.tfg.Entities.User.UserPreferences;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserPreferencesRepository extends MongoRepository<UserPreferences, String> {

    UserPreferences findUserPreferencesByUserId(String userId);
    List<String> findAllByArtistsIdsContaining(String userId);
}
