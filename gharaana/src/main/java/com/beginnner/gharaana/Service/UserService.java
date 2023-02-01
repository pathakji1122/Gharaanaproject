package com.beginnner.gharaana.Service;

import com.beginnner.gharaana.Entity.*;
import com.beginnner.gharaana.Repo.CustomerRepository;
import com.beginnner.gharaana.Repo.OrderRepository;
import com.beginnner.gharaana.Repo.UserRepository;
import com.beginnner.gharaana.Repo.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class UserService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    WorkerRepository workerRepository;
    public boolean userVerify(String email){
        User user= userRepository.findOneByEmail(email);
        if(user==null){
            return false;
        }
        return true;
    }
    public boolean loginVerify(LoginRequest loginRequest) {
        String password = loginRequest.password;
        String email = loginRequest.email;
        User user = userRepository.findOneByEmail(email);
        if (user.password.equals(password)) {
            return true;
        } else {
            return false;
        }
    }
    public boolean registerCustomer(SignupRequest signupRequest){
        String email= signupRequest.email;
        String phoneno=signupRequest.phoneno;
        Customer customer=customerRepository.findOneByEmail(email);
        Customer customer1=customerRepository.findOneByPhoneno(phoneno);
        if(customer!=null||customer1!=null){
            return false;
        }
        Customer newCustomer =new Customer(signupRequest.name,signupRequest.email,signupRequest.password,signupRequest.phoneno,signupRequest.location,signupRequest.servicePack);
        User user=new User(signupRequest.name,signupRequest.email,signupRequest.password,signupRequest.phoneno,signupRequest.location);
        userRepository.save(user);
        saveCustomer(newCustomer);
        return true;
    }
    public void saveCustomer(Customer customer) {
        customerRepository.save(customer);

    }
    public boolean registerWorker(SignupRequest signupRequest){
        String email= signupRequest.email;
        Worker worker=workerRepository.findOneByEmail(email);
        if(worker!=null){
            return false;
        }

        Worker newWorker =new Worker(signupRequest.name,signupRequest.email,signupRequest.password,signupRequest.phoneno,signupRequest.location,signupRequest.expertise);
        saveWorker(newWorker);
        User user=new User(signupRequest.name,signupRequest.email,signupRequest.password,signupRequest.phoneno,signupRequest.location);
        userRepository.save(user);
        return true;
    }
    public void saveWorker(Worker worker) {
        workerRepository.save(worker);
    }
    public boolean isWorker(User user) {
        Worker worker = workerRepository.findOneByEmail(user.email);
        if (worker != null) {
            return true;
        } else {
            return false;
        }
    }


    public String getCustomerLocations(){
        String locations="";
        for (Location locate : Location.values()) {
            locations=locations+" "+locate.toString();
        }
        return locations;
    }
    public boolean customerVeirfication(String email){
        Customer customer=customerRepository.findOneByEmail(email);
        if(customer!=null){
            return true;
        }
        return false;
    }

    public Customer getCustomerByToken(String token){
        String email=token.split("@@@@@GharanaUser")[0];
        Customer customer=customerRepository.findOneByEmail(email);
        return customer;
    }
    public Worker getWorkerByToken(String token){
        String email=token.split("@@@@@GharanaUser")[0];
        Worker worker=workerRepository.findOneByEmail(email);
        return worker;
    }
   public void deleteCustomer(String email){
        customerRepository.deleteByEmail(email);
        userRepository.deleteByEmail(email);
   }

    }

