package com.beginnner.gharaana.Entity;
public class User {
    public String name;
    public String email;
    public String password;
    public int phoneno;
    Location location;

    public User(String name, String email, String password, int phoneno,Location location) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneno = phoneno;
        this.location=location;
    }




}
