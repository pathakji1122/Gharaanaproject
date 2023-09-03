package com.beginnner.gharaana.Entity;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("worker")
public class Expert extends User {


    public List<Expertise> expertise;
    public Integer orders;
    public Double rating;

    public Expert(String name, String email, String password, String phoneNo, Location location, List<Expertise> expertise, Integer orders, Double rating) {
        super(name, email, password, phoneNo, location);
        this.expertise = expertise;
        this.orders=orders;
        this.rating=rating;
    }
}
