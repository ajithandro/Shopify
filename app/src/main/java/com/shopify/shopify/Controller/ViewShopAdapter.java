package com.shopify.shopify.Controller;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.shopify.shopify.Database.ProductsDB;
import com.shopify.shopify.Helper.Controller;
import com.shopify.shopify.Helper.ShowProgress;
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

public class ViewShopAdapter extends RecyclerView.Adapter<ViewShopAdapter.Myviewholder> {
    Context context;
    private LayoutInflater mInflater;
    List<Shop_Details> mData;
    List<Order_Details> order_details;
    ProductsDB productsDB;
    JSONArray order_detailsJSONArray;
    JSONObject jsonObject;
    Dialog dialog;
    String shopno;


    public ViewShopAdapter(Context context, List<Shop_Details> mData) {
        productsDB = Room.databaseBuilder(context, ProductsDB.class, ProductsDB.DB_NAME).build();
        order_detailsJSONArray = new JSONArray();
        new Controller(context);
        this.mInflater = LayoutInflater.from(context);
        dialog=new Dialog(context);
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewShopAdapter.Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_viewshopdesign, parent, false);
        return new Myviewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewShopAdapter.Myviewholder holder, int position) {
        final Shop_Details listItem = mData.get(position);
        holder.setIsRecyclable(false);
        holder.shopname.setText(listItem.getS_Shopname());
        holder.shopownername.setText(listItem.getS_Ownername());
        holder.shopaddress.setText(listItem.getS_Address());
        shopno = listItem.getS_Contactno().trim();
        holder.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showdialog(listItem.getS_Id());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder {
        TextView shopname, shopownername, shopaddress;
        ImageView next;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            shopname = itemView.findViewById(R.id.uitvviewproductname);
            shopownername = itemView.findViewById(R.id.uitvviewproductinfo);
            shopaddress = itemView.findViewById(R.id.uitvviewproductquantity);
            next = itemView.findViewById(R.id.uiivnext);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private void getlocalrecords(final String sid) {
        new AsyncTask<String, Void, List<Order_Details>>() {

            @Override
            protected List<Order_Details> doInBackground(String... strings) {
                order_details = productsDB.daocontroller().getrecords();
                return productsDB.daocontroller().getrecords();
            }

            @Override
            protected void onPostExecute(List<Order_Details> addDetails) {
                super.onPostExecute(addDetails);
                for (int i = 0; i < order_details.size(); i++) {
                    try {

                        jsonObject = new JSONObject();
                        jsonObject.put("U_Id", Controller.getUID());
                        jsonObject.put("S_Id", sid);
                        jsonObject.put("product", order_details.get(i).getProduct());
                        jsonObject.put("product_info", order_details.get(i).getDescription());
                        jsonObject.put("quantity", order_details.get(i).getQuantity());
                        jsonObject.put("status", order_details.get(i).getStatus());
                        order_detailsJSONArray.put(jsonObject);
                        Log.d("Ajith", Controller.getUID());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                placeordertoshop();
            }
        }.execute();

    }

    private void placeordertoshop() {
        ShowProgress.show("Uploading...", context);
        Call<String> call = ApiConfig.getServiceclass().placeorder(order_detailsJSONArray);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ShowProgress.dismiss();
                confirmdialog();
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(shopno, null, "Shopify...\n One new order received...", null, null);
                Log.d("Status", response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("Status", t.getMessage());
            }
        });

    }

    private void showdialog(final String uid) {
        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(context);
        builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT);
        builder.setTitle("Hey there ! Need Confirmation!");
        builder.setMessage("are you want to send  list of product's to this shop...");
        builder.addButton("YES", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                getlocalrecords(uid);

            }
        });

        builder.addButton("NO", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void confirmdialog() {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.layout_confrmdialog);
        Button confrimbtn = dialog.findViewById(R.id.uibtndone);
        TextView textView=dialog.findViewById(R.id.uitvdtext);
        textView.setText("Order Placed");
        confrimbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               dialog.dismiss();
            }
        });
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    }


}
