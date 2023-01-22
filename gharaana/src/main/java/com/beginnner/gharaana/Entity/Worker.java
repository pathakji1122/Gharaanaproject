package com.beginnner.gharaana.Entity;

import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators;

public class Worker extends User{

public String price;
Expertise expertise;
    public Worker(String name, String email, String password, int phoneno, Location location, String price,Expertise expertise) {
        super(name, email, password,phoneno,location);
        this.price=price;
        this.expertise=expertise;
    }
}
