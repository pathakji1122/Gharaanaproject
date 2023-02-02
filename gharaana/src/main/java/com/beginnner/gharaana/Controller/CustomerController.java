package com.beginnner.gharaana.Controller;

import com.beginnner.gharaana.Entity.Customer;
import com.beginnner.gharaana.Entity.Location;
import com.beginnner.gharaana.Entity.Order;
import com.beginnner.gharaana.Repo.CustomerRepository;
import com.beginnner.gharaana.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.Callable;

import static java.lang.String.valueOf;

@RestController
@RequestMapping("customer")
public class CustomerController {
    @Autowired
    OrderService orderService;
    @Autowired
    UserService userService;
    @Autowired
    Auth auth;
    @Autowired
    CustomerRepository customerRepository;
    private SignupRequest signupRequest;

    @PostMapping(path = "signup")
    public SignupResponce signup(@RequestBody SignupRequest signupRequest) {
        Location locationverify = Location.getLocationFromCode(valueOf(signupRequest.location));
        if (locationverify != null) {
            Boolean signedup = userService.registerCustomer(signupRequest);
            if (signedup == true) {
                Boolean accountCreated = true;
                String responce = "Welcome To Gharaana " + signupRequest.name;
                SignupResponce signupResponce = new SignupResponce(responce, accountCreated);
                return signupResponce;
            } else if (signedup == false) {
                Boolean accountCreated = false;
                String responce = "Customer exists";
                SignupResponce signupResponce = new SignupResponce(responce, accountCreated);
                return signupResponce;
            }
        }
        Boolean accountCreated = false;
        String responce = "Gharaana Not In Your Location" + userService.getCustomerLocations();
        SignupResponce signupResponce = new SignupResponce(responce, accountCreated);

        return signupResponce;
    }

    @DeleteMapping("/delete")
    public String deletecustomer(@RequestBody DeleteRequest deleteRequest) {
        String token = deleteRequest.token;
        Boolean verified = auth.verifyToken(token);
        if (verified) {
            Customer customer = userService.getCustomerByToken(deleteRequest.token);
            if (customer != null) {
                userService.deleteCustomer(deleteRequest.email);

            } else {
                return "Cant proceed";
            }
        }
        return "Try Again";

    }

    @PostMapping(path = "myorder")
    public MyOrderResponce myOrder(@RequestBody MyOrderReques myOrderReques) {
        String token = myOrderReques.token;
        Boolean verify = auth.verifyToken(myOrderReques.token);
        if (verify) {
            List<Order> orderList = orderService.myOrder(myOrderReques);
            MyOrderResponce myOrderResponce = new MyOrderResponce(orderList);
            return myOrderResponce;
        }
        return null;

    }

    @PostMapping(path = "orderstatus")
    public OrderStatusResponce orderStatus(@RequestBody OrderStatusRequest orderStatusRequest) {
        String token = orderStatusRequest.token;
        Boolean verify = auth.verifyToken(token);
        if (verify) {
            Order order = orderService.orderStatus(orderStatusRequest);

            OrderStatusResponce orderStatusResponce = new OrderStatusResponce(order);
            return orderStatusResponce;
        }
        return null;
    }

    @PostMapping(path = "placeorder")
    public OrderResponce order(@RequestBody OrderRequest orderRequest) {
        String token = orderRequest.token;
        Boolean verified = auth.verifyToken(token);
        if (verified) {
            String orderId = orderService.createOrderId(orderRequest);
            orderService.saveOrder(orderRequest, orderId);
            String responce = "Gharaana Loading";
            OrderResponce orderResponce = new OrderResponce(orderId, responce);
            return orderResponce;
        }
        OrderResponce orderResponce = new OrderResponce("null", "Order again");
        return orderResponce;
    }


}

