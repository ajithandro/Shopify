package com.shopify.shopify.Retrofit;

public class ApiConfig {

    public static final String BASE_URL="https://answerarea.000webhostapp.com/";
    public static RetrofitInterface getServiceclass(){
        return Retrofitservice.getRetrofit(BASE_URL).create(RetrofitInterface.class);
    }
}
