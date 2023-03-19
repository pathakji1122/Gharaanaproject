package com.beginnner.gharaana.Object;

import com.beginnner.gharaana.Entity.Order;

public class StartOrderResponse {
    public String response;
    public Boolean status;
    public Order order;

    public StartOrderResponse(String response, Boolean status, Order order) {
        this.response = response;
        this.status = status;
        this.order = order;
    }
}
