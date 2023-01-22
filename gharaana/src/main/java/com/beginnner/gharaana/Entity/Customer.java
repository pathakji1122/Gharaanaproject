package com.beginnner.gharaana.Entity;

public class Customer{
    public String servicePack;

    public String name;
    public String email;
    public String password;
    public String location;
    public int phoneno;

    public Customer(String name, String email, int phoneno, String servicePack, String location, String password) {

        this.name = name;
        this.email = email;
        this.password = password;
        this.location = location;
        this.phoneno = phoneno;
        this.servicePack=servicePack;
    }
}
