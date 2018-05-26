package com.tech.tle.posystemandroid.Models;

/**
 * Created by nykbMac on 26/05/2018.
 */

public class User {


    private int id;
    private String Name;
    private String Dob;
    private String Gender;
    private String Username;
    private String Password;
    private String Email;
    private String Phone;
    private String Address;
    private String RoleType;
    private String ProfileUrl;
    private String LastSignIn;
    private String Token;
    private  int organization_id;


    public User() {
    }


    public User(int id, String name, String dob, String gender, String username, String password, String email, String phone, String address, String roleType, String profileUrl, String lastSignIn, String token, int organization_id) {
        this.id = id;
        Name = name;
        Dob = dob;
        Gender = gender;
        Username = username;
        Password = password;
        Email = email;
        Phone = phone;
        Address = address;
        RoleType = roleType;
        ProfileUrl = profileUrl;
        LastSignIn = lastSignIn;
        Token = token;
        this.organization_id = organization_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDob() {
        return Dob;
    }

    public void setDob(String dob) {
        Dob = dob;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getRoleType() {
        return RoleType;
    }

    public void setRoleType(String roleType) {
        RoleType = roleType;
    }

    public String getProfileUrl() {
        return ProfileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        ProfileUrl = profileUrl;
    }

    public String getLastSignIn() {
        return LastSignIn;
    }

    public void setLastSignIn(String lastSignIn) {
        LastSignIn = lastSignIn;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public int getOrganization_id() {
        return organization_id;
    }

    public void setOrganization_id(int organization_id) {
        this.organization_id = organization_id;
    }
}
