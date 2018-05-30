package com.tech.tle.posystemandroid;

/**
 * Created by nykbMac on 26/05/2018.
 */

import android.arch.persistence.room.Room;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class Application extends android.app.Application{

   public static String AppCurrency = "GHS";

   public static String AppOrganization = "";


    private static Application mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;



    }

    public static synchronized Application getInstance() {
        return mInstance;
    }


}
