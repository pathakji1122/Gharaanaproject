package com.beginnner.gharaana.Object;

import com.beginnner.gharaana.Entity.Expert;

public class ExpertProfileResponse {
    public Boolean status;
    public Expert expert;
    public String response;

    public ExpertProfileResponse(Boolean status, Expert expert, String response) {
        this.status=status;
        this.expert=expert;
        this.response=response;
    }
}
