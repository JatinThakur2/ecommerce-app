package com.example.ecommerce.Model;

public class Cart {
    String userid,sellerid,productid,productname,productimageUrl,productprice,quantity,totalprice;

    Cart(){}

    public Cart(String userid, String sellerid, String productid, String productname, String productimageUrl, String productprice, String quantity, String totalprice) {
        this.userid = userid;
        this.sellerid = sellerid;
        this.productid = productid;
        this.productname = productname;
        this.productimageUrl = productimageUrl;
        this.productprice = productprice;
        this.quantity = quantity;
        this.totalprice = totalprice;
    }


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getSellerid() {
        return sellerid;
    }

    public void setSellerid(String sellerid) {
        this.sellerid = sellerid;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductimageUrl() {
        return productimageUrl;
    }

    public void setProductimageUrl(String productimageUrl) {
        this.productimageUrl = productimageUrl;
    }

    public String getProductprice() {
        return productprice;
    }

    public void setProductprice(String productprice) {
        this.productprice = productprice;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice;
    }
}
