package com.beginnner.gharaana.Service;

import com.beginnner.gharaana.Entity.Expertise;
import com.beginnner.gharaana.Entity.Location;
import com.beginnner.gharaana.Entity.ServicePack;
import org.springframework.lang.NonNull;

public class SignupRequest {
    @NonNull
    public String email;
    public String password;
    public String name;
    public String phoneno;
   public Location location;
   public ServicePack servicePack;
   public Expertise expertise;


}
