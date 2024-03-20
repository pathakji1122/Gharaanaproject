package com.beginnner.gharaana.Object;

public class PaymentIdResponse {
    public String paymentId;
    public boolean status;


    public PaymentIdResponse(String paymentId, boolean status) {
        this.paymentId = paymentId;
        this.status = status;
    }
}
