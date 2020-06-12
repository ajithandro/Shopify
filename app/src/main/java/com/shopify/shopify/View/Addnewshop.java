package com.shopify.shopify.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.shopify.shopify.Helper.MovePage;
import com.shopify.shopify.Helper.ShowProgress;
import com.shopify.shopify.R;
import com.shopify.shopify.Retrofit.ApiConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Addnewshop extends AppCompatActivity {

    EditText shopname, ownername, phoneno, address, pincode, password, confirmpassword;
    Button submit;
    Toolbar toolbar;
    String sname, sownername, sphone, saddress, spincode, spassword, sconfirmpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnewshop);
        initviews();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getvalues();
                addshopaccount();
            }
        });


    }

    private void initviews() {
        shopname = findViewById(R.id.uiedshopname);
        ownername = findViewById(R.id.uiedshopownername);
        phoneno = findViewById(R.id.uiedshopcontactno);
        address = findViewById(R.id.uiedshopaddress);
        pincode = findViewById(R.id.uiedshoppincode);
        password = findViewById(R.id.uiedshopconpassword);
        confirmpassword = findViewById(R.id.uiedshopconfirmpassword);
        submit = findViewById(R.id.uibtnshopadd);
        toolbar = findViewById(R.id.uitbaddshop);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sign Up");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


    }

    private void getvalues() {
        sname = shopname.getText().toString();
        sownername = ownername.getText().toString();
        sphone = phoneno.getText().toString();
        saddress = address.getText().toString();
        spincode = pincode.getText().toString();
        spassword = password.getText().toString();
        sconfirmpassword = confirmpassword.getText().toString();

    }

    private void addshopaccount() {
        ShowProgress.show("Loading...", Addnewshop.this);
        Call<String> call = ApiConfig.getServiceclass().addnewshop(sname, sownername, sphone, saddress, spincode, sconfirmpassword);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("status", response.body());
                MovePage.showtoast("Shop Created", Addnewshop.this);
                Bundle bundle = new Bundle();
                bundle.putString("type", "shop");
                Intent intent = new Intent(Addnewshop.this, LoginPage.class);
                intent.putExtras(bundle);
                startActivity(intent);
                ShowProgress.dismiss();
                finish();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}
