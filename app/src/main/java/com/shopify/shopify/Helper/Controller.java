package com.shopify.shopify.Helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.shopify.shopify.Model.Product_Details;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    public Context context;
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;
    public static String USER = "user";
    public static String SHOPKEEPER = "shopkeeper";
    public static List<Product_Details> cancellist = new ArrayList<>();
    public static int display = 0;

    public Controller(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    public static void clearuserdetails() {
        editor.clear();
        editor.apply();
    }

    public static void addUserID(String id) {
        editor.putString("UID", id).apply();
    }

    public static String getUID() {
        return sharedPreferences.getString("UID", null);
    }

    public static void addPincode(String pincode) {
        editor.putString("Pincode", pincode).apply();
    }

    public static String getPincode() {
        return sharedPreferences.getString("Pincode", null);
    }

    public static void addType(String pincode) {
        editor.putString("type", pincode).apply();
    }

    public static String getType() {
        return sharedPreferences.getString("type", null);
    }

}
