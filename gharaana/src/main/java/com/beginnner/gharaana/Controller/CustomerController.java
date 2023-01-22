package com.beginnner.gharaana.Controller;

import com.beginnner.gharaana.Entity.Customer;
import com.beginnner.gharaana.Repo.CustomerRepository;
import com.beginnner.gharaana.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

@RestController
@RequestMapping("customer")
public class CustomerController {
    @Autowired
    UserService userService;
@PostMapping(path = "signup")
    public String signup(@RequestBody SignupRequest signupRequest) {
        Boolean signedup = userService.registerCustomer(signupRequest);
        if (signedup==true){
            return "Welcome to Gharaana " +signupRequest.name;
        } else {
            return "Customer exists";
        }
    }
}

