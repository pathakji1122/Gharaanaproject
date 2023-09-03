package com.beginnner.gharaana.Object;

import com.beginnner.gharaana.Entity.Customer;

public class CustomerProfileResponse {
    public Boolean status;
    public Customer customer;
    public String response;

    public CustomerProfileResponse(Boolean status, Customer customer, String response) {
        this.status=status;
        this.customer=customer;
        this.response=response;
    }
}
