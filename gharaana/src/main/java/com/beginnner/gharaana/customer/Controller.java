package com.beginnner.gharaana.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;

@RestController
@RequestMapping("customer")
public class Controller {
    @Autowired
    CustomerService customerService;
    @Autowired
    CustomerRepository customerRepository;
    @PostMapping(path = "signup")
    public String signUp(@RequestBody SignupRequest signupRequest){
        Boolean signedup= customerService.signup(signupRequest);
        Boolean checklocation=customerService.checkLocation(signupRequest);
        if(checklocation==false) {
            return "Gharaana coming soon near you";
        }
        else{
        if(signedup) {
            return "Welcome to Gharaana " +signupRequest.name  + " Service at your doors";
        }
       else{
           return "Customer with this email already exists";
        }
    }
    }


    @PostMapping(path = "login")
    public String login(LoginRequest loginRequest){
        Boolean login=customerService.login(loginRequest);
        if(login) return "Gharaana";
        else{
            return "Email and Password wrong ";
        }
    }



}
