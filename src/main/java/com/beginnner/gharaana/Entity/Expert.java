package com.beginnner.gharaana.Entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("worker")
public class Expert extends User {


    public Expertise expertise;

    public Expert(String name, String email, String password, String phoneNo, Location location, Expertise expertise) {
        super(name, email, password, phoneNo, location);
        this.expertise = expertise;
    }
}
