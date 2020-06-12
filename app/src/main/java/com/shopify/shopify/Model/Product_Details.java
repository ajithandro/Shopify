package com.shopify.shopify.Model;

public class Product_Details {
    private String Product;
    private String Product_Info;
    private String Product_Quantity;


    // Getter Methods

    public String getProduct() {
        return Product;
    }

    public String getProduct_Info() {
        return Product_Info;
    }

    public String getProduct_Quantity() {
        return Product_Quantity;
    }

    // Setter Methods

    public void setProduct(String Product) {
        this.Product = Product;
    }

    public void setProduct_Info(String Product_Info) {
        this.Product_Info = Product_Info;
    }

    public void setProduct_Quantity(String Product_Quantity) {
        this.Product_Quantity = Product_Quantity;
    }
}
