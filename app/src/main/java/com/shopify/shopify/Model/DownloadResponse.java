package com.shopify.shopify.Model;

import java.util.List;

public class DownloadResponse {
    private List<Login_Details>  Login_Details;
    private List<Shop_Details> Shop_Details;
    private List<Buyer_Details> Buyer_Details;
    private List<Product_Details> Product_Details;

    public List<com.shopify.shopify.Model.Product_Details> getProduct_Details() {
        return Product_Details;
    }

    public void setProduct_Details(List<com.shopify.shopify.Model.Product_Details> product_Details) {
        Product_Details = product_Details;
    }

    public List<com.shopify.shopify.Model.Buyer_Details> getBuyer_Details() {
        return Buyer_Details;
    }

    public void setBuyer_Details(List<com.shopify.shopify.Model.Buyer_Details> buyer_Details) {
        Buyer_Details = buyer_Details;
    }

    public List<com.shopify.shopify.Model.Shop_Details> getShop_Details() {
        return Shop_Details;
    }

    public void setShop_Details(List<com.shopify.shopify.Model.Shop_Details> shop_Details) {
        Shop_Details = shop_Details;
    }

    public List<com.shopify.shopify.Model.Login_Details> getLogin_Details() {
        return Login_Details;
    }

    public void setLogin_Details(List<com.shopify.shopify.Model.Login_Details> login_Details) {
        Login_Details = login_Details;
    }
}
