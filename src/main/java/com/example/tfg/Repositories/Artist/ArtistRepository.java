package com.example.tfg.Repositories.Artist;

import com.example.tfg.Entities.Artist.Artist;
import com.example.tfg.Entities.User.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArtistRepository extends MongoRepository<Artist, String> {

    Artist findByUserId(String userId);
}
