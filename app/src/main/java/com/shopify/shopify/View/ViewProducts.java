package com.shopify.shopify.View;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.shopify.shopify.Controller.ProductsviewAdapter;
import com.shopify.shopify.Helper.Controller;
import com.shopify.shopify.Helper.MovePage;
import com.shopify.shopify.Helper.ShowProgress;
import com.shopify.shopify.Model.DownloadResponse;
import com.shopify.shopify.Model.Product_Details;
import com.shopify.shopify.R;
import com.shopify.shopify.Retrofit.ApiConfig;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewProducts extends AppCompatActivity {
    RecyclerView recyclerView;
    MaterialToolbar toolbar;
    List<Product_Details> product_details;
    LinearLayout linearLayout;
    Button confirm, makecall;
    String uid, phn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_products);
        initvies();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Controller.display = 1;
                Bundle bundle = new Bundle();
                bundle.putString("UID", uid);
                bundle.putString("PHN", phn);
                Intent intent = new Intent(ViewProducts.this, DeliveryReport.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        makecall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phn));
                startActivity(i);

            }
        });
    }


    private void initvies() {

        recyclerView = findViewById(R.id.uirvviewproducts);
        toolbar = findViewById(R.id.uitbviewproducts);
        confirm = findViewById(R.id.uibtnconfirm);
        makecall = findViewById(R.id.uibtnmakecall);
        linearLayout = findViewById(R.id.uilinemptyviewproducts);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Order details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        uid = bundle.getString("UID");
        phn = bundle.getString("PHN");


    }

    private void loaddata() {
        ShowProgress.show("Loading...", ViewProducts.this);
        Call<DownloadResponse> call = ApiConfig.getServiceclass().getProducts(uid);
        call.enqueue(new Callback<DownloadResponse>() {
            @Override
            public void onResponse(Call<DownloadResponse> call, Response<DownloadResponse> response) {
                product_details = response.body().getProduct_Details();
                ShowProgress.dismiss();
                if (product_details.size() == 0) {
                    recyclerView.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.VISIBLE);
                } else {
                    linearLayout.setVisibility(View.GONE);
                    ProductsviewAdapter productsviewAdapter = new ProductsviewAdapter(ViewProducts.this, product_details);
                    recyclerView.setAdapter(productsviewAdapter);

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
        MovePage.move(ViewProducts.this, GetOrders.class);
    }
}