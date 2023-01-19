package com.beginnner.gharaana.customer;

public class Customer {
    public Customer(String name, String email, String location, String password) {
        this.name = name;
        this.password=password;
        this.email=email;
        this.location=location;
    }

    public Customer() {

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    private String name;
    private String email;
    private String password;
    private String location;

}
