package com.beginnner.gharaana.Service;

import com.beginnner.gharaana.Entity.Order;

public class AcceptOrderResponce {
    public Order order;
    public String  responce;
    public Boolean status;

    public AcceptOrderResponce(String responce,Order order,Boolean status) {
        this.responce=responce;
        this.order = order;
        this.status=status;
    }
}
