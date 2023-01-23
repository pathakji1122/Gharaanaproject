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
    Auth auth;
    @Autowired
    CustomerRepository customerRepository;
@PostMapping(path = "signup")
    public String signup(@RequestBody SignupRequest signupRequest) {
        Boolean signedup = userService.registerCustomer(signupRequest);
        Boolean locate=userService.currentLocation(signupRequest);
    //    if(locate==false){
      //      return "Gharaana soon at your Location";
       // }
        if (signedup==true){
            return "Welcome to Gharaana " +signupRequest.name;
        } else {
            return "Customer exists";
        }
    }
 @PostMapping(path = "order")
 public OrderResponce order(@RequestBody OrderRequest orderRequest) {
     String token = orderRequest.token;
     Boolean verified = auth.verifyToken(token);
     if (verified) {
         String orderId= auth.orderIdByToken(token)+orderRequest.OrderType;
         String responce="Gharanaa loading";
          OrderResponce orderResponce=new OrderResponce(orderId,responce);
          return orderResponce;
     }
     OrderResponce orderResponce=new OrderResponce("null","Order again");
     return orderResponce;

 }


}

