package com.shopify.shopify.View;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.shopify.shopify.Controller.ProductsviewAdapter;
import com.shopify.shopify.Helper.Controller;
import com.shopify.shopify.Helper.MovePage;
import com.shopify.shopify.Helper.ShowProgress;
import com.shopify.shopify.R;
import com.shopify.shopify.Retrofit.ApiConfig;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveryReport extends AppCompatActivity {
    MaterialToolbar toolbar;
    RecyclerView recyclerView;
    EditText eddate, edtime;
    Button sendstatus;
    private int mYear, mMonth, mDay;
    String uid, phn;
    Dialog dialog;
    LinearLayout linearLayout;
    StringBuilder stringBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_report);
        initviews();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        sendstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtime.getText().toString().isEmpty() && eddate.getText().toString().isEmpty()){
                    MovePage.showtoast("Delivery Date Or Time is Empty",DeliveryReport.this);
                }else {
                    updateorders();
                }
            }
        });
        eddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(DeliveryReport.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                String date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                eddate.setText(date);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        edtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(DeliveryReport.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        edtime.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.show();
            }
        });

    }

    private void initviews() {
        new Controller(this);
        stringBuilder=new StringBuilder();
        toolbar = findViewById(R.id.uitbdeliveryreport);
        recyclerView = findViewById(R.id.uirvcancellist);
        eddate = findViewById(R.id.uieddeleiverydate);
        edtime = findViewById(R.id.uieddeliverytime);
        sendstatus = findViewById(R.id.uibtnsendstatus);
        linearLayout = findViewById(R.id.uilinemptydeleiveryreport);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        uid = bundle.getString("UID");
        phn = bundle.getString("PHN");
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Set Delivery");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        if (Controller.cancellist.size() == 0) {
            recyclerView.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.VISIBLE);
        } else {
            linearLayout.setVisibility(View.GONE);
            ProductsviewAdapter productsviewAdapter = new ProductsviewAdapter(DeliveryReport.this, Controller.cancellist);
            recyclerView.setAdapter(productsviewAdapter);
        }
    }

    private void updateorders() {
        ShowProgress.show("Loading...", DeliveryReport.this);
        Call<String> call = ApiConfig.getServiceclass().updateorder(uid);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ShowProgress.dismiss();
                MovePage.showtoast("Updated", DeliveryReport.this);
                confirmdialog();

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Controller.display = 0;
        Controller.cancellist.clear();
        Bundle bundle = new Bundle();
        bundle.putString("UID", uid);
        bundle.putString("PHN", phn);
        Intent intent = new Intent(DeliveryReport.this, ViewProducts.class);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    private void confirmdialog() {
        dialog =new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.layout_confrmdialog);
        Button confrimbtn = dialog.findViewById(R.id.uibtndone);
        TextView textView = dialog.findViewById(R.id.uitvdtext);
        textView.setText("Confirmation Message Send Success");
        confrimbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i=0;i<Controller.cancellist.size();i++){
                    stringBuilder.append(Controller.cancellist.get(i).getProduct()+",");
                }

                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phn, null, "Shopify...\n your order Received on "+eddate.getText().toString()+"/"+edtime.getText().toString()+" unavailable Products "+stringBuilder.toString(), null, null);
                MovePage.move(DeliveryReport.this, GetOrders.class);
                dialog.dismiss();
            }
        });
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    }
}