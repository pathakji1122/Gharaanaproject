package com.beginnner.gharaana.Service;

public class SignupResponce {
    public String responce;
    public Boolean accountCreated;

    public SignupResponce(String responce, Boolean accountCreated) {
        this.accountCreated=accountCreated;
        this.responce=responce;
    }
}
