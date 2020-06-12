package com.shopify.shopify.Database;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.shopify.shopify.Model.Order_Details;

@Database(entities = Order_Details.class, version = 1, exportSchema = false)
public abstract class ProductsDB extends RoomDatabase {
    public static final String DB_NAME = "productDB";
    public static final String USER_TABLE = "productinfo";

    public abstract Daocontroller daocontroller();
}
