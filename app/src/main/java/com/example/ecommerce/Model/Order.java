package com.example.ecommerce.Model;

import java.util.List;

public class Order {

    String orderid,userid, username, userphone, useraddress, sellerid,productname, productid, productimageUrl, productprice, quantity, paymentmode, status, totalamount;

    String time,date;

    Order() {
    }

    public Order(String orderid, String userid, String username, String userphone, String useraddress, String sellerid, String productname, String productid, String productimageUrl, String productprice, String quantity, String paymentmode, String status, String totalamount, String time, String date) {
        this.orderid = orderid;
        this.userid = userid;
        this.username = username;
        this.userphone = userphone;
        this.useraddress = useraddress;
        this.sellerid = sellerid;
        this.productname = productname;
        this.productid = productid;
        this.productimageUrl = productimageUrl;
        this.productprice = productprice;
        this.quantity = quantity;
        this.paymentmode = paymentmode;
        this.status = status;
        this.totalamount = totalamount;
        this.time = time;
        this.date = date;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserphone() {
        return userphone;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    public String getUseraddress() {
        return useraddress;
    }

    public void setUseraddress(String useraddress) {
        this.useraddress = useraddress;
    }

    public String getSellerid() {
        return sellerid;
    }

    public void setSellerid(String sellerid) {
        this.sellerid = sellerid;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
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

    public String getPaymentmode() {
        return paymentmode;
    }

    public void setPaymentmode(String paymentmode) {
        this.paymentmode = paymentmode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(String totalamount) {
        this.totalamount = totalamount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
