package com.beginnner.gharaana.Controller;

import com.beginnner.gharaana.Object.*;
import com.beginnner.gharaana.Service.*;
import com.beginnner.gharaana.Validation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
@CrossOrigin(
        origins = {
                "http://localhost:3000",
                "https://web1-1.onrender.com/"
        },
        methods = {
                RequestMethod.OPTIONS,
                RequestMethod.GET,
                RequestMethod.PUT,
                RequestMethod.DELETE,
                RequestMethod.POST
        })

@RestController
@RequestMapping("customer")
public class CustomerController {
    @Autowired
    OrderService orderService;
    @Autowired
    UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping(path = "signup")
    public SignUpResponse signUp(@RequestBody CustomerSignUpRequest customerSignUpRequest) throws IOException, InterruptedException {
        return userService.registerCustomer(customerSignUpRequest);
    }

    @DeleteMapping("delete")
    public DeleteCustomerResponse deleteCustomer( @RequestHeader("Authorization") String authorizationHeader,@RequestBody DeleteCustomerRequest deleteCustomerRequest) {
        String token = authorizationHeader.replace("Bearer ", "");
        Boolean verified = jwtUtil.isTokenValid(token);
        if (verified) {
            return userService.deleteCustomer(deleteCustomerRequest.email);
        }
        return new DeleteCustomerResponse(false, "Invalid Token");
    }

    @PostMapping(path = "myorder")
    public MyOrderResponse myOrder( @RequestHeader("Authorization") String authorizationHeader,@RequestBody MyOrderRequest myOrderRequest) {
        String token = authorizationHeader.replace("Bearer ", "");
        Boolean verify = jwtUtil.isTokenValid(token);
        if (verify) {
            return orderService.myOrder(myOrderRequest,token);
        }
        return new MyOrderResponse(false, "Invalid Token", null);
    }

    @PostMapping(path = "orderstatus")
    public OrderStatusResponse orderStatus(@RequestHeader("Authorization") String authorizationHeader,@RequestBody OrderStatusRequest orderStatusRequest) {
        String token = authorizationHeader.replace("Bearer ", "");
        Boolean verify = jwtUtil.isTokenValid(token);
        if (verify) {
            return orderService.orderStatus(orderStatusRequest,token);
        }
        return new OrderStatusResponse(false, "Invalid Token", null);
    }

    @PostMapping(path = "placeorder")
    public OrderResponse order(@RequestHeader("Authorization") String authorizationHeader,@RequestBody OrderRequest orderRequest) throws ParseException {
        String validationError = OrderRequestValidator.validate(orderRequest);
        if (validationError != null) {
            return new OrderResponse(false, null, validationError);
        }
        String token = authorizationHeader.replace("Bearer ", "");
        Boolean verified = jwtUtil.isTokenValid(token);
        if (verified) {
            return orderService.placeOrder(orderRequest,token);
        }
        OrderResponse orderResponse = new OrderResponse(false, null, "Invalid Token");
        return orderResponse;
    }


    @PostMapping(path = "getotp")
    public GetOtpResponse getOtp(@RequestHeader("Authorization") String authorizationHeader,@RequestBody GetOtpRequest getOtpRequest) {
        String token = authorizationHeader.replace("Bearer ", "");
        Boolean verified = jwtUtil.isTokenValid(token);
        if (verified) {
            return orderService.getOtp(getOtpRequest,token);
        }
        return new GetOtpResponse(null, "Invalid Token", false);

    }

    @PostMapping(path = "cancelorder")
    public CancelOrderResponse cancelOrder(@RequestHeader("Authorization") String authorizationHeader,@RequestBody CancelOrderRequest cancelOrderRequest) {
        String token = authorizationHeader.replace("Bearer ", "");
        Boolean verified = jwtUtil.isTokenValid(token);
        if (verified) {
            return orderService.cancelOrder(cancelOrderRequest,token);
        }
        return new CancelOrderResponse("Invalid Token", false, null);
    }

    @GetMapping(path = "upgradeaccount")
    public UpgradeAccountResponse upgradeAccount(@RequestHeader("Authorization") String authorizationHeader, @RequestBody UpgradeAccountRequest upgradeAccountRequest) throws IOException, InterruptedException {

        String token = authorizationHeader.replace("Bearer ", "");
        Boolean verified=jwtUtil.isTokenValid(token);
        if (verified) {
            return userService.upgradeAccount(upgradeAccountRequest,token);
        }
        return new UpgradeAccountResponse("Invalid Token", false, null);

    }



}

