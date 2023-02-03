package com.beginnner.gharaana.Service;

import com.beginnner.gharaana.Entity.*;
import com.beginnner.gharaana.Repo.CustomerRepository;
import com.beginnner.gharaana.Repo.OrderRepository;
import com.beginnner.gharaana.Repo.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class UserService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    WorkerRepository workerRepository;


    public boolean loginCustomerVerify(LoginRequest loginRequest) {
        String password = loginRequest.password;
        String email = loginRequest.email;
        Customer customer = customerRepository.findOneByEmail(email);
        if (customer.password.equals(password)) {
            return true;
        } else {
            return false;
        }
    }
    public boolean loginWorkerVerify(LoginRequest loginRequest) {
        String password = loginRequest.password;
        String email = loginRequest.email;
        Worker worker = workerRepository.findOneByEmail(email);
        if (worker.password.equals(password)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean registerCustomer(SignupRequest signupRequest) {
        String email = signupRequest.email;
        String phoneNo = signupRequest.phonenNo;
        Customer customer = customerRepository.findOneByEmail(email);
        Customer customer1 = customerRepository.findOneByPhoneNo(phoneNo);
        if (customer != null || customer1 != null) {
            return false;
        }
        Customer newCustomer = new Customer(signupRequest.name, signupRequest.email, signupRequest.password, signupRequest.phonenNo, signupRequest.location, signupRequest.servicePack);
        User user = new User(signupRequest.name, signupRequest.email, signupRequest.password, signupRequest.phonenNo, signupRequest.location);
        saveCustomer(newCustomer);
        return true;
    }

    public void saveCustomer(Customer customer) {
        customerRepository.save(customer);

    }

    public boolean registerWorker(SignupRequest signupRequest) {
        String email = signupRequest.email;
        Worker worker = workerRepository.findOneByEmail(email);
        if (worker != null) {
            return false;
        }

        Worker newWorker = new Worker(signupRequest.name, signupRequest.email, signupRequest.password, signupRequest.phonenNo, signupRequest.location, signupRequest.expertise);
        saveWorker(newWorker);
        User user = new User(signupRequest.name, signupRequest.email, signupRequest.password, signupRequest.phonenNo, signupRequest.location);
        return true;
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
    public boolean isCustomer(String email) {
        Customer customer= customerRepository.findOneByEmail(email);
        if (customer != null) {
            return true;
        } else {
            return false;
        }
    }


    public String getCustomerLocations() {
        String locations = "";
        for (Location locate : Location.values()) {
            locations = locations + " " + locate.toString();
        }
        return locations;
    }
    public String getCustomerExpertise() {
        String expertise = "";
        for (Expertise expertises : Expertise.values()) {
            expertise = expertise + " " + expertises.toString();
        }
        return "We Are Providing Now"+expertise;
    }

    public boolean customerVeirfication(String email) {
        Customer customer = customerRepository.findOneByEmail(email);
        if (customer != null) {
            return true;
        }
        return false;
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

    public void deleteCustomer(String email) {
        customerRepository.deleteByEmail(email);
    }

}

