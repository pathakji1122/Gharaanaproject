package com.beginnner.gharaana.Controller;

import com.beginnner.gharaana.Object.*;
import com.beginnner.gharaana.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;




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
    public SignUpResponse signup(@RequestBody WorkerSignupRequest workerSignupRequest) throws  IOException, InterruptedException {
        return userService.registerWorker(workerSignupRequest);
    }

    @PostMapping(path = "acceptorder")
    public AcceptOrderResponse acceptOrder(@RequestBody AcceptOrderRequest acceptOrderRequest) {
        Boolean verify = auth.verifyWorkerToken(acceptOrderRequest.token);
        if (verify) {
            return orderService.acceptOrder(acceptOrderRequest);
        }
        return new AcceptOrderResponse("Invalid Token", null, false);

    }


    @PostMapping(path = "checkorders")
    public CheckOrdersResponse checkOrderResponce(@RequestBody CheckOrdersRequest checkOrdersRequest) {
        Boolean verify = auth.verifyWorkerToken(checkOrdersRequest.token);
        if (verify) {
            return orderService.checkOrders(checkOrdersRequest);
        }
        return new CheckOrdersResponse("Invalid Token", null);
    }

    @PostMapping(path = "startorder")
    public StartOrderResponse startOrderResponce(@RequestBody StartOrderRequest startOrderRequest) {
        Boolean verify = auth.verifyWorkerToken(startOrderRequest.token);
        if (verify) {
            return orderService.startOrder(startOrderRequest);
        }

        return new StartOrderResponse("Invalid Token", false, null);
    }

    @PostMapping(path = "completeorder")
    public CompleteOrderResponse completeOrderResponce(@RequestBody CompleteOrderRequest completeOrderRequest) {
        Boolean verify = auth.verifyWorkerToken(completeOrderRequest.token);
        if (verify) {
            return orderService.completeOrder(completeOrderRequest);
        }
        return new CompleteOrderResponse("Token Invalid", false);
    }

}
