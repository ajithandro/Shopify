package com.shopify.shopify.Retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofitservice {

    public static Retrofit getRetrofit(String baseurl) {
        Gson gson = new GsonBuilder().setLenient().create();
        return new Retrofit.Builder().baseUrl(baseurl).addConverterFactory(GsonConverterFactory.create(gson)).build();

    }
}
