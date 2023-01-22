package com.beginnner.gharaana.Service;
@org.springframework.stereotype.Service
public class Token {
    public String SecretKey="Prakash";
    public String token;
    public String generateToken(String email){
        return email+"@@@@@"+"GharanaUser"+"^^^^^^^^^^^^^"+SecretKey;
    }
    public void verifyToken(String token){

    }



}
