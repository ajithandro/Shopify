package com.shopify.shopify.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.shopify.shopify.Helper.Controller;
import com.shopify.shopify.Helper.MovePage;
import com.shopify.shopify.Helper.ShowProgress;
import com.shopify.shopify.Model.DownloadResponse;
import com.shopify.shopify.Model.Login_Details;
import com.shopify.shopify.R;
import com.shopify.shopify.Retrofit.ApiConfig;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPage extends AppCompatActivity {
    TextView adduser, addshop;
    EditText phoneno, password;
    Button loginbtn;
    String REQ,phn,pass;
    List<Login_Details> login_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        initviews();
        new Controller(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String type = bundle.getString("type");
        if (type.equals("user")) {
            adduser.setVisibility(View.VISIBLE);
            REQ = "1";
        } else {
            addshop.setVisibility(View.VISIBLE);
            REQ = "2";
        }
        adduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovePage.move(LoginPage.this, Createuseraccount.class);
            }
        });
        addshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovePage.move(LoginPage.this, Addnewshop.class);

            }
        });
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phn=phoneno.getText().toString();
                pass=password.getText().toString();
                if (phn.isEmpty() && pass.isEmpty()){
                    MovePage.showtoast("please enter phoneno and password...",LoginPage.this);
                }
                else {
                    checkaccount();
                }
            }
        });


    }

    private void initviews() {
        adduser = findViewById(R.id.uitvadduser);
        addshop = findViewById(R.id.uitvaddshop);
        phoneno = findViewById(R.id.uiedloginphone);
        password = findViewById(R.id.uiedloginpassword);
        loginbtn = findViewById(R.id.uibtnloginpage);

    }
    private void checkaccount(){
        ShowProgress.show("Account Verifying",LoginPage.this);
        Call<DownloadResponse> call= ApiConfig.getServiceclass().Checkaccount(phn,pass,REQ);
        call.enqueue(new Callback<DownloadResponse>() {
            @Override
            public void onResponse(Call<DownloadResponse> call, Response<DownloadResponse> response) {
                if (response.isSuccessful()){
                    ShowProgress.dismiss();
                    login_details=response.body().getLogin_Details();
                    if (login_details.get(0).getU_Type().equals("00")){
                        MovePage.showtoast("Username or password incorrect",LoginPage.this);
                    }else {
                        Controller.addUserID(login_details.get(0).getU_Id());
                        Controller.addPincode(login_details.get(0).getU_Pincode());
                        Controller.addType(login_details.get(0).getU_Type());
                        Log.d("Check", login_details.get(0).getU_Id());
                        if (Controller.getType().equals("1")) {
                            Controller.addType(Controller.USER);

                            MovePage.move(LoginPage.this, Orderproduct.class);
                            finish();

                        } else {
                            Controller.addType(Controller.SHOPKEEPER);
                            finish();
                            MovePage.move(LoginPage.this, GetOrders.class);
                            finish();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<DownloadResponse> call, Throwable t) {

            }
        });

    }
}
