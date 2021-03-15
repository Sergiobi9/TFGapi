package com.example.tfg.Repositories.MusicStyle;

import com.example.tfg.Entities.MusicStyle.MusicStyle;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MusicStyleRepository extends MongoRepository<MusicStyle, String> {

    MusicStyle findMusicStyleById(String musicStyleId);
    MusicStyle findMusicStyleByName(String musicStyleName);
}
