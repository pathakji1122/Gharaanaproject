package com.beginnner.gharaana.Service;

public class OrderResponce {
    public Boolean status;
    public String OrderId;
    public String responce;

    public OrderResponce(Boolean status,String orderId, String responce) {
        this.status=status;
        this.OrderId=orderId;
        this.responce=responce;
    }
}
