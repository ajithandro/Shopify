package com.shopify.shopify.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.shopify.shopify.Database.ProductsDB;

import java.io.Serializable;

@Entity(tableName = ProductsDB.USER_TABLE)
public class Order_Details implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int OId;
    public String UId;
    public String Product;
    public String Description;

    public int getOId() {
        return OId;
    }

    public void setOId(int OId) {
        this.OId = OId;
    }

    public String quantity;
    public String Status;

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }



    public String getUId() {
        return UId;
    }

    public void setUId(String UId) {
        this.UId = UId;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getProduct() {
        return Product;
    }

    public void setProduct(String product) {
        Product = product;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
