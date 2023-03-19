package com.beginnner.gharaana.Object;

import com.beginnner.gharaana.Entity.Order;

import java.util.List;
public class CheckOrdersResponse {
    public String response;
    public List<Order> orderList;

    public CheckOrdersResponse(String responce, List<Order> orderList) {
        this.response =responce;
        this.orderList=orderList;
    }
}

