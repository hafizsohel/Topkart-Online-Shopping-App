package com.example.topkartonlineshoppingapp.models;

public class OrderListModel {
    private String orderId;
    private String subTotal;
    private String discount;
    private double shippingCost;
    private String totalPrice;

    public OrderListModel() {}

    public OrderListModel(String orderId, String subTotal, String discount, double shippingCost, String totalPrice) {
        this.orderId=orderId;
        this.subTotal = subTotal;
        this.discount = discount;
        this.shippingCost = shippingCost;
        this.totalPrice = totalPrice;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public double getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(double shippingCost) {
        this.shippingCost = shippingCost;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

}
