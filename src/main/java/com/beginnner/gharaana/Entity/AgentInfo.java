package com.beginnner.gharaana.Entity;

import org.springframework.data.annotation.Id;

public class AgentInfo {
    public String name;
    @Id
    public String email;
    public Expertise expertise;
    public int totalOrders;
    public int rating;


    public AgentInfo(String name, String email, Expertise expertise, int totalOrders, Integer rating) {
        this.name = name;
        this.email = email;
        this.expertise = expertise;
        this.totalOrders = totalOrders;
        this.rating = rating;
    }
}
