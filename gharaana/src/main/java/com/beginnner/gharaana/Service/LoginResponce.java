package com.beginnner.gharaana.Service;

public class LoginResponce {
    public String token;
    public Boolean worker;
    public String message;
    public LoginResponce(String message) {
        this.message=message;
    }

    public LoginResponce(String token, Boolean worker) {
        this.token=token;
        this.worker=worker;
    }
}
