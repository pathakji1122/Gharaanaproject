package com.beginnner.gharaana.Service;

import com.beginnner.gharaana.Entity.Customer;
import com.beginnner.gharaana.Entity.User;
import com.beginnner.gharaana.Entity.Worker;
import com.beginnner.gharaana.Repo.CustomerRepository;
import com.beginnner.gharaana.Repo.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.Callable;

@org.springframework.stereotype.Service
public class Auth {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    WorkerRepository workerRepository;
    public String SecretKey="Prakash";
    public String token;
    public String generateToken(String email) {
        Customer customer = customerRepository.findOneByEmail(email);
        String emailSplit = email.split("@")[0];
        Worker worker = workerRepository.findOneByEmail(email);
        if (customer == null) {
            String tokenCode7 = worker.phoneNo.substring(0, 6). replace('7', 'S');
            String tokenCode8= tokenCode7. replace('8', 'A');
            String tokenCode9 = tokenCode8. replace('9', 'N');
            String tokenCode4 = tokenCode9. replace('4', 'C');
            String tokenCode1 = tokenCode4. replace('1', 'O');
            String tokenCode5 = tokenCode1. replace('5', 'P');
            String tokenCode = tokenCode5. replace('0', 'E');
            return emailSplit + "##" + worker.phoneNo.substring(6, 10) + "##" + tokenCode + "##" + SecretKey;
        }
            String tokenCode7 = customer.phoneNo.substring(0, 6). replace('7', 'S');
            String tokenCode8= tokenCode7. replace('8', 'A');
        String tokenCode9 = tokenCode8. replace('9', 'N');
        String tokenCode4 = tokenCode9. replace('4', 'C');
        String tokenCode1 = tokenCode4. replace('1', 'O');
        String tokenCode5 = tokenCode1. replace('5', 'P');
        String tokenCode = tokenCode5. replace('0', 'E');

            return emailSplit + "##" + customer.phoneNo.substring(6, 10) + "##" + tokenCode + "##" + SecretKey;
        }

        public boolean verifyCustomerToken (String token){
            String[] splitToken = token.split("##", 5);
            String email = splitToken[0] + "@gmail.com";
            String phoneCode = splitToken[1];
            String tokenCode7 = splitToken[2]. replace('S', '7');
            String tokenCode8= tokenCode7. replace('A', '8');
            String tokenCode9 = tokenCode8. replace('N', '9');
            String tokenCode4 = tokenCode9. replace('C', '4');
            String tokenCode1 = tokenCode4. replace('O', '1');
            String tokenCode5 = tokenCode1. replace('P', '5');
            String tokenCode = tokenCode5. replace('E', '0');
            String phoneNo = tokenCode + phoneCode;
            String secret = splitToken[3];
            Customer user = customerRepository.findOneByPhoneNoAndEmail(phoneNo, email);
            if (user == null) {
                return false;
            }
            if (secret.equals(SecretKey) == false) {
                return false;
            }
            return true;
    }
    public boolean verifyWorkerToken(String token){
        String[] splitToken = token.split("##", 4);
        String email = splitToken[0] + "@gmail.com";
        String phoneCode = splitToken[1];
        String tokenCode7 = splitToken[2]. replace('S', '7');
        String tokenCode8= tokenCode7. replace('A', '8');
        String tokenCode9 = tokenCode8. replace('N', '9');
        String tokenCode4 = tokenCode9. replace('C', '4');
        String tokenCode1 = tokenCode4. replace('O', '1');
        String tokenCode5 = tokenCode1. replace('P', '5');
        String tokenCode = tokenCode5. replace('E', '0');
        String phoneNo = tokenCode + phoneCode;
        String secret = splitToken[3];
        Worker user=workerRepository.findOneByPhoneNoAndEmail(phoneNo,email);
        if(user==null) {
            return false;
        }
        if (secret.equals(SecretKey)==false) {
            return false;
        }
        return true;
    }




}
