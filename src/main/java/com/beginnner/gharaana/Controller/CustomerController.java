package com.beginnner.gharaana.Controller;

import com.beginnner.gharaana.Object.*;
import com.beginnner.gharaana.Service.*;
import com.beginnner.gharaana.Validation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;


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
    public SignUpResponse signUp(@RequestBody CustomerSignUpRequest customerSignUpRequest) throws IOException, InterruptedException {
        return userService.registerCustomer(customerSignUpRequest);
    }

    @DeleteMapping("delete")
    public DeleteCustomerResponse deleteCustomer(@RequestBody DeleteCustomerRequest deleteCustomerRequest) {
        String token = deleteCustomerRequest.token;
        Boolean verified = auth.verifyCustomerToken(token);
        if (verified) {
            return userService.deleteCustomer(deleteCustomerRequest.email);
        }
        return new DeleteCustomerResponse(false, "Invalid Token");
    }

    @PostMapping(path = "myorder")
    public MyOrderResponse myOrder(@RequestBody MyOrderRequest myOrderRequest) {
        String token = myOrderRequest.token;
        Boolean verify = auth.verifyCustomerToken(myOrderRequest.token);
        if (verify) {
            return orderService.myOrder(myOrderRequest);
        }
        return new MyOrderResponse(false, "Invalid Token", null);
    }

    @PostMapping(path = "orderstatus")
    public OrderStatusResponse orderStatus(@RequestBody OrderStatusRequest orderStatusRequest) {
        String token = orderStatusRequest.token;
        Boolean verify = auth.verifyCustomerToken(token);
        if (verify) {
            return orderService.orderStatus(orderStatusRequest);
        }
        return new OrderStatusResponse(false, "Invalid Token", null);
    }

    @PostMapping(path = "placeorder")
    public OrderResponse order(@RequestBody OrderRequest orderRequest) throws ParseException {
        String validationError = OrderRequestValidator.validate(orderRequest);
        if (validationError != null) {
            return new OrderResponse(false, null, validationError);
        }
        String token = orderRequest.token;
        Boolean verified = auth.verifyCustomerToken(token);
        if (verified) {
            return orderService.placeOrder(orderRequest);
        }
        OrderResponse orderResponse = new OrderResponse(false, null, "Invalid Token");
        return orderResponse;
    }


    @PostMapping(path = "getotp")
    public GetOtpResponse getOtp(@RequestBody GetOtpRequest getOtpRequest) {
        String token = getOtpRequest.token;
        Boolean verified = auth.verifyCustomerToken(token);
        if (verified) {
            return orderService.getOtp(getOtpRequest);
        }
        return new GetOtpResponse(null, "Invalid Token", false);

    }

    @PostMapping(path = "cancelorder")
    public CancelOrderResponse cancelOrder(@RequestBody CancelOrderRequest cancelOrderRequest) {
        String token = cancelOrderRequest.token;
        Boolean verified = auth.verifyCustomerToken(token);
        if (verified) {
            return orderService.cancelOrder(cancelOrderRequest);
        }
        return new CancelOrderResponse("Invalid Token", false, null);
    }

    @PostMapping(path = "upgradeaccount")
    public UpgradeAccountResponse upgradeAccount(@RequestBody UpgradeAccountRequest upgradeAccountRequest) throws IOException, InterruptedException {
        Boolean verified = auth.verifyCustomerToken(upgradeAccountRequest.token);
        if (verified) {
            return userService.upgradeAccount(upgradeAccountRequest);
        }
        return new UpgradeAccountResponse("Invalid Token", false, null);

    }



}

