package com.tech.tle.posystemandroid.Models;

/**
 * Created by nykbMac on 26/05/2018.
 */

public class Organization {

    private int id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private String type;
    private int organizationCategoryId;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getOrganizationCategoryId() {
        return organizationCategoryId;
    }

    public void setOrganizationCategoryId(int organizationCategoryId) {
        this.organizationCategoryId = organizationCategoryId;
    }


}
