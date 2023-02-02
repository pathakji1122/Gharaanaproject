package com.beginnner.gharaana.Entity;


import org.springframework.data.mongodb.core.mapping.Document;

@Document("customer")
public class Customer extends User {
    public ServicePack servicePack;

    public Customer(String name, String email, String password, String phoneno, Location location, ServicePack servicePack) {
        super(name, email, password, phoneno, location);
        this.servicePack = servicePack;

    }


}
