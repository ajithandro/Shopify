package com.shopify.shopify.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.shopify.shopify.Model.Order_Details;

import java.util.List;

@Dao
public interface Daocontroller {


    @Insert
    long insertimage(Order_Details order_details);

    @Query("SELECT * FROM " + ProductsDB.USER_TABLE )
    List<Order_Details> getrecords();

    @Update
    int updateproduct(Order_Details order_details);

    @Delete
    int deleteTodo(Order_Details order_details);



}
