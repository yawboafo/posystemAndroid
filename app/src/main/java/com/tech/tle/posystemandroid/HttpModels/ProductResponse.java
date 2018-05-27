package com.tech.tle.posystemandroid.HttpModels;

import com.tech.tle.posystemandroid.Models.Product;

import java.util.List;

/**
 * Created by nykbMac on 27/05/2018.
 */

public class ProductResponse {

    private String code;
    private String status;
    private String message;
    private List<Product> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Product> getData() {
        return data;
    }

    public void setData(List<Product> data) {
        this.data = data;
    }
}
