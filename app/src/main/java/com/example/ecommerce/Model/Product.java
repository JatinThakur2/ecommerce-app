package com.example.ecommerce.Model;

import java.io.Serializable;

public class Product implements Serializable {

    String category, date, description, imageUrl, name, price, productid, time, sellerid, state;

    public Product() {
    }

    public Product(String category, String date, String description, String imageUrl, String name, String price, String productid, String time, String sellerid, String state) {
        this.category = category;
        this.date = date;
        this.description = description;
        this.imageUrl = imageUrl;
        this.name = name;
        this.price = price;
        this.productid = productid;
        this.time = time;
        this.sellerid = sellerid;
        this.state = state;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSellerid() {
        return sellerid;
    }

    public void setSellerid(String sellerid) {
        this.sellerid = sellerid;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}