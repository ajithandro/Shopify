package com.shopify.shopify.Retrofit;

import com.shopify.shopify.Model.DownloadResponse;

import org.json.JSONArray;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitInterface {


    @FormUrlEncoded
    @POST("/addnewuseraccount.php")
    Call<String> createnewuser(@Field("U_Name") String uname,
                               @Field("U_PhoneNo") String phone,
                               @Field("U_Address") String address,
                               @Field("U_Pincode") String pincode,
                               @Field("U_Password") String password);

    @GET("/Login.php")
    Call<DownloadResponse> Checkaccount(@Query("U_PhoneNo") String phone,
                                        @Query("U_Password") String password,
                                        @Query("U_Type") String type);

    @GET("/getshops.php")
    Call<DownloadResponse> getShops(@Query("Pincode") String pincode);

    @GET("/getorders.php")
    Call<DownloadResponse> getOrders(@Query("sid") String sid);

    @GET("/getproducts.php")
    Call<DownloadResponse> getProducts(@Query("UID") String UID);


    @FormUrlEncoded
    @POST("/addnewshop.php")
    Call<String> addnewshop(@Field("S_Name") String uname,
                            @Field("S_OwnerName") String ownername,
                            @Field("S_ContactNo") String contactno,
                            @Field("S_Address") String address,
                            @Field("S_Pincode") String pincode,
                            @Field("S_Password") String password);

    @FormUrlEncoded
    @POST("/placeorder.php")
    Call<String> placeorder(@Field("Orderarray") JSONArray order_details);

    @FormUrlEncoded
    @POST("/updateorder.php")
    Call<String> updateorder(@Field("UID") String UID);


}
