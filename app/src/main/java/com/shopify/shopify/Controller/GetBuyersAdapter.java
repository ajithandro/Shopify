package com.shopify.shopify.Controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shopify.shopify.Helper.Controller;
import com.shopify.shopify.Model.Buyer_Details;
import com.shopify.shopify.R;
import com.shopify.shopify.View.ViewProducts;

import java.util.List;

public class GetBuyersAdapter extends RecyclerView.Adapter<GetBuyersAdapter.Myviewholder> {
    Context context;
    private LayoutInflater mInflater;
    List<Buyer_Details> mData;

    public GetBuyersAdapter(Context context, List<Buyer_Details> mData) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public GetBuyersAdapter.Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_viewbuyerdesign, parent, false);
        return new GetBuyersAdapter.Myviewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull GetBuyersAdapter.Myviewholder holder, int position) {
        final Buyer_Details listItem = mData.get(position);
        holder.setIsRecyclable(false);
        holder.buyername.setText(listItem.getU_Name());
        holder.buyeraddress.setText(listItem.getU_Address());
        holder.viewstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("UID",listItem.getU_Id());
                bundle.putString("PHN",listItem.getU_PhoneNo());
                Intent intent=new Intent(context, ViewProducts.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder {
        TextView buyername, buyeraddress;
        ImageView viewstatus;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            buyername = itemView.findViewById(R.id.uitvbuyername);
            buyeraddress = itemView.findViewById(R.id.uitvbuyeraddress);
            viewstatus = itemView.findViewById(R.id.uiivbuyerview);
        }
    }
}
