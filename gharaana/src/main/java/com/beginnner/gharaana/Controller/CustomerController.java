package com.beginnner.gharaana.Controller;

import com.beginnner.gharaana.Entity.*;
import com.beginnner.gharaana.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        Location locationverify = Location.getLocationFromCode(valueOf(customerSignupRequest.location));
        if (locationverify != null) {
            Boolean signedup = userService.registerCustomer(customerSignupRequest);
            if (signedup == true) {
                Boolean accountCreated = true;
                String responce = "Welcome To Gharaana " + customerSignupRequest.name;
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
        String responce = "Gharaana Not At Your Location " + "We Are Preesent in " + userService.getCustomerLocations();
        SignupResponce signupResponce = new SignupResponce(responce, accountCreated);

        return signupResponce;
    }

    @DeleteMapping("/delete")
    public DeleteCustomerResponce deletecustomer(@RequestBody DeleteRequest deleteRequest) {
        String token = deleteRequest.token;
        Boolean verified = auth.verifyCustomerToken(token);
        if (verified) {
            Customer customer = userService.getCustomerByToken(deleteRequest.token);
            userService.deleteCustomer(customer.email);
            DeleteCustomerResponce deleteCustomerResponce = new DeleteCustomerResponce(true, "Successsfully Deleted");
            return deleteCustomerResponce;
        }
        DeleteCustomerResponce deleteCustomerResponce = new DeleteCustomerResponce(false, "No Customer With This Email");
        return deleteCustomerResponce;

    }

    @PostMapping(path = "myorder")
    public MyOrderResponce myOrder(@RequestBody MyOrderReques myOrderReques) {
        String token = myOrderReques.token;
        Boolean verify = auth.verifyCustomerToken(myOrderReques.token);
        if (verify) {
            List<Order> orderList = orderService.myOrder(myOrderReques);
            MyOrderResponce myOrderResponce = new MyOrderResponce(true, "Your Orders Are", orderList);
            return myOrderResponce;
        }
        return new MyOrderResponce(false, "Cant Proceed", null);

    }

    @PostMapping(path = "orderstatus")
    public OrderStatusResponce orderStatus(@RequestBody OrderStatusRequest orderStatusRequest) {
        String token = orderStatusRequest.token;
        Boolean verify = auth.verifyCustomerToken(token);
        if (verify) {
            Order order = orderService.orderStatus(orderStatusRequest);
            if (order != null) {
                OrderStatusResponce orderStatusResponce = new OrderStatusResponce(true, "Your Order Status is", order);
                return orderStatusResponce;
            } else {
                return new OrderStatusResponce(false, "Enter Correct OrderId", null);
            }
        }
        return new OrderStatusResponce(false, "Access Denied", null);
    }

    @PostMapping(path = "placeorder")
    public OrderResponce order(@RequestBody OrderRequest orderRequest) {
        String token = orderRequest.token;
        Boolean verified = auth.verifyCustomerToken(token);
        if (verified) {
            Expertise expertise = Expertise.checkExpertise(valueOf(orderRequest.expertise));
            if (expertise == null) {
                String responce = userService.getCustomerExpertise();
                return new OrderResponce(false, null, responce);
            }
            String orderId = orderService.createOrderId(orderRequest);
            orderService.saveOrder(orderRequest, orderId);
            OrderResponce orderResponce = new OrderResponce(true, orderId, "Order Successful");
            return orderResponce;
        }
        OrderResponce orderResponce = new OrderResponce(false, null, "Access Denied");
        return orderResponce;
    }

    @PostMapping(path = "getotp")
    public GetOtpResponce getOtp(@RequestBody GetOtpRequest getOtpRequest) {
        String token = getOtpRequest.token;
        Boolean verified = auth.verifyCustomerToken(token);
        if (verified) {
            Otp newOtp = orderService.getOtp(getOtpRequest);
            return new GetOtpResponce(newOtp.otp, "Your Otp is", true);
        }
        return new GetOtpResponce(null, "Access Denied", false);

    }

}

