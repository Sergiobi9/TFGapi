package com.example.tfg.Repositories.ArtistSocialMediaLinks;

import com.example.tfg.Entities.Artist.Artist;
import com.example.tfg.Entities.ArtistSocialMediaLinks.ArtistSocialMediaLinks;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ArtistSocialMediaLinksRepository extends MongoRepository<ArtistSocialMediaLinks, String> {

    ArtistSocialMediaLinksRepository findArtistSocialMediaLinksByUserId(String userId);
}
