package com.beginnner.gharaana.Entity;

import org.springframework.data.annotation.Id;

public class User {
    public String name;
    @Id
    public String email;
    public String password;
    public String phoneNo;
    public Location location;



    public User(String name, String email, String password, String phoneNo, Location location) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNo = phoneNo;
        this.location = location;


    }


}
