package com.beginnner.gharaana.Entity;

import java.time.LocalDateTime;

public class Times {
    public String orderPlacedAt;
    public String orderAcceptedAt;
    public String startTime;
    public String completeTime;
    public String placedFor;

    public Times(String orderPlacedAt, String placedFor, String startTime, String completeTime, String orderAcceptedAt) {
        this.orderPlacedAt = orderPlacedAt;
        this.placedFor = placedFor;
        this.startTime = startTime;
        this.completeTime = completeTime;
        this.orderAcceptedAt = orderAcceptedAt;
    }
}
