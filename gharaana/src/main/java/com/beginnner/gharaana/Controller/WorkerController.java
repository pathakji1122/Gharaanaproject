package com.beginnner.gharaana.Controller;

import com.beginnner.gharaana.Entity.Location;
import com.beginnner.gharaana.Entity.Order;
import com.beginnner.gharaana.Entity.Worker;
import com.beginnner.gharaana.Repo.OrderRepository;
import com.beginnner.gharaana.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
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
    public SignupResponce signup(@RequestBody SignupRequest signupRequest) {
        Location locationverify = Location.getLocationFromCode(valueOf(signupRequest.location));
        if (locationverify != null) {
            Boolean signedup = userService.registerWorker(signupRequest);
            if (signedup == true) {
                Boolean accountCreated = true;
                String responce = "Welcome To Gharaana " + signupRequest.name;
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
        String responce = "Gharaana Not In Your Location"+"We Are Present in" + userService.getCustomerLocations();
        SignupResponce signupResponce = new SignupResponce(responce, accountCreated);

        return signupResponce;
    }

    @PostMapping(path = "acceptorder")
    public AcceptOrderResponce acceptOrder(@RequestBody AcceptOrderRequest acceptOrderRequest) {
        Boolean verify = auth.verifyToken(acceptOrderRequest.token);
        if (verify) {
            Order order = orderService.acceptOrder(acceptOrderRequest);
            AcceptOrderResponce acceptOrderResponce = new AcceptOrderResponce(order);
            return acceptOrderResponce;
        }
        return null;

    }
    @PostMapping(path = "checkorder")
    public CheckOrderResponce checkOrderResponce(@RequestBody CheckOrdersRequest checkOrdersRequest){
        Boolean verify = auth.verifyToken(checkOrdersRequest.token);
        if (verify){
            List<Order>orderList=orderService.checkOrders(checkOrdersRequest);
            return new CheckOrderResponce("Your Orders",orderList);

        }
        return new CheckOrderResponce("Access Denied",null);
    }
}
