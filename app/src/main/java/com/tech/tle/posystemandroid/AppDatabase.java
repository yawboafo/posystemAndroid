package com.tech.tle.posystemandroid;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;

import com.tech.tle.posystemandroid.Models.Product;
import com.tech.tle.posystemandroid.Models.ShoppingCart;
import com.tech.tle.posystemandroid.ModelsDAO.ProductDao;
import com.tech.tle.posystemandroid.ModelsDAO.ShoppingCartDAO;

import java.util.List;

/**
 * Created by smsgh on 28/05/2018.
 */

@Database(entities = { Product.class, ShoppingCart.class},
        version = 2)

public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase db;

    public static AppDatabase getAppDatabase(Context context) {
       if (db == null) {
           db = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "store-front")
                   // don't do this on a real app!
                   //.allowMainThreadQueries()
                   .build();
       }
       return db;
   }

    public static void destroyInstance() {
        db = null;
    }

    public abstract ProductDao getProductDao();
    public abstract ShoppingCartDAO getShoppingCartdDao();



}
