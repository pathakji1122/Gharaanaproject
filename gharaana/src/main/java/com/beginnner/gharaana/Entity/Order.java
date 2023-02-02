package com.beginnner.gharaana.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("order")
public class Order {

    public String email;
    public String name;
    public Location location;
    public Expertise expertise;
    public Integer price;
    public OrderStatus orderStatus;
    public String time;
    @Id
    public String orderId;
    public String GharanaAgent;
    public Order(String email, String name, Location location, Expertise expertise, Integer price, OrderStatus orderStatus, String time, String orderId,String GharanaAgent) {
        this.email =email;
        this.name =name;
        this.location =location;
        this.expertise =expertise;
        this.price=price;
        this.orderStatus=orderStatus;
        this.time=time;
        this.orderId=orderId;
        this.GharanaAgent=GharanaAgent;
    }
}
