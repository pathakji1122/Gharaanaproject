package com.beginnner.gharaana.Service;

import com.beginnner.gharaana.Entity.Order;

import java.util.List;

public class MyOrderResponce {

    public Boolean success;
    public String  responce;
    public List<Order>orderList;

    public MyOrderResponce(boolean success, String responce, List<Order> orderList) {
        this.success=success;
        this.responce=responce;
        this.orderList=orderList;
    }
}
