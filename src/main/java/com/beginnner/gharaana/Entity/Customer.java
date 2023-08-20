package com.beginnner.gharaana.Entity;


import org.springframework.data.mongodb.core.mapping.Document;

@Document("customer")
public class Customer extends User {
    public ServicePack servicePack;

    public Customer(String name, String email, String password, String phoneNo, Location location,String accountNo, ServicePack servicePack) {
        super(name, email, password,phoneNo, location);
        this.servicePack = servicePack;

    }


}
