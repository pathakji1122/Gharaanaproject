package com.beginnner.gharaana.Object;

public class LoginResponce {
    public String token;
    public Boolean expert;
   public String response;
   public Boolean status;

    public LoginResponce(String token, Boolean expert, String response, Boolean status) {
        this.token=token;
        this.expert=expert;
        this.response = response;
        this.status=status;
    }


}
