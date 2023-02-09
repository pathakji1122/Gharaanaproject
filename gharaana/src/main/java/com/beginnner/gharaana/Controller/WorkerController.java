package com.beginnner.gharaana.Controller;

import com.beginnner.gharaana.Entity.Location;
import com.beginnner.gharaana.Entity.Order;
import com.beginnner.gharaana.Entity.OrderStatus;
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
    public SignupResponce signup(@RequestBody WorkerSignupRequest workerSignupRequest) {
        return userService.registerWorker(workerSignupRequest);
    }

    @PostMapping(path = "acceptorder")
    public AcceptOrderResponce acceptOrder(@RequestBody AcceptOrderRequest acceptOrderRequest) {
        Boolean verify = auth.verifyWorkerToken(acceptOrderRequest.token);
        if (verify) {
            return orderService.acceptOrder(acceptOrderRequest);
        }
        return new AcceptOrderResponce("Invalid Token", null, false);

    }


    @PostMapping(path = "checkorders")
    public CheckOrdersResponce checkOrderResponce(@RequestBody CheckOrdersRequest checkOrdersRequest) {
        Boolean verify = auth.verifyWorkerToken(checkOrdersRequest.token);
        if (verify) {
            return orderService.checkOrders(checkOrdersRequest);
        }
        return new CheckOrdersResponce("Invalid Token", null);
    }

    @PostMapping(path = "startorder")
    public StartOrderResponce startOrderResponce(@RequestBody StartOrderRequest startOrderRequest) {
        Boolean verify = auth.verifyWorkerToken(startOrderRequest.token);
        if (verify) {
            return orderService.startOrder(startOrderRequest);
        }

        return new StartOrderResponce("Invalid Token", false, null);
    }

    @PostMapping(path = "completeorder")
    public CompleteOrderResponce completeOrderResponce(@RequestBody CompleteOrderRequest completeOrderRequest) {
        Boolean verify = auth.verifyWorkerToken(completeOrderRequest.token);
        if (verify) {
            return orderService.completeOrder(completeOrderRequest);
        }
        return new CompleteOrderResponce("Token Invalid", false);

    }
}
