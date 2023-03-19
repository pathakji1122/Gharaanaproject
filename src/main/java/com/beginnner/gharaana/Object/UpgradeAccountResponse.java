package com.beginnner.gharaana.Object;

import com.beginnner.gharaana.Entity.ServicePack;

public class UpgradeAccountResponse {
    public String response;
    public Boolean status;
    public ServicePack servicePack;

    public UpgradeAccountResponse(String response, boolean status,ServicePack servicePack) {
        this.response=response;
        this.status=status;
        this.servicePack=servicePack;
    }
}
