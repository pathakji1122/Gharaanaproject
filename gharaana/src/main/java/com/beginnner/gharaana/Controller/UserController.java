package com.beginnner.gharaana.Controller;

import com.beginnner.gharaana.Entity.User;
import com.beginnner.gharaana.Repo.UserRepository;
import com.beginnner.gharaana.Service.LoginRequest;
import com.beginnner.gharaana.Service.LoginResponce;
import com.beginnner.gharaana.Service.Auth;
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
    Auth token;
    @Autowired
    UserService userService;

    @PostMapping("login")
    public LoginResponce login(@RequestBody LoginRequest loginRequest) {
        Boolean verification = userService.userVerify(loginRequest.email);
        if (verification == false) {
            String responce = "No User With this email";
            LoginResponce loginResponce = new LoginResponce(responce);
            return loginResponce;
        }
        String email = loginRequest.email;
        String Token = token.generateToken(email);
        Boolean login = userService.loginVerify(loginRequest);
        if (login) {
            Boolean worker = userService.isWorker(email);
            LoginResponce loginResponce = new LoginResponce(Token, worker);
            return loginResponce;
        } else {
            String responce = "Wrong info";
            LoginResponce loginResponce = new LoginResponce(responce);
            return loginResponce;
        }

    }

}
