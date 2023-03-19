package com.beginnner.gharaana.Object;

import com.beginnner.gharaana.Entity.Order;

public class AcceptOrderResponse {
    public Order order;
    public String  response;
    public Boolean status;

    public AcceptOrderResponse(String response, Order order, Boolean status) {
        this.response=response;
        this.order = order;
        this.status=status;
    }
}
