package com.beginnner.gharaana.Object;

public class CompleteOrderResponse {
    public boolean status;
    public String response;

    public CompleteOrderResponse(String response, boolean status) {
        this.response =response;
        this.status=status;
    }

}
