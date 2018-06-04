package com.tech.tle.posystemandroid.Models;

import java.util.List;

/**
 * Created by nykbMac on 29/05/2018.
 */

public  class MemoryData {

    public static Product activeProduct;
    public static List<ShoppingCart> activeShoppingCart;

    public static Product getActiveProduct() {
        return activeProduct;
    }

    public static void setActiveProduct(Product activeProduct) {
        MemoryData.activeProduct = activeProduct;
    }

    public static List<ShoppingCart> getActiveShoppingCart() {
        return activeShoppingCart;
    }

    public static void setActiveShoppingCart(List<ShoppingCart> activeShoppingCart) {
        MemoryData.activeShoppingCart = activeShoppingCart;
    }
}
