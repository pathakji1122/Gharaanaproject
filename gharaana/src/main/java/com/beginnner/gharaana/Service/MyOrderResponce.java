package com.beginnner.gharaana.Service;

import com.beginnner.gharaana.Entity.Order;

import java.util.List;

public class MyOrderResponce {
    public MyOrderResponce(List<Order> orderList) {
        this.orderList=orderList;
    }

    public List<Order>orderList;
}
