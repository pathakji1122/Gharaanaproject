package com.beginnner.gharaana.Service;

public class OrderResponce {
    public Boolean status;
    public String orderId;
    public String responce;

    public OrderResponce(Boolean status,String orderId, String responce) {
        this.status=status;
        this.orderId=orderId;
        this.responce=responce;
    }
}
