package com.beginnner.gharaana.Service;

import com.beginnner.gharaana.Entity.Customer;
import com.beginnner.gharaana.Entity.User;
import com.beginnner.gharaana.Repo.CustomerRepository;
import com.beginnner.gharaana.Repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Locale;

@org.springframework.stereotype.Service
public class Auth {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    UserRepository userRepository;
    public String SecretKey="Prakash";
    public String token;
    public String generateToken(String email){
        return email+"@@@@@"+"GharanaUser"+SecretKey;
    }
    public boolean verifyToken(String token){
        String email=token.split("@@@@@GharanaUser")[0];
        String secret=token.split("@@@@@GharanaUser")[1];
        User user=userRepository.findOneByEmail(email);
        if(user==null) {
            return false;
        }
        if (secret.equals(SecretKey)==false) {
            return false;
        }
        return true;
    }




}
