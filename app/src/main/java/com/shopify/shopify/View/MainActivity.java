package com.shopify.shopify.View;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.shopify.shopify.Helper.Controller;
import com.shopify.shopify.Helper.MovePage;
import com.shopify.shopify.R;

public class MainActivity extends AppCompatActivity {
    Button user, shop;
    Bundle bundle;
    Intent intent;
    private static final int REQUEST_CODE = 101;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = findViewById(R.id.uibtnloginuser);
        shop = findViewById(R.id.uibtnloginshopowner);
        progressBar=findViewById(R.id.progressBar);
        new Controller(this);
        user.setVisibility(View.GONE);
        shop.setVisibility(View.GONE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.SEND_SMS}, REQUEST_CODE);

                return;
            } else {
                connectionverify();
            }
        }


        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveuser("user");


            }
        });
        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveuser("shop");
            }
        });


    }

    private void moveuser(String type) {
        bundle = new Bundle();
        bundle.putString("type", type);
        intent = new Intent(MainActivity.this, LoginPage.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    void connectionverify() {
        if (isNetworkAvailable()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (Controller.getUID() == null) {
                        user.setVisibility(View.VISIBLE);
                        shop.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);

                    } else {
                        if (Controller.getType().equals(Controller.USER)){
                            MovePage.move(MainActivity.this,Orderproduct.class);
                            finish();
                        }else {
                            MovePage.move(MainActivity.this,GetOrders.class);
                            finish();
                        }
                    }
                }
            },2000);

        } else {
            MovePage.showtoast("Please check your internet connection", MainActivity.this);


        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    connectionverify();
                }
                break;
        }


    }
}
