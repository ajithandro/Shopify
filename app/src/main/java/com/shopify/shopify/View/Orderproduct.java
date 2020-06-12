package com.shopify.shopify.View;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.shopify.shopify.Controller.AddProductAdapter;
import com.shopify.shopify.Database.ProductsDB;
import com.shopify.shopify.Helper.Controller;
import com.shopify.shopify.Helper.MovePage;
import com.shopify.shopify.Helper.ShowProgress;
import com.shopify.shopify.Model.Order_Details;
import com.shopify.shopify.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class Orderproduct extends AppCompatActivity {
    EditText product, dis;
    Button addtocart, placeorderbtn;
    RecyclerView recyclerView;
    Toolbar toolbar;
    List<Order_Details> order_details;
    JSONArray order_detailsJSONArray;
    JSONObject jsonObject;
    String sid, sname, sowner, sphone;
    ProductsDB productsDB;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderproduct);
        initviews();

        order_detailsJSONArray = new JSONArray();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productname = product.getText().toString();
                String quantity = dis.getText().toString();
                if (productname.isEmpty() && quantity.isEmpty()) {
                    MovePage.showtoast("Enter Empty Fields...", Orderproduct.this);
                } else {
                    final Order_Details orderproduct = new Order_Details();
                    orderproduct.setUId(Controller.getUID());
                    orderproduct.setProduct(productname);
                    orderproduct.setDescription(quantity);
                    orderproduct.setQuantity("1");
                    orderproduct.setStatus("0");
                    inserttolocal(orderproduct);
                }


            }
        });

        placeorderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // placeordertoshop();
                if (order_details.size() == 0) {
                    MovePage.showtoast("Add Some product's", Orderproduct.this);
                } else {
                    MovePage.move(Orderproduct.this, ViewShops.class);
                }

            }
        });


    }

    private void initviews() {
        new Controller(this);
        product = findViewById(R.id.uiedaddproductname);
        dis = findViewById(R.id.uiedaddproductquantity);
        addtocart = findViewById(R.id.uibtnaddcart);
        recyclerView = findViewById(R.id.uircaddproduct);
        toolbar = findViewById(R.id.uitborderproduct);
        placeorderbtn = findViewById(R.id.uibtnaddproduct);
        linearLayout = findViewById(R.id.uilinemptyorderproduct);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Product's");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        productsDB = Room.databaseBuilder(Orderproduct.this, ProductsDB.class, ProductsDB.DB_NAME).build();

    }


    @SuppressLint("StaticFieldLeak")
    private void inserttolocal(Order_Details odetails) {
        new AsyncTask<Order_Details, Void, Long>() {

            @Override
            protected Long doInBackground(Order_Details... order_details) {
                return productsDB.daocontroller().insertimage(order_details[0]);
            }

            @Override
            protected void onPostExecute(Long aLong) {
                super.onPostExecute(aLong);
                loadalldata();
            }
        }.execute(odetails);

    }

    @SuppressLint("StaticFieldLeak")
    public void loadalldata() {
        new AsyncTask<String, Void, List<Order_Details>>() {

            @Override
            protected List<Order_Details> doInBackground(String... strings) {
                order_details = productsDB.daocontroller().getrecords();
                return productsDB.daocontroller().getrecords();
            }

            @Override
            protected void onPostExecute(List<Order_Details> addDetails) {
                super.onPostExecute(addDetails);
                if (order_details.size() == 0) {
                    recyclerView.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.VISIBLE);
                } else {
                    linearLayout.setVisibility(View.GONE);
                    getview();
                }
            }
        }.execute();

    }

    private void getview() {
        AddProductAdapter addProductAdapter = new AddProductAdapter(Orderproduct.this, order_details);
        recyclerView.setAdapter(addProductAdapter);
        product.setText("");
        dis.setText("");
        product.requestFocus();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadalldata();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_logout, menu);
        MenuItem logout = menu.findItem(R.id.logout);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.logout:
                ShowProgress.show("Logout...", Orderproduct.this);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Controller.clearuserdetails();
                        ShowProgress.dismiss();
                        finish();
                        MovePage.move(Orderproduct.this,MainActivity.class);
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