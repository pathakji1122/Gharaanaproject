package com.beginnner.gharaana.Controller;

import com.beginnner.gharaana.Object.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {


    @PostMapping(path = "addbalance")
    public AddBalanceResponse addCoins(@RequestBody AddBalanceRequest addCoinsRequest){
        return null;
    }
    @PostMapping(path = "payment")
    public OrderPaymentResponse payment(@RequestBody OrderPaymentRequest orderPaymentRequest){
        return null;

    }
    @PostMapping(path = "checkbalance")
    public CheckBalanceResponse checkBalance(@RequestBody CheckBalanceRequest checkBalanceRequest){
        return null;
    }
}
