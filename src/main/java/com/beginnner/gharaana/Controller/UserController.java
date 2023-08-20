package com.beginnner.gharaana.Controller;

import com.beginnner.gharaana.Object.LoginRequest;
import com.beginnner.gharaana.Object.LoginResponce;
import com.beginnner.gharaana.Service.JwtUtil;
import com.beginnner.gharaana.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "user")
public class UserController {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    UserService userService;

    @PostMapping("login")
    public LoginResponce login(@RequestBody LoginRequest loginRequest) {
        Boolean workerVerification = userService.isExpert(loginRequest.email);
        if (workerVerification) {
            return userService.loginExpertVerify(loginRequest);
        }
        return userService.loginCustomerVerify(loginRequest);
    }
}
