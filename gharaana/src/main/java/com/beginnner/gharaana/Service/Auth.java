package com.beginnner.gharaana.Service;

import com.beginnner.gharaana.Entity.Customer;
import com.beginnner.gharaana.Repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Locale;

@org.springframework.stereotype.Service
public class Auth {
    @Autowired
    CustomerRepository customerRepository;
    public String SecretKey="Prakash";
    public String token;
    public String generateToken(String email){
        return email+"@@@@@"+"GharanaUser"+"^^^^^^^^^^^^^"+SecretKey;
    }
    public boolean verifyToken(String token){
        String email=token.split("@@@@@GharanaUser^^^^^^^^^^^^^")[0];
        String secret=token.split("@@@@@GharanaUser^^^^^^^^^^^^^")[1];
        Customer customer=customerRepository.findOneByEmail(email);
        if (!secret.equals(SecretKey)) {
            return false;
        }
        if (customer==null) {
            return false;
        }


        return true;



    }



}
