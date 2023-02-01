package com.beginnner.gharaana.Service;

import com.beginnner.gharaana.Entity.Order;

import java.util.ArrayList;
import java.util.List;

public class CheckOrderResponce {
    public List<Order> orderList;


    public CheckOrderResponce(List<Order> orderList) {
        this.orderList=orderList;
    }
}
