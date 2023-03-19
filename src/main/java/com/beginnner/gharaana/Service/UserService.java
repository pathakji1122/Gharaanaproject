package com.beginnner.gharaana.Service;

import com.beginnner.gharaana.Entity.*;
import com.beginnner.gharaana.Object.*;
import com.beginnner.gharaana.Object.AddBalanceResponse;
import com.beginnner.gharaana.Repo.*;
import com.beginnner.gharaana.Validation.*;
import com.beginnner.gharaana.PaymentGatewayResponse.*;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

import static java.lang.String.valueOf;

@org.springframework.stereotype.Service
public class UserService {
    @Autowired
    AgentInfoRepository agentInfoRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    PaymentService paymentService;
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
            return new LoginResponce(null, false, "User Doesn't Exists");
        }
        if (worker.password.equals(password)) {
            String token = auth.generateToken(loginRequest.email);
            return new LoginResponce(token, true, "Login Successful");
        } else {
            return new LoginResponce(null, false, "Wrong Password");
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
                String accountNo=paymentService.createPaymentAccount(customerSignupRequest.name,customerSignupRequest.email).accountNo;
                Customer newCustomer = new Customer(customerSignupRequest.name, customerSignupRequest.email, customerSignupRequest.password, customerSignupRequest.phoneNo,customerSignupRequest.location,accountNo,ServicePack.BASIC);
                saveCustomer(newCustomer);
                String response = "Welcome To Gharaana " + customerSignupRequest.name;
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
                String accountNo=paymentService.createPaymentAccount(workerSignupRequest.name,workerSignupRequest.email).accountNo;
                Worker newSaveWorker = new Worker(workerSignupRequest.name, workerSignupRequest.email, workerSignupRequest.password, workerSignupRequest.phoneNo, workerSignupRequest.location,accountNo, workerSignupRequest.expertise);
                saveWorker(newSaveWorker);
                AgentInfo agentInfo=new AgentInfo(workerSignupRequest.name,workerSignupRequest.email,workerSignupRequest.expertise,0,0);
                agentInfoRepository.save(agentInfo);
                String response = "Welcome to Gharaana " + workerSignupRequest.name;
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
            Boolean verify=paymentService.premiumCharge(customer.email).status;
            String response=paymentService.premiumCharge(customer.email).response;
            if(verify){
                customer.servicePack=ServicePack.PREMIUM;
                customerRepository.save(customer);
                return new UpgradeAccountResponse("You are Premium Customer Now",true,ServicePack.PREMIUM);
            }
            return new UpgradeAccountResponse(response,false,ServicePack.BASIC);
        }
        return new UpgradeAccountResponse("You Are Already Premium Customer",true,ServicePack.PREMIUM);
    }
    public AddBalanceResponse addBalance(AddBalanceRequest addBalanceRequest) throws IOException, InterruptedException {
        Customer customer=getCustomerByToken(addBalanceRequest.token);
       AddBalanceGateWayResponse addBalanceGateWayResponse = paymentService.addBalance(addBalanceRequest.amount,customer.email);
       return new AddBalanceResponse(addBalanceGateWayResponse.response,addBalanceGateWayResponse.status);
    }
    public CheckBalanceResponse checkBalance(CheckBalanceRequest checkBalanceRequest) throws IOException, InterruptedException {
        Customer customer=getCustomerByToken(checkBalanceRequest.token);
        CheckBalanceGateWayResponse checkBalanceGateWayResponse= paymentService.checkBalance(customer.email);
        return new CheckBalanceResponse(checkBalanceGateWayResponse.balance,checkBalanceGateWayResponse.status);
    }
    public RatingResponse rating(RatingRequest ratingRequest){
        Boolean validRating=validRatingPoint(ratingRequest);
        if(validRating==false){
            return new RatingResponse("Please Rate Between 1-10",false);
        }
        if(ratingRequest.ratingPoint>10||ratingRequest.ratingPoint<1){
            return new RatingResponse("Please Rate between 1-10",false);
        }

        Order order=orderRepository.findByOrderId(ratingRequest.orderId);
        AgentInfo agentInfo=agentInfoRepository.findOneByEmail(order.getGharaanaAgent());
        Customer customer=getCustomerByToken(ratingRequest.token);
        if(order.getEmail().equals(customer.email)){
            agentInfo.rating= (agentInfo.rating+ ratingRequest.ratingPoint)/agentInfo.totalOrders;
            agentInfoRepository.save(agentInfo);
            return new RatingResponse("Thank You For Rating",true);


        }
        return new RatingResponse("Wrong Request",false);
    }

    public boolean validRatingPoint(RatingRequest ratingRequest){
        try {
            Integer.parseInt(String.valueOf(ratingRequest.ratingPoint));
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }
    }




}

