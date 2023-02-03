package com.beginnner.gharaana.Controller;

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
        Boolean WorkerVification = userService.isWorker(loginRequest.email);
        Boolean CustomerVerification = userService.isCustomer(loginRequest.email);
        if (WorkerVification == false && CustomerVerification==false) {
            LoginResponce loginResponce = new LoginResponce(null, false, "Login Failed");
            return loginResponce;
        }
        if (CustomerVerification == true) {
            String email = loginRequest.email;
            String Token = token.generateToken(email);
            Boolean login = userService.loginCustomerVerify(loginRequest);
            if (login) {
                Boolean worker = userService.isWorker(email);
                LoginResponce loginResponce = new LoginResponce(Token, false, "Logged in");
                return loginResponce;
            } else {
                String responce = "Wrong info";
                LoginResponce loginResponce = new LoginResponce(null, false, responce);
                return loginResponce;
            }
        }
            String workerEmail = loginRequest.email;
            String workerToken = token.generateToken(workerEmail);
            Boolean workerLogin = userService.loginWorkerVerify(loginRequest);
            if (workerLogin) {
                LoginResponce workerLoginResponce = new LoginResponce(workerToken, true, "Logged in");
                return workerLoginResponce;
            }
            LoginResponce wrongLoginResponce = new LoginResponce(null, false, "Wrong info");
            return wrongLoginResponce;


        }
    }
