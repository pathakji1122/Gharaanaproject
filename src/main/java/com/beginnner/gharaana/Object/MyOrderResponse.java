package com.beginnner.gharaana.Object;

import com.beginnner.gharaana.Entity.Order;

import java.util.List;

public class MyOrderResponse {
    public Boolean success;
    public String response;
    public List<Order> orderList;

    public MyOrderResponse(Boolean success, String response, List<Order> orderList) {
        this.success=success;
        this.response = response;
        this.orderList=orderList;
    }
}
