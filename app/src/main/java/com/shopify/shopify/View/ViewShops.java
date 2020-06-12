package com.shopify.shopify.View;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.google.android.material.appbar.MaterialToolbar;
import com.shopify.shopify.Controller.ProductsviewAdapter;
import com.shopify.shopify.Controller.ViewShopAdapter;
import com.shopify.shopify.Database.ProductsDB;
import com.shopify.shopify.Helper.Controller;
import com.shopify.shopify.Helper.MovePage;
import com.shopify.shopify.Helper.ShowProgress;
import com.shopify.shopify.Model.DownloadResponse;
import com.shopify.shopify.Model.Order_Details;
import com.shopify.shopify.Model.Shop_Details;
import com.shopify.shopify.R;
import com.shopify.shopify.Retrofit.ApiConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewShops extends AppCompatActivity {

    RecyclerView recyclerView;
    MaterialToolbar toolbar;
    List<Shop_Details> shop_details;
    List<Order_Details> order_details;
    ProductsDB productsDB;
    JSONArray order_detailsJSONArray;
    JSONObject jsonObject;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_shops);
        initvies();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }


    private void initvies() {
        new Controller(this);
        order_detailsJSONArray = new JSONArray();
        recyclerView = findViewById(R.id.uirvviewshops);
        toolbar = findViewById(R.id.uitbtoolbarviewshop);
        linearLayout=findViewById(R.id.uilinemptyviewshops);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Nearby Shops");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    private void loaddata() {
        ShowProgress.show("Loading...", ViewShops.this);
        Call<DownloadResponse> call = ApiConfig.getServiceclass().getShops(Controller.getPincode());
        call.enqueue(new Callback<DownloadResponse>() {
            @Override
            public void onResponse(Call<DownloadResponse> call, Response<DownloadResponse> response) {
                shop_details = response.body().getShop_Details();
                ShowProgress.dismiss();
                if (shop_details.size() == 0) {
                    recyclerView.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.VISIBLE);
                } else {
                    linearLayout.setVisibility(View.GONE);
                    ViewShopAdapter viewShopAdapter = new ViewShopAdapter(ViewShops.this, shop_details);
                    recyclerView.setAdapter(viewShopAdapter);

                }


            }

            @Override
            public void onFailure(Call<DownloadResponse> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loaddata();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MovePage.move(ViewShops.this,Orderproduct.class);
        finish();
    }
      }
