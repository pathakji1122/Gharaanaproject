package com.beginnner.gharaana.Service;

public class LoginResponce {
    public String token;
    public Boolean worker;
   public String responce;
    //public LoginResponce(String message) {
    //    this.message=message;
    //}

    public LoginResponce(String token, Boolean worker) {
        this.token=token;
        this.worker=worker;
    }

    public LoginResponce(String responce) {
        this.responce=responce;
    }
}
