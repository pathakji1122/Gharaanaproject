package com.beginnner.gharaana.Service;

import com.beginnner.gharaana.Entity.*;
import com.beginnner.gharaana.Repo.CustomerRepository;
import com.beginnner.gharaana.Repo.OrderRepository;
import com.beginnner.gharaana.Repo.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;

import static java.lang.String.valueOf;

@org.springframework.stereotype.Service
public class UserService {
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
            return new LoginResponce(null, false, "User Doesn't Exists");
        }
        if (customer.password.equals(password)) {
            String token = auth.generateToken(loginRequest.email);
            return new LoginResponce(token, false, "Login Successful");
        } else {
            return new LoginResponce(null, false, "Wrong Password");
        }
    }

    public LoginResponce loginWorkerVerify(LoginRequest loginRequest) {
        String password = loginRequest.password;
        String email = loginRequest.email;
        Worker worker = workerRepository.findOneByEmail(email);
        if (worker == null) {
            return new LoginResponce(null, false, "User Doesnt Exists");
        }
        if (worker.password.equals(password)) {
            String token = auth.generateToken(loginRequest.email);
            return new LoginResponce(token, true, "Login Successful");
        } else {
            return new LoginResponce(null, false, "Wrong Password");
        }
    }

    public SignupResponce registerCustomer(CustomerSignupRequest customerSignupRequest) {
        Location locationverify = Location.getLocationFromCode(valueOf(customerSignupRequest.location));
        if (locationverify != null) {
            Customer customer = customerRepository.findOneByEmail(customerSignupRequest.email);
            if (customer == null) {
                Customer newSaveCustomer = new Customer(customerSignupRequest.name, customerSignupRequest.email, customerSignupRequest.password, customerSignupRequest.phoneNo, customerSignupRequest.location, customerSignupRequest.servicePack);
                saveCustomer(newSaveCustomer);
                String responce = "Welcome to Gharaana " + customerSignupRequest.name;
                return new SignupResponce(responce, true);
            }

            return new SignupResponce("Customer Exists", false);

        }
        String responce = getCurrentLocations();
        return new SignupResponce(responce, false);
    }

    public void saveCustomer(Customer customer) {
        customerRepository.save(customer);

    }

    public SignupResponce registerWorker(WorkerSignupRequest workerSignupRequest) {
        Location locationverify = Location.getLocationFromCode(valueOf(workerSignupRequest.location));
        if (locationverify != null) {
            Worker worker = workerRepository.findOneByEmail(workerSignupRequest.email);
            if (worker == null) {
                Worker newSaveWorker = new Worker(workerSignupRequest.name, workerSignupRequest.email, workerSignupRequest.password, workerSignupRequest.phoneNo, workerSignupRequest.location, workerSignupRequest.expertise);
                saveWorker(newSaveWorker);
                String responce = "Welcome to Gharaana " + workerSignupRequest.name;
                return new SignupResponce(responce, true);
            }

            return new SignupResponce("Worker Exists", false);

        }
        String responce = getCurrentLocations();
        return new SignupResponce(responce, false);
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
        String email = token.split("@@@@@GharanaUser")[0];
        Customer customer = customerRepository.findOneByEmail(email);
        return customer;
    }

    public Worker getWorkerByToken(String token) {
        String email = token.split("@@@@@GharanaUser")[0];
        Worker worker = workerRepository.findOneByEmail(email);
        return worker;
    }

    public DeleteCustomerResponce deleteCustomer(String email) {
        Customer customer = customerRepository.findOneByEmail(email);
        if (customer != null) {
            customerRepository.deleteByEmail(email);
            return new DeleteCustomerResponce(true, "Customer Deleted");
        }
        return new DeleteCustomerResponce(false, "Customer Doesn't Exists");

    }


}

