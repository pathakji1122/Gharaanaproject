package com.beginnner.gharaana.Entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.boot.convert.DataSizeUnit;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

public class User {
    public String name;
    @Id
    @NotNull
    @Email
    public String email;
    public String password;
    @Size(min=10,max =10,message = "Enter Correct no Please")
    public String phoneno;
    public Location location;

    public User(String name, String email, String password, String phoneno, Location location) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneno = phoneno;
        this.location = location;
    }


}
