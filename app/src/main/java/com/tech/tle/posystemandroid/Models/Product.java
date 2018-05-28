package com.tech.tle.posystemandroid.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by nykbMac on 26/05/2018.
 */


@Entity
public class Product {



    @PrimaryKey
    private int ProductID;
    private String SKU;
    private String IDSKU;
    private String Name;
    private String Description;
    private int Quantity;
    private int UnitPrice;
    private String Discount;
    private String IsAvailable;
    private String Ranking;
    private String Datecreated;
    private String DateUpdated;
    private String ImageUrl;
    private int CategoryID;
    private int Organization_id;


    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int productID) {
        ProductID = productID;
    }

    public String getSKU() {
        return SKU;
    }

    public void setSKU(String SKU) {
        this.SKU = SKU;
    }

    public String getIDSKU() {
        return IDSKU;
    }

    public void setIDSKU(String IDSKU) {
        this.IDSKU = IDSKU;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public int getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        UnitPrice = unitPrice;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getIsAvailable() {
        return IsAvailable;
    }

    public void setIsAvailable(String isAvailable) {
        IsAvailable = isAvailable;
    }

    public String getRanking() {
        return Ranking;
    }

    public void setRanking(String ranking) {
        Ranking = ranking;
    }

    public String getDatecreated() {
        return Datecreated;
    }

    public void setDatecreated(String datecreated) {
        Datecreated = datecreated;
    }

    public String getDateUpdated() {
        return DateUpdated;
    }

    public void setDateUpdated(String dateUpdated) {
        DateUpdated = dateUpdated;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public int getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(int categoryID) {
        CategoryID = categoryID;
    }

    public int getOrganization_id() {
        return Organization_id;
    }

    public void setOrganization_id(int organization_id) {
        Organization_id = organization_id;
    }
}


