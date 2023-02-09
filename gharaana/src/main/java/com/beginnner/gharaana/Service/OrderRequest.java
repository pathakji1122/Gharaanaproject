package com.beginnner.gharaana.Service;

import com.beginnner.gharaana.Entity.Expertise;
import org.springframework.validation.annotation.Validated;

public class OrderRequest {
    public String token;
    public Expertise expertise;
    public Integer price;
    public String placedFor;


}
