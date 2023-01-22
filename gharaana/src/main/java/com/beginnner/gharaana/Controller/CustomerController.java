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
    @Autowired
    Token token;
    @Autowired
    CustomerRepository customerRepository;
@PostMapping(path = "login")
    public LoginResponce login(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.email;
        String Token = token.generateToken(email);
        Boolean login = userService.loginVerify(loginRequest);
        if (login) {
            LoginResponce loginResponce = new LoginResponce(Token);
            return loginResponce;
        } else {
            String responce = "Wrong info";
            LoginResponce loginResponce = new LoginResponce(responce);
            return loginResponce;
        }
    }
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

