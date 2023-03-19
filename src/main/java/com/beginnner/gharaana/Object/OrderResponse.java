package com.beginnner.gharaana.Object;

public class OrderResponse {
    public Boolean status;
    public String orderId;
    public String response;

    public OrderResponse(Boolean status, String orderId, String responce) {
        this.status=status;
        this.orderId=orderId;
        this.response =responce;
    }
}
