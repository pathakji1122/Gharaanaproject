package com.beginnner.gharaana.Object;

import com.beginnner.gharaana.Entity.Order;

public class CancelOrderResponse {
    public String response;
    public Boolean status;
    public Order order;

    public CancelOrderResponse(String response, boolean status, Order order){
        this.response=response;
        this.status=status;
        this.order=order;
    }
}
