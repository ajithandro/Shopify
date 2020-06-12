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

public class Createuseraccount extends AppCompatActivity {


    EditText username, phoneno, address, pincode, password, conpassword;
    Button submit;
    Toolbar toolbar;
    String name, pno, add, pin, pass, conpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createuseraccount);
        initviews();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sign Up");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = username.getText().toString();
                pno = phoneno.getText().toString();
                add = address.getText().toString();
                pin = pincode.getText().toString();
                pass = password.getText().toString();
                conpass = conpassword.getText().toString();
                if (name.isEmpty() && pno.isEmpty() && add.isEmpty() && pin.isEmpty() && pass.isEmpty() && conpass.isEmpty()) {
                    MovePage.showtoast("Enter all the empty fields", Createuseraccount.this);

                }  else if (pass.equals(conpass)) {
                    createaccount();
                } else {
                    MovePage.showtoast("Password mismatch", Createuseraccount.this);

                }


            }
        });


    }

    private void initviews() {
        username = findViewById(R.id.uiedcreateuser);
        phoneno = findViewById(R.id.uiedcreatephone);
        address = findViewById(R.id.uiedcreateaddress);
        pincode = findViewById(R.id.uiedcreatepincode);
        password = findViewById(R.id.uiedcreatepassword);
        conpassword = findViewById(R.id.uiedcreateconpassword);
        submit = findViewById(R.id.uibtnadduser);
        toolbar = findViewById(R.id.uitbuseraccount);

    }

    private void createaccount() {
        ShowProgress.show("Processing...", Createuseraccount.this);
        Call<String> call = ApiConfig.getServiceclass().createnewuser(name, pno, add, pin, conpass);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                MovePage.showtoast("Register Success", Createuseraccount.this);
                Log.d("Error", response.body());
                ShowProgress.dismiss();
                Bundle bundle = new Bundle();
                bundle.putString("type", "user");
                Intent intent = new Intent(Createuseraccount.this, LoginPage.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("Error", t.getMessage());
                MovePage.showtoast("Register Failed", Createuseraccount.this);

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MovePage.move(Createuseraccount.this, MainActivity.class);
    }
}
