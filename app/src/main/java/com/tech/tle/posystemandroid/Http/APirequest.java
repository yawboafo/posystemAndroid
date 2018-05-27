package com.tech.tle.posystemandroid.Http;

import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.tech.tle.posystemandroid.Application;


/**
 * Created by nykbMac on 26/05/2018.
 */

public class APirequest {


   public static final String BASEURL = "https://posystemapi.herokuapp.com/";
   public static String getProductURL = APirequest.BASEURL + "getAllProducts";


    public static final String TAG = Application.class
            .getSimpleName();
    private RequestQueue mRequestQueue;


    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(Application.getInstance().getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

}
