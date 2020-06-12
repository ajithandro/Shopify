package com.shopify.shopify.Controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.button.MaterialButton;
import com.shopify.shopify.Database.ProductsDB;
import com.shopify.shopify.Helper.MovePage;
import com.shopify.shopify.Model.Order_Details;
import com.shopify.shopify.R;
import com.shopify.shopify.View.Orderproduct;

import java.util.List;

public class AddProductAdapter extends RecyclerView.Adapter<AddProductAdapter.Myviewholder> {

    Context context;
    private LayoutInflater mInflater;
    List<Order_Details> mData;
    ProductsDB productsDB;
    Order_Details details;


    public AddProductAdapter(Context context, List<Order_Details> mData) {
        productsDB = Room.databaseBuilder(context, ProductsDB.class, ProductsDB.DB_NAME).build();
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = mData;
        details=new Order_Details();
    }

    @NonNull
    @Override
    public AddProductAdapter.Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_vieworderedproducts, parent, false);
        return new AddProductAdapter.Myviewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final AddProductAdapter.Myviewholder holder, final int position) {
        final Order_Details listItem = mData.get(position);
        holder.setIsRecyclable(false);
        holder.product.setText(listItem.getProduct());
        holder.discription.setText(listItem.getDescription());
        holder.count.setText(listItem.getQuantity());
        holder.min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quan = Integer.valueOf(holder.count.getText().toString());
                if (quan != 0) {
                    listItem.setQuantity(String.valueOf(quan - 1));
                    holder.count.setText(listItem.getQuantity());
                    details.UId = listItem.getUId();
                    details.Product = listItem.getProduct();
                    details.Description = listItem.getDescription();
                    details.quantity = listItem.getQuantity();
                    details.Status = listItem.getStatus();
                    updateoder(listItem);
                }
            }
        });
        holder.max.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quan = Integer.valueOf(holder.count.getText().toString());
                if (quan != 10) {
                    listItem.setQuantity(String.valueOf(quan + 1));
                    holder.count.setText(listItem.getQuantity());
                    details.UId = listItem.getUId();
                    details.Product = listItem.getProduct();
                    details.Description = listItem.getDescription();
                    details.quantity = listItem.getQuantity();
                    details.Status = listItem.getStatus();
                    updateoder(listItem);
                }
            }
        });

        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteorder(listItem);
                mData.remove(position);
                ((Orderproduct)context).loadalldata();
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder {
        TextView product, discription;
        ImageView cancel;
        MaterialButton min, max;
        EditText count;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            product = itemView.findViewById(R.id.uitvaddproductname);
            discription = itemView.findViewById(R.id.uitvaddproductdis);
            cancel = itemView.findViewById(R.id.uiivcancel);
            min = itemView.findViewById(R.id.uibtnmin);
            max = itemView.findViewById(R.id.uibtnmax);
            count = itemView.findViewById(R.id.uiedcount);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private void updateoder(Order_Details details) {
        new AsyncTask<Order_Details, Void, Integer>() {

            @Override
            protected Integer doInBackground(Order_Details... order_details) {
                return productsDB.daocontroller().updateproduct(order_details[0]);
            }

            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
                MovePage.showtoast("Updated", context);
            }
        }.execute(details);

    }

    @SuppressLint("StaticFieldLeak")
    private void deleteorder( Order_Details details){
        new AsyncTask<Order_Details,Void,Integer>(){

            @Override
            protected Integer doInBackground(Order_Details... order_details) {
                return productsDB.daocontroller().deleteTodo(order_details[0]);
            }

            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
            }
        }.execute(details);
    }
}
