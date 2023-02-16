package com.beginnner.gharaana.Service;

import com.beginnner.gharaana.Entity.Location;
import com.beginnner.gharaana.Entity.ServicePack;
import org.springframework.lang.NonNull;

public class CustomerSignupRequest {
    @NonNull
    public String email;
    public String password;
    public String name;
    public String phoneNo;
   public Location location;
   public ServicePack servicePack;



}
