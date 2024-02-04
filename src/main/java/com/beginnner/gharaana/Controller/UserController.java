package com.beginnner.gharaana.Controller;

import com.beginnner.gharaana.Object.LoginRequest;
import com.beginnner.gharaana.Object.LoginResponce;
import com.beginnner.gharaana.Service.JwtUtil;
import com.beginnner.gharaana.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController

@CrossOrigin(origins = "http://localhost:3000/")
@RequestMapping(path = "user")
public class UserController {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    UserService userService;

    @PostMapping("login")
    public LoginResponce login(@RequestBody LoginRequest loginRequest) {
        Boolean expertVerification = userService.isExpert(loginRequest.email);
        if (expertVerification) {
            return userService.loginExpertVerify(loginRequest);
        }
        return userService.loginCustomerVerify(loginRequest);
    }

}
