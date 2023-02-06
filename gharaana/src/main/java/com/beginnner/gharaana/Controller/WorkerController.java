package com.beginnner.gharaana.Controller;

import com.beginnner.gharaana.Entity.Location;
import com.beginnner.gharaana.Entity.Order;
import com.beginnner.gharaana.Entity.OrderStatus;
import com.beginnner.gharaana.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.lang.String.valueOf;


@RestController
@RequestMapping("worker")
public class WorkerController {
    @Autowired
    UserService userService;
    @Autowired
    Auth auth;
    @Autowired
    OrderService orderService;

    @PostMapping(path = "signup")
    public SignupResponce signup(@RequestBody WorkerSignupRequest workerSignupRequest) {
        Location locationverify = Location.getLocationFromCode(valueOf(workerSignupRequest.location));
        if (locationverify != null) {
            Boolean signedup = userService.registerWorker(workerSignupRequest);
            if (signedup == true) {
                Boolean accountCreated = true;
                String responce = "Welcome To Gharaana " + workerSignupRequest.name;
                SignupResponce signupResponce = new SignupResponce(responce, accountCreated);
                return signupResponce;
            } else if (signedup == false) {
                Boolean accountCreated = false;
                String responce = "Worker exists";
                SignupResponce signupResponce = new SignupResponce(responce, accountCreated);
                return signupResponce;
            }
        }

        Boolean accountCreated = false;
        String responce = "Gharaana Not In Your Location" + "We Are Present in" + userService.getCustomerLocations();
        SignupResponce signupResponce = new SignupResponce(responce, accountCreated);

        return signupResponce;
    }

    @PostMapping(path = "acceptorder")
    public AcceptOrderResponce acceptOrder(@RequestBody AcceptOrderRequest acceptOrderRequest) {
        Boolean verify = auth.verifyWorkerToken(acceptOrderRequest.token);
        if (verify) {
            Order orderCheck = orderService.getOrderByOrderId(acceptOrderRequest.orderId);
            if (orderCheck.orderStatus.equals(OrderStatus.NOT_ACCEPTED)) {
                Order order = orderService.acceptOrder(acceptOrderRequest);
                orderService.saveOtp(acceptOrderRequest.orderId);
                return new AcceptOrderResponce("Your Order id", order, true);
            } else {
                AcceptOrderResponce acceptOrderResponce = new AcceptOrderResponce("Select Other Order ", null, false);

                return acceptOrderResponce;
            }
        }
        return new AcceptOrderResponce("Access Denied", null, false);

    }


    @PostMapping(path = "checkorder")
    public CheckOrderResponce checkOrderResponce(@RequestBody CheckOrdersRequest checkOrdersRequest) {
        Boolean verify = auth.verifyWorkerToken(checkOrdersRequest.token);
        if (verify) {
            List<Order> orderList = orderService.checkOrders(checkOrdersRequest);
            return new CheckOrderResponce("Your Orders", orderList);
        }
        return new CheckOrderResponce("Access Denied", null);
    }

    @PostMapping(path = "startorder")
    public StartOrderResponce startOrderResponce(@RequestBody StartOrderRequest startOrderRequest) {
        Boolean verify = auth.verifyWorkerToken(startOrderRequest.token);
        if (verify) {
            Order order = orderService.startOrder(startOrderRequest);
            return new StartOrderResponce("Order Started", true, order);
        }

        return new StartOrderResponce("Access Denied", false, null);
    }

    @PostMapping(path = "completeorder")
    public CompleteOrderResponce completeOrderResponce(@RequestBody CompleteOrderRequest completeOrderRequest) {
        Boolean verify = auth.verifyWorkerToken(completeOrderRequest.token);
        if (verify) {
            Boolean otpVerify = orderService.verifyOtp(completeOrderRequest);
            if (otpVerify) {
                orderService.orderComplete(completeOrderRequest);
                return new CompleteOrderResponce("Order Completed", true);
            } else {
                orderService.saveOtp(completeOrderRequest.orderId);
                return new CompleteOrderResponce("Enter New Otp", false);

            }
        }
        return new CompleteOrderResponce("Access Denied", false);

    }
}
