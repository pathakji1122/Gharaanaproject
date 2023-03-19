package com.beginnner.gharaana.Object;

import com.beginnner.gharaana.Entity.Order;

public class OrderStatusResponse {
    public Order order;
    public Boolean status;
    public String response;
    public OrderStatusResponse(Boolean status, String response, Order order) {
        this.status=status;
        this.response=response;
        this.order=order;
    }
}
