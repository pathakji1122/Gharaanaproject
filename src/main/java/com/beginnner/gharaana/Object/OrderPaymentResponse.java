package com.beginnner.gharaana.Object;

public class OrderPaymentResponse {
    public String response;
    public Boolean status;
    public String balance;

    public OrderPaymentResponse(String response, Boolean status) {
        this.response = response;
        this.status = status;
    }
}
