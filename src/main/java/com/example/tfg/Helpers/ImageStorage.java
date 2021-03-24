package com.example.tfg.Helpers;

import java.net.ContentHandler;

public class ImageStorage {

    private static final String MUSIC_STYLE_STORAGE = "https://music-styles.s3.us-east-2.amazonaws.com/";
    private static final String PNG_SUFFIX = ".png";

    private static final String ARTIST_STORAGE = "https://artists-tfg.s3.us-east-2.amazonaws.com/";
    private static final String CONCERT_IMAGES_STORAGE = "https://concerts-images-tfg.s3.us-east-2.amazonaws.com/";

    public static String getConcertCoverImage(String concertId){
        return CONCERT_IMAGES_STORAGE + concertId + "_cover" + PNG_SUFFIX;
    }

    public static String getArtistImage(String userId) {
        return ARTIST_STORAGE + userId + PNG_SUFFIX;
    }

    public static String getConcertPlaceImage(String concertId, int photoNumber){
        return CONCERT_IMAGES_STORAGE + concertId + "_" + photoNumber + PNG_SUFFIX;
    }

    public static String getMusicStyleImage(String musicStyleId){
        return MUSIC_STYLE_STORAGE + musicStyleId + PNG_SUFFIX;
    }
}
