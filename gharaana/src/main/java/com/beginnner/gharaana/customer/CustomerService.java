package com.beginnner.gharaana.customer;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;



@org.springframework.stereotype.Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;
    HashSet<String>ServiceLocation=new HashSet<>();
    public Boolean checkLocation(SignupRequest signupRequest) {
        ServiceLocation.add("Hyderabad");
        ServiceLocation.add("Agartala");
        ServiceLocation.add("Vishakhapatnam");
        ServiceLocation.add("Delhi");
        ServiceLocation.add("Sasaram");
        if(ServiceLocation.contains(signupRequest.location)){
            return true;
        }
          return false;
    }
    public Boolean login(LoginRequest loginRequest){
        String email=loginRequest.email;
        String password=loginRequest.password;
        Customer customer =findCustomerByEmail(email);
        if(customer==null){
            return false;
        }
        if(customer.getPassword().equals(password)){
            return true;
        }
         return false;
    }
    private Customer findCustomerByEmail(String email){
        Customer customer = customerRepository.findOneByEmail(email);
        return customer;
    }
    public Boolean signup(SignupRequest signupRequest){
        String email = signupRequest.email;
        Customer customer = findCustomerByEmail(email);
        if(customer!=null){
            return false;
            }
        Customer newCustomer = new Customer(signupRequest.name,signupRequest.email,signupRequest.location,signupRequest.password);
         saveCustomer(newCustomer);
          return true;
        }
        private void saveCustomer(Customer customer) {
          customerRepository.save(customer);
        }

}
