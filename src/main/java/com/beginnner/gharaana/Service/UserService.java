package com.beginnner.gharaana.Service;

import com.beginnner.gharaana.Entity.*;
import com.beginnner.gharaana.Object.*;

import com.beginnner.gharaana.Repo.*;
import com.beginnner.gharaana.Validation.*;


import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

import static java.lang.String.valueOf;

@org.springframework.stereotype.Service
public class UserService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    Auth auth;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    WorkerRepository workerRepository;



    public LoginResponce loginCustomerVerify(LoginRequest loginRequest) {
        String password = loginRequest.password;
        String email = loginRequest.email;
        Customer customer = customerRepository.findOneByEmail(email);
        if (customer == null) {
            return new LoginResponce(null, false, "User Doesn't Exists",false);
        }
        if (customer.password.equals(password)) {
            String token = auth.generateToken(loginRequest.email);
            return new LoginResponce(token, false, "Login Successful",true);
        } else {
            return new LoginResponce(null, false, "Wrong Password",false);
        }
    }

    public LoginResponce loginWorkerVerify(LoginRequest loginRequest) {
        String password = loginRequest.password;
        String email = loginRequest.email;
        Worker worker = workerRepository.findOneByEmail(email);
        if (worker == null) {
            return new LoginResponce(null, false, "User Doesn't Exists",false);
        }
        if (worker.password.equals(password)) {
            String token = auth.generateToken(loginRequest.email);
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

                Customer newCustomer = new Customer(customerSignupRequest.customerName, customerSignupRequest.email, customerSignupRequest.password, customerSignupRequest.phoneNo,customerSignupRequest.location,"11",ServicePack.BASIC);
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

    public SignUpResponse registerWorker(WorkerSignupRequest workerSignupRequest) throws IOException, InterruptedException {
        String validWorkerData = SignupRequestValidator.validateWorkerRequest(workerSignupRequest);
        if (validWorkerData != null) {
            return new SignUpResponse(validWorkerData, false);
        }
        Location locationVerify = Location.getLocationFromCode(valueOf(workerSignupRequest.location));
        if (locationVerify != null) {
            Worker worker = workerRepository.findOneByEmail(workerSignupRequest.email);
            if (worker == null) {

                Worker newSaveWorker = new Worker(workerSignupRequest.expertName, workerSignupRequest.email, workerSignupRequest.password, workerSignupRequest.phoneNo, workerSignupRequest.location, workerSignupRequest.expertise);
                saveWorker(newSaveWorker);

                String response = "Welcome to Gharaana " + workerSignupRequest.expertName;
                return new SignUpResponse(response, true);
            }

            return new SignUpResponse("Worker Exists", false);

        }
        String response = getCurrentLocations();
        return new SignUpResponse(response, false);
    }

    public void saveWorker(Worker worker) {
        workerRepository.save(worker);
    }

    public boolean isWorker(String email) {
        Worker worker = workerRepository.findOneByEmail(email);
        if (worker != null) {
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
        String[] splitToken = token.split("##", 4);
        String email = splitToken[0] + "@gmail.com";
        Customer customer = customerRepository.findOneByEmail(email);
        return customer;
    }

    public Worker getWorkerByToken(String token) {
        String[] splitToken = token.split("##", 4);
        String email = splitToken[0] + "@gmail.com";
        Worker worker = workerRepository.findOneByEmail(email);
        return worker;
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
    public UpgradeAccountResponse upgradeAccount(UpgradeAccountRequest upgradeAccountRequest) throws IOException, InterruptedException {
        Customer customer=getCustomerByToken(upgradeAccountRequest.token);
        if(customer.servicePack.equals(ServicePack.BASIC)){
                customer.servicePack=ServicePack.PREMIUM;
                customerRepository.save(customer);
                return new UpgradeAccountResponse("You are Premium Customer Now",true,ServicePack.PREMIUM);
            }


        return new UpgradeAccountResponse("You Are Already Premium Customer",true,ServicePack.PREMIUM);
    }






}

