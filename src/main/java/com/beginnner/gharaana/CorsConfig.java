package com.beginnner.gharaana;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Allow CORS for all endpoints
                .allowedOrigins("*")     // Allow requests from any origin
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allowed HTTP methods
                .allowedHeaders("*");    // Allowed headers
    }
}
//
////1.Create a new User
//2.Login
//   3.PlaceOrder
////
//        Diff Worker account and check if it is visible or Not
//        Accept Order and show it as accepted to Others
//// // MongoLock(Synchroncise)Raise Conditon /
//// MultiThreading
//        Order Cancelled without Starting .
//        The order should be visible to other but not Him  .
//        Delete Order after realtime orderPlacedFor
//
////Norder visible after cancellation
////Cancel order before y time
//
//        Otp at both Ends .






