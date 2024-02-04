package com.beginnner.gharaana.Object;

import com.beginnner.gharaana.Entity.*;
import org.springframework.lang.NonNull;

public class CustomerSignUpRequest {
    @NonNull
    public String email;
    public String password;
    public String customerName;
    public String phoneNo;
    public Location location;


}
