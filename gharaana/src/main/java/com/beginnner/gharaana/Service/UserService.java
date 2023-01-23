package com.beginnner.gharaana.Service;

import com.beginnner.gharaana.Entity.Customer;
import com.beginnner.gharaana.Entity.Location;
import com.beginnner.gharaana.Entity.User;
import com.beginnner.gharaana.Entity.Worker;
import com.beginnner.gharaana.Repo.CustomerRepository;
import com.beginnner.gharaana.Repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    CustomerRepository customerRepository;
    public boolean loginVerify(LoginRequest loginRequest) {
        String password = loginRequest.password;
        String email = loginRequest.email;
        Customer customer = customerRepository.findOneByEmail(email);
        if (customer.password.equals(password)) {
            return true;
        } else {
            return false;
        }
    }
    public boolean registerCustomer(SignupRequest signupRequest){
        String email= signupRequest.email;
        Customer customer=customerRepository.findOneByEmail(email);
        if(customer!=null){
            return false;
        }
        Customer newCustomer =new Customer(signupRequest.name,signupRequest.email,signupRequest.password,signupRequest.phoneno,signupRequest.location,signupRequest.servicePack);
        saveCustomer(newCustomer);
        return true;
    }
    public void saveCustomer(Customer customer) {
        customerRepository.save(customer);

    }
    public boolean isWorker(User user) {
        Worker worker = (Worker) user;
        if (worker != null) {
            return true;
        } else {
            return false;
        }
    }
    public boolean currentLocation(SignupRequest signupRequest){
        Location location=signupRequest.location;
        Location locations[]=Location.values();
        for(Location location1:locations){
            if(location1.equals(location)){
                return true;
            }
        }
        return false;
    }
}
