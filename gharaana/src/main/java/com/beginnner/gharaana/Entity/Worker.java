package com.beginnner.gharaana.Entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("worker")
public class Worker extends User {


    public Expertise expertise;

    public Worker(String name, String email, String password, String phoneno, Location location, Expertise expertise) {
        super(name, email, password, phoneno, location);
        this.expertise = expertise;
    }
}
