package com.tech.tle.posystemandroid.ModelsDAO;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.tech.tle.posystemandroid.Models.Product;

import java.util.List;

/**
 * Created by smsgh on 28/05/2018.
 */


@Dao
public interface ProductDao {

    @Insert
    void insert(Product product);

    @Update
    void update(Product... product);

    @Delete
    void delete(Product... product);
    @Query("SELECT * FROM product")
    List<Product> getAllProducts();

    /**@Query("SELECT * FROM Product WHERE ProductID=:productID")
    LiveData<List<Product>> findProductByID(final int productID);
**/
    @Query("SELECT * FROM Product WHERE ProductID=:productID")
    Product findProductByIDNow(final int productID);

}
