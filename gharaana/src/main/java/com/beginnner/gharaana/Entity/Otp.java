package com.beginnner.gharaana.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalTime;
@Document("otp")
public class Otp {

    public long otp;
    @Id
    public String orderId;


    public Otp(String orderId, long otp) {
        this.otp=otp;
        this.orderId=orderId;

    }
}
