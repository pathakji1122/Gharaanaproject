package com.beginnner.gharaana.Service;

import com.beginnner.gharaana.Entity.User;
import com.beginnner.gharaana.Repo.CustomerRepository;
import com.beginnner.gharaana.Repo.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class Auth {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    WorkerRepository workerRepository;
    public String SecretKey="Prakash";
    public String token;
    public String generateToken(String email){
        return email+"@@@@@"+"GharanaUser"+SecretKey;
    }
    public boolean verifyCustomerToken(String token){
        String email=token.split("@@@@@GharanaUser")[0];
        String secret=token.split("@@@@@GharanaUser")[1];
        User user=customerRepository.findOneByEmail(email);
        if(user==null) {
            return false;
        }
        if (secret.equals(SecretKey)==false) {
            return false;
        }
        return true;
    }
    public boolean verifyWorkerToken(String token){
        String email=token.split("@@@@@GharanaUser")[0];
        String secret=token.split("@@@@@GharanaUser")[1];
        User user=workerRepository.findOneByEmail(email);
        if(user==null) {
            return false;
        }
        if (secret.equals(SecretKey)==false) {
            return false;
        }
        return true;
    }




}
