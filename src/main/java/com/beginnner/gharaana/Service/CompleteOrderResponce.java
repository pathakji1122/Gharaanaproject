package com.beginnner.gharaana.Service;

import com.beginnner.gharaana.Entity.Order;

public class CompleteOrderResponce {
    public boolean status;
    public String responce;

    public CompleteOrderResponce(String responce, boolean status) {
        this.responce=responce;
        this.status=status;
    }

}
