package com.beginnner.gharaana.Controller;

import com.beginnner.gharaana.Entity.Order;
import com.beginnner.gharaana.Object.PaymentIdResponse;
import com.beginnner.gharaana.Service.PaymentService;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beginnner.gharaana.Object.*;
import com.beginnner.gharaana.Service.*;
import com.beginnner.gharaana.Validation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.text.ParseException;
@RestController
@RequestMapping("payments")
public class PaymentController {
    @Autowired
    PaymentService paymentService;
    @Autowired
    private JwtUtil jwtUtil;
    @PostMapping(path = "createpayment")
    public String createPayment(@RequestHeader("Authorization") String authorizationHeader,@RequestBody CreatePaymentRequest createPaymentRequest) throws RazorpayException {
        String token = authorizationHeader.replace("Bearer ", "");
        Boolean verified = jwtUtil.isTokenValid(token);
        if (verified) {
           return paymentService.createPayment(createPaymentRequest);
        }
        return null;
    }

}
