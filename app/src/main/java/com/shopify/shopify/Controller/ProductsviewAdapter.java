package com.shopify.shopify.Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shopify.shopify.Helper.Controller;
import com.shopify.shopify.Model.Product_Details;
import com.shopify.shopify.R;

import java.util.List;

public class ProductsviewAdapter extends RecyclerView.Adapter<ProductsviewAdapter.Myviewholder> {
    Context context;
    private LayoutInflater mInflater;
    List<Product_Details> mData;


    public ProductsviewAdapter(Context context, List<Product_Details> mData) {
        new Controller(context);
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = mData;
    }


    @NonNull
    @Override
    public ProductsviewAdapter.Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_productsviewdesign, parent, false);
        return new Myviewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsviewAdapter.Myviewholder holder, int position) {
        final Product_Details listItem = mData.get(position);
        holder.setIsRecyclable(false);
        if (Controller.display ==1){
            holder.cancel.setVisibility(View.GONE);
        }else {
            holder.cancel.setVisibility(View.VISIBLE);
        }
        holder.product.setText(listItem.getProduct());
        holder.productinfo.setText(listItem.getProduct_Info());
        holder.productquaantity.setText(listItem.getProduct_Quantity());
        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.remove(listItem);
                Controller.cancellist.add(listItem);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder {
        TextView product, productinfo, productquaantity;
        ImageView cancel;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            product = itemView.findViewById(R.id.uitvviewproductname);
            productinfo = itemView.findViewById(R.id.uitvviewproductinfo);
            productquaantity = itemView.findViewById(R.id.uitvviewproductquantity);
            cancel = itemView.findViewById(R.id.uiivproductclear);
        }
    }
}
