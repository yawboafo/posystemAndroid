package com.tech.tle.posystemandroid.ModelsDAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.tech.tle.posystemandroid.Models.ShoppingCart;

import java.util.List;


@Dao
public interface ShoppingCartDAO
{

    @Insert
    void insert(ShoppingCart shoppingCart);

    @Update
    void update(ShoppingCart... shoppingCart);

    @Delete
    void delete(ShoppingCart... shoppingCart);


    @Query("SELECT * FROM shoppingCart")
    List<ShoppingCart> getAllShoppingCarts();


    @Query("SELECT * FROM shoppingCart WHERE id=:id")
    ShoppingCart findShoppingCartByIDNow(final int id);

    @Query("SELECT * FROM shoppingCart WHERE productID=:productID")
    ShoppingCart findShoppingCartByProduct(final String productID);



    @Query("SELECT * FROM shoppingCart WHERE productID=:productID")
    List<ShoppingCart> getAllShoppingCartByProduct(final String productID);

}
