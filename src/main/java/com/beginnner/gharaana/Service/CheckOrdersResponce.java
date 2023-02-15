package com.beginnner.gharaana.Service;

import com.beginnner.gharaana.Entity.Order;

import java.util.List;
public class CheckOrdersResponce {
    public String responce;
    public List<Order> orderList;

    public CheckOrdersResponce(String responce, List<Order> orderList) {
        this.responce=responce;
        this.orderList=orderList;
    }
}

