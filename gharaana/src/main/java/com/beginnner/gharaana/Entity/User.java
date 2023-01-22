package com.beginnner.gharaana.Entity;

public class User {
    public User() {
        this.name = name;
        this.email = email;
        this.password = password;
        this.location = location;
        this.phoneno = phoneno;
    }

    public String name;
    public String email;
    public String password;
    public String location;
    public int phoneno;

    enum userType{
        Worker,
        Customer;
    }

}
