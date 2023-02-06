package com.beginnner.gharaana.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

@Document("order")
public class Order {

    public String email;
    public String name;
    public Location location;
    public Expertise expertise;
    public Integer price;
    public OrderStatus orderStatus;
    @Id
    public String orderId;
    public String gharanaAgent;
    public Times times;

    public Order(String email, String name, Location location, Expertise expertise, Integer price, OrderStatus orderStatus, String orderId, String gharanaAgent,Times times) {
        this.email =email;
        this.name =name;
        this.location =location;
        this.expertise =expertise;
        this.price=price;
        this.orderStatus=orderStatus;
        this.orderId=orderId;
        this.gharanaAgent=gharanaAgent;
        this.times=times;
    }


}
