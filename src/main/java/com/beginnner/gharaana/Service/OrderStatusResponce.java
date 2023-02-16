package com.beginnner.gharaana.Service;

import com.beginnner.gharaana.Entity.Order;

public class OrderStatusResponce {
    public Order order;
    public Boolean status;
    public String responce;
    public OrderStatusResponce(Boolean status,String responce,Order order) {
        this.status=status;
        this.responce=responce;
        this.order=order;
    }
}
