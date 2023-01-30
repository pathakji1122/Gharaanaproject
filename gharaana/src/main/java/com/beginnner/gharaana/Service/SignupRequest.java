package com.beginnner.gharaana.Service;

import com.beginnner.gharaana.Entity.Expertise;
import com.beginnner.gharaana.Entity.Location;
import com.beginnner.gharaana.Entity.ServicePack;

public class SignupRequest {
    public String email;
    public String password;
    public String name;
    public int phoneno;
    Location location;
    ServicePack servicePack;
    Expertise expertise;


}
