package com.beginnner.gharaana.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class CorsController {

    @RequestMapping(method = RequestMethod.OPTIONS, path = "/**")
    public ResponseEntity<?> handleOptions() {
        return ResponseEntity.ok()
                .header("Access-Control-Allow-Origin", "https://web1-1.onrender.com") // Replace with your frontend's origin
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE")
                .header("Access-Control-Allow-Headers", "Authorization, Content-Type")
                .header("Access-Control-Allow-Credentials", "true")
                .build();
    }
}
