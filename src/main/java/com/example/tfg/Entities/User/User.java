package com.example.tfg.Entities.User;

import com.example.tfg.Entities.Role.Role;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document(collection = "USER")
public class User {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String country;
    private String city;
    private String zipCode;
    private int gender;
    private String birthday;
    private String phoneNumber;
    private String email;
    private String password;
    private String profileUrl;
    private String userRole;

    @DBRef(lazy = true)
    private Set<Role> roles;

    public User(){}


    public User(String id, UserArtist userArtist) {
        this.id = id;
        this.firstName = userArtist.getFirstName();
        this.lastName = userArtist.getLastName();
        this.country = userArtist.getCountry();
        this.city = userArtist.getCity();
        this.zipCode = userArtist.getZipCode();
        this.gender = userArtist.getGender();
        this.birthday = userArtist.getBirthday();
        this.email = userArtist.getEmail();
        this.password = userArtist.getPassword();
    }

    public User(UserArtist userArtist) {
        this.firstName = userArtist.getFirstName();
        this.lastName = userArtist.getLastName();
        this.country = userArtist.getCountry();
        this.city = userArtist.getCity();
        this.zipCode = userArtist.getZipCode();
        this.gender = userArtist.getGender();
        this.birthday = userArtist.getBirthday();
        this.email = userArtist.getEmail();
        this.password = userArtist.getPassword();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
