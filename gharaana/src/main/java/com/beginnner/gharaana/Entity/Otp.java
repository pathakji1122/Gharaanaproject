package com.beginnner.gharaana.Entity;

import java.time.LocalTime;

public class Otp {
    public long otp;
    public String orderId;
    public LocalTime generatedTime;

    public Otp(String orderId, long otp, LocalTime generatedTime) {
        this.orderId = this.orderId;
        this.otp=otp;
        this.generatedTime=generatedTime;

    }
}
