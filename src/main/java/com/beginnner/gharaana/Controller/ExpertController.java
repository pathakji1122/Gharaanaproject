package com.beginnner.gharaana.Controller;

import com.beginnner.gharaana.Object.*;
import com.beginnner.gharaana.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
@RestController
@RequestMapping("expert")
public class ExpertController {
    @Autowired
    UserService userService;

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    OrderService orderService;

    @PostMapping(path = "signup")
    public SignUpResponse signup(@RequestBody ExpertSignupRequest expertSignupRequest) throws  IOException, InterruptedException {
        return userService.registerExpert(expertSignupRequest);
    }

    @PostMapping(path = "acceptorder")
    public AcceptOrderResponse acceptOrder(@RequestHeader("Authorization") String authorizationHeader,@RequestBody AcceptOrderRequest acceptOrderRequest) {

      String token=  authorizationHeader.replace("Bearer ", "");
        Boolean verify = jwtUtil.isTokenValid(token);


        if (verify) {
            return orderService.acceptOrder(acceptOrderRequest,token);
        }
        return new AcceptOrderResponse("Invalid Token", null, false);

    }


    @GetMapping(path = "checkorders")
    public CheckOrdersResponse checkOrderResponce(@RequestHeader("Authorization") String authorizationHeader) {

       String token=authorizationHeader.replace("Bearer ", "");
        Boolean verify = jwtUtil.isTokenValid(token);
        if (verify) {
            return orderService.checkOrders(token);
        }
        return new CheckOrdersResponse("Invalid Token", null);
    }

    @PostMapping(path = "startorder")
    public StartOrderResponse startOrderResponce(@RequestHeader("Authorization") String authorizationHeader,@RequestBody StartOrderRequest startOrderRequest) {
       String token=authorizationHeader.replace("Bearer ", "");
        Boolean verify = jwtUtil.isTokenValid(token);
        if (verify) {
            return orderService.startOrder(startOrderRequest,token);
        }

        return new StartOrderResponse("Invalid Token", false, null);
    }

    @PostMapping(path = "completeorder")
    public CompleteOrderResponse completeOrderResponce(@RequestHeader("Authorization") String authorizationHeader,@RequestBody CompleteOrderRequest completeOrderRequest) {
        String token=authorizationHeader.replace("Bearer ", "");
        Boolean verify =jwtUtil.isTokenValid(token);
        if (verify) {
            return orderService.completeOrder(completeOrderRequest,token);
        }
        return new CompleteOrderResponse("Token Invalid", false);
    }
    @GetMapping(path="myorders")
    public MyOrderResponse myOrder(@RequestHeader("Authorization") String authorizationHeader){
        String token=authorizationHeader.replace("Bearer ", "");
        Boolean verify =jwtUtil.isTokenValid(token);
        if(verify){
            return orderService.myOrderAsExpert(token);
        }
        return new MyOrderResponse(false,"Invalid",null);
    }
   @GetMapping(path = "profile")
    public ExpertProfileResponse expertProfile(@RequestHeader("Authorization") String authorizationHeader){
       String token=authorizationHeader.replace("Bearer ", "");
       Boolean verify =jwtUtil.isTokenValid(token);
       if(verify){
           return userService.expertView(token);
       }
       return new ExpertProfileResponse(false,null,"response");


   }

}
