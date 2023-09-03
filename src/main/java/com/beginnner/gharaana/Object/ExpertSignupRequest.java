package com.beginnner.gharaana.Object;

import com.beginnner.gharaana.Entity.Expert;
import com.beginnner.gharaana.Entity.Expertise;
import com.beginnner.gharaana.Entity.Location;

import java.util.List;

public class ExpertSignupRequest {
    public String email;
    public String password;
    public String expertName;
    public String phoneNo;
    public Location location;
    public List<Expertise> expertise;
}
