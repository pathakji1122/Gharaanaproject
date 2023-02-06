package com.beginnner.gharaana.Service;

import com.beginnner.gharaana.Entity.Order;

public class StartOrderResponce {
    public String responce;
    public Boolean status;
    public Order order;

    public StartOrderResponce(String responce, Boolean status, Order order) {
        this.responce = responce;
        this.status = status;
        this.order = order;
    }
}
