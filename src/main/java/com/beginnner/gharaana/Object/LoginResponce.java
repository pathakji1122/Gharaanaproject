package com.beginnner.gharaana.Object;

public class LoginResponce {
    public String token;
    public Boolean worker;
   public String responce;
   public Boolean status;

    public LoginResponce(String token, Boolean worker,String responce,Boolean status) {
        this.token=token;
        this.worker=worker;
        this.responce=responce;
        this.status=status;
    }


}
