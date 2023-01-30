package com.beginnner.gharaana.Entity;

public class Customer extends User{
    ServicePack servicePack;

    public Customer(String name, String email, String password,int phoneno,Location location,ServicePack servicePack) {
        super(name,email,password,phoneno,location);
        this.servicePack=servicePack;

    }



}
