package com.beginnner.gharaana.Service;

import com.beginnner.gharaana.Entity.*;
import com.beginnner.gharaana.Object.*;

import com.beginnner.gharaana.Repo.*;
import com.beginnner.gharaana.Validation.*;


import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

import static java.lang.String.join;
import static java.lang.String.valueOf;

@org.springframework.stereotype.Service
public class UserService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
   ExpertRepository expertRepository;



    public LoginResponce loginCustomerVerify(LoginRequest loginRequest) {
        String password = loginRequest.password;
        String email = loginRequest.email;
        Customer customer = customerRepository.findOneByEmail(email);
        if (customer == null) {
            return new LoginResponce(null, false, "User Doesn't Exists",false);
        }
        if (customer.password.equals(password)) {
            String token = jwtUtil.generateToken(email);
            return new LoginResponce(token, false, "Login Successful",true);
        } else {
            return new LoginResponce(null, false, "Wrong Password",false);
        }
    }

    public LoginResponce loginExpertVerify(LoginRequest loginRequest) {
        String password = loginRequest.password;
        String email = loginRequest.email;
       Expert expert = expertRepository.findOneByEmail(email);
        if (expert == null) {
            return new LoginResponce(null, false, "User Doesn't Exists",false);
        }
        if (expert.password.equals(password)) {
            String token = jwtUtil.generateToken(email);
            return new LoginResponce(token, true, "Login Successful",true);
        } else {
            return new LoginResponce(null, false, "Wrong Password",false);
        }
    }

    public SignUpResponse registerCustomer(CustomerSignUpRequest customerSignupRequest) throws IOException, InterruptedException {
        String validCustomerData = SignupRequestValidator.validateCustomerRequest(customerSignupRequest);
        if (validCustomerData != null) {
            return new SignUpResponse(validCustomerData, false);
        }
        Location locationVerify = Location.getLocationFromCode(valueOf(customerSignupRequest.location));
        if (locationVerify != null) {
            Customer customer = customerRepository.findOneByEmail(customerSignupRequest.email);
            if (customer == null) {

                Customer newCustomer = new Customer(customerSignupRequest.customerName, customerSignupRequest.email, customerSignupRequest.password, customerSignupRequest.phoneNo,customerSignupRequest.location,ServicePack.BASIC);
                saveCustomer(newCustomer);
                String response = "Welcome To Gharaana " + customerSignupRequest.customerName;
                return new SignUpResponse(response, true);
            }

            return new SignUpResponse("Customer Exists", false);

        }
        String response = getCurrentLocations();
        return new SignUpResponse("We Are Only Available in " + response, false);
    }

    public void saveCustomer(Customer customer) {
        customerRepository.save(customer);

    }

    public SignUpResponse registerExpert(ExpertSignupRequest expertSignupRequest) throws IOException, InterruptedException {
        String validWorkerData = SignupRequestValidator.validateExpertRequest(expertSignupRequest);
        if (validWorkerData != null) {
            return new SignUpResponse(validWorkerData, false);
        }
        Location locationVerify = Location.getLocationFromCode(valueOf(expertSignupRequest.location));
        if (locationVerify != null) {
            Expert expert =expertRepository.findOneByEmail(expertSignupRequest.email);
            if (expert == null) {
                Expert newExpert = new Expert(expertSignupRequest.expertName, expertSignupRequest.email, expertSignupRequest.password, expertSignupRequest.phoneNo, expertSignupRequest.location, expertSignupRequest.expertise);
                saveExpert(newExpert);

                String response = "Welcome to Gharaana " + expertSignupRequest.expertName;
                return new SignUpResponse(response, true);
            }

            return new SignUpResponse("Worker Exists", false);

        }
        String response = getCurrentLocations();
        return new SignUpResponse(response, false);
    }

    public void saveExpert(Expert expert) {
        expertRepository.save(expert);
    }

    public boolean isExpert(String email) {
        Expert expert = expertRepository.findOneByEmail(email);
        if (expert != null) {
            return true;
        } else {
            return false;
        }
    }


    public String getCurrentLocations() {
        String locations = "";
        for (Location locate : Location.values()) {
            locations = locations + " " + locate.toString();
        }
        return locations;
    }

    public String availableExpertises() {
        String expertise = "";
        for (Expertise expertises : Expertise.values()) {
            expertise = expertise + " " + expertises.toString();
        }
        return "We Are Providing Now" + expertise;
    }


    public Customer getCustomerByToken(String token) {
       String email= jwtUtil.extractUserEmail(token);
        Customer customer = customerRepository.findOneByEmail(email);
        return customer;
    }

    public Expert getExpertByToken(String token) {
        String email= jwtUtil.extractUserEmail(token);
        Expert expert = expertRepository.findOneByEmail(email);
        return expert;
    }

    public DeleteCustomerResponse deleteCustomer(String email) {
        Customer customer = customerRepository.findOneByEmail(email);
        if (customer != null) {
            customerRepository.deleteByEmail(email);
            List<Order>orderList=orderRepository.findByEmail(email);
            for(int i = 0;i<orderList.size();i++){
                orderRepository.deleteByOrderId(orderList.get(i).getOrderId());

            }
            return new DeleteCustomerResponse(true, "Customer Deleted");
        }
        return new DeleteCustomerResponse(false, "Customer Doesn't Exists");

    }
    public UpgradeAccountResponse upgradeAccount(UpgradeAccountRequest upgradeAccountRequest, String token) throws IOException, InterruptedException {
        String email= jwtUtil.extractUserEmail(token);
        Customer customer=customerRepository.findOneByEmail(email);

        if(customer.servicePack.equals(ServicePack.BASIC)){
                customer.servicePack=ServicePack.PREMIUM;
                customerRepository.save(customer);
                return new UpgradeAccountResponse("You are Premium Customer Now",true,ServicePack.PREMIUM);
            }

        return new UpgradeAccountResponse("You Are Already Premium Customer",true,ServicePack.PREMIUM);
    }
    public Boolean servicePack(String token){
        String email=jwtUtil.extractUserEmail(token);
        Customer customer=customerRepository.findOneByEmail(email);
        if(customer.servicePack==ServicePack.BASIC){
            return false;
        }
        return true;
    }






}

