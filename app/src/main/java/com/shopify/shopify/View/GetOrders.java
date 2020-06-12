package com.shopify.shopify.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.shopify.shopify.Controller.GetBuyersAdapter;
import com.shopify.shopify.Controller.ViewShopAdapter;
import com.shopify.shopify.Helper.Controller;
import com.shopify.shopify.Helper.MovePage;
import com.shopify.shopify.Helper.ShowProgress;
import com.shopify.shopify.Model.Buyer_Details;
import com.shopify.shopify.Model.DownloadResponse;
import com.shopify.shopify.R;
import com.shopify.shopify.Retrofit.ApiConfig;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetOrders extends AppCompatActivity {
    MaterialToolbar toolbar;
    RecyclerView recyclerView;
    List<Buyer_Details> buyer_details;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_orders);
        initviews();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    private void getbuyers(){
        ShowProgress.show("Getting Orders...",GetOrders.this);
        Call<DownloadResponse> call= ApiConfig.getServiceclass().getOrders(Controller.getUID());
        call.enqueue(new Callback<DownloadResponse>() {
            @Override
            public void onResponse(Call<DownloadResponse> call, Response<DownloadResponse> response) {
                buyer_details = response.body().getBuyer_Details();
                ShowProgress.dismiss();
                if (buyer_details.size() == 0) {
                    recyclerView.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.VISIBLE);
                } else {
                    linearLayout.setVisibility(View.GONE);
                    GetBuyersAdapter getBuyersAdapter=new GetBuyersAdapter(GetOrders.this,buyer_details);
                    recyclerView.setAdapter(getBuyersAdapter);

                }

            }

            @Override
            public void onFailure(Call<DownloadResponse> call, Throwable t) {

            }
        });

    }
    private void initviews(){
        new Controller(this);
        toolbar=findViewById(R.id.uitbgetorders);
        recyclerView=findViewById(R.id.uirvgetorders);
        linearLayout=findViewById(R.id.uilinemptygetorders);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("New Orders");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getbuyers();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_logout, menu);
       // MenuItem logout = menu.findItem(R.id.logout);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.logout:
                ShowProgress.show("Logout...", GetOrders.this);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Controller.clearuserdetails();
                        ShowProgress.dismiss();
                       finish();
                        MovePage.move(GetOrders.this,MainActivity.class);
                    }
                }, 2000);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}