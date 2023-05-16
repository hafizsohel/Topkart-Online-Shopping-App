package com.example.topkartonlineshoppingapp.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "cart_items")
public class MyCartModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    String currentDate;
    String currentTime;
    String productName;
    String productPrice;
    double totalPrice;
    String totalQuantity;

    public MyCartModel() {
    }

    public MyCartModel(int id, String currentDate, String currentTime, String productName, String productPrice, int totalPrice, String totalQuantity) {
        this.id = id;
        this.currentDate = currentDate;
        this.currentTime = currentTime;
        this.productName = productName;
        this.productPrice = productPrice;
        this.totalPrice = totalPrice;
        this.totalQuantity = totalQuantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    @Override
    public String toString() {
        return "MyCartModel{" +
                "currentDate='" + currentDate + '\'' +
                ", currentTime='" + currentTime + '\'' +
                ", productName='" + productName + '\'' +
                ", productPrice='" + productPrice + '\'' +
                ", totalPrice=" + totalPrice +
                ", totalQuantity='" + totalQuantity + '\'' +
                '}';
    }
}
