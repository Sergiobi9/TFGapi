package com.example.tfg.Entities.User;

public class UserArtist {

    public String firstName;
    public String lastName;
    public String country;
    private String city;
    private String zipCode;
    public int gender;
    public String birthday;
    public String email;
    public String password;
    private String artistName;
    private String bio;
    private String musicalStyleId;
    private String artistSince;

    public UserArtist() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getMusicalStyleId() {
        return musicalStyleId;
    }

    public void setMusicalStyleId(String musicalStyleId) {
        this.musicalStyleId = musicalStyleId;
    }

    public String getArtistSince() {
        return artistSince;
    }

    public void setArtistSince(String artistSince) {
        this.artistSince = artistSince;
    }
}
