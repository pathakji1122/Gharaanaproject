package com.beginnner.gharaana.Entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;

public class User {
    public String name;
    @Id
    @NotNull
    @Email(message = "Enter Correct Email Id")
    public String email;
    public String password;
    @Pattern(regexp ="^\\d{10}S",message = "Enter Valid Phone Number")
    public String phoneNo;
    public Location location;

    public User(String name, String email, String password, String phoneNo, Location location) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNo = phoneNo;
        this.location = location;
    }


}
