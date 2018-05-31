package com.tech.tle.posystemandroid.Models;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;

public class DataTypeConverter {

    private static Gson gson = new Gson();
    @TypeConverter
    public static Product stringToObject(String data) {
        if (data == null) {
            return new Product();
        }

        Type listType = new TypeToken<Product>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String ObjectToString(Product someObjects) {
        return gson.toJson(someObjects);
    }
}
