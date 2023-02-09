package com.beginnner.gharaana.Controller;

import com.beginnner.gharaana.Entity.*;
import com.beginnner.gharaana.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

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

    @PostMapping(path = "signup")
    public SignupResponce signup(@RequestBody CustomerSignupRequest customerSignupRequest) {
        return userService.registerCustomer(customerSignupRequest);
    }

    @DeleteMapping("delete")
    public DeleteCustomerResponce deletecustomer(@RequestBody DeleteRequest deleteRequest) {
        String token = deleteRequest.token;
        Boolean verified = auth.verifyCustomerToken(token);
        if (verified) {
            return userService.deleteCustomer(deleteRequest.email);
        }
        DeleteCustomerResponce deleteCustomerResponce = new DeleteCustomerResponce(false, "Invalid Token");
        return deleteCustomerResponce;

    }

    @PostMapping(path = "myorder")
    public MyOrderResponce myOrder(@RequestBody MyOrderRequest myOrderRequest) {
        String token = myOrderRequest.token;
        Boolean verify = auth.verifyCustomerToken(myOrderRequest.token);
        if (verify) {
            return orderService.myOrder(myOrderRequest);
        }
        return new MyOrderResponce(false, "Invalid Token", null);
    }

    @PostMapping(path = "orderstatus")
    public OrderStatusResponce orderStatus(@RequestBody OrderStatusRequest orderStatusRequest) {
        String token = orderStatusRequest.token;
        Boolean verify = auth.verifyCustomerToken(token);
        if (verify) {
            return orderService.orderStatus(orderStatusRequest);
        }
        return new OrderStatusResponce(false, "Invalid Token", null);
    }

    @PostMapping(path = "placeorder")
    public OrderResponce order(@RequestBody OrderRequest orderRequest) throws ParseException {

        String token = orderRequest.token;
        Boolean verified = auth.verifyCustomerToken(token);
        if (verified) {
            return orderService.placeOrder(orderRequest);
        }
        OrderResponce orderResponce = new OrderResponce(false, null, "Invalid Token");
        return orderResponce;

    }

    @PostMapping(path = "getotp")
    public GetOtpResponce getOtp(@RequestBody GetOtpRequest getOtpRequest) {
        String token = getOtpRequest.token;
        Boolean verified = auth.verifyCustomerToken(token);
        if (verified) {
            return orderService.getOtp(getOtpRequest);
        }
        return new GetOtpResponce(null, "Invalid Token", false);

    }

}

