package com.beginnner.gharaana.Service;

import com.beginnner.gharaana.PaymentGatewayResponse.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;


@org.springframework.stereotype.Service
public class PaymentService {

    public CreatePaymentAccountGateWayResponse createPaymentAccount(String name, String email) throws IOException, InterruptedException {
        HashMap values = new HashMap<String, String>();
        values.put("name", name);
        values.put("email", email);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(values);
        String uri = "http://localhost:3600/paymentgateway/createaccount";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri)).POST(HttpRequest.BodyPublishers.ofString(requestBody)).setHeader("content-type", "application/json").build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String json = response.body();
        CreatePaymentAccountGateWayResponse createPaymentAccountResponse = objectMapper.readValue(json, CreatePaymentAccountGateWayResponse.class);
        return createPaymentAccountResponse;
    }

    public String gatewayResponse(String uri, String requestBody) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri)).POST(HttpRequest.BodyPublishers.ofString(requestBody)).setHeader("content-type", "application/json").build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String json = response.body();
        return json;
    }

    public AddBalanceGateWayResponse addBalance(String amount, String email) throws IOException, InterruptedException {
        HashMap values = new HashMap<String, String>();
        values.put("email", email);
        values.put("amount", amount);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(values);
        String uri = "http://localhost:3600/paymentgateway/addbalance";
        String json = gatewayResponse(uri, requestBody);
        AddBalanceGateWayResponse addBalanceResponse = objectMapper.readValue(json, AddBalanceGateWayResponse.class);
        return addBalanceResponse;

    }

    public OrderPaymentGateWayResponse orderPayment(String customerEmail, String workerEmail, String amount) throws IOException, InterruptedException {
        HashMap values = new HashMap<String, String>();
        values.put("senderEmail", customerEmail);
        values.put("receiverEmail", workerEmail);
        values.put("amount", amount);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(values);
        String uri = "http://localhost:3600/paymentgateway/payment";
        String json = gatewayResponse(uri, requestBody);
        OrderPaymentGateWayResponse orderPaymentResponse = objectMapper.readValue(json, OrderPaymentGateWayResponse.class);
        return orderPaymentResponse;


    }

    public OrderPaymentGateWayResponse premiumCharge(String customerEmail) throws IOException, InterruptedException {
        HashMap values = new HashMap<String, String>();
        values.put("senderEmail", customerEmail);
        values.put("receiverEmail", "gharaanapayment@gmail.com");
        values.put("amount", "100");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(values);
        String uri = "http://localhost:3600/paymentgateway/payment";
        String json = gatewayResponse(uri, requestBody);
        OrderPaymentGateWayResponse orderPaymentResponse = objectMapper.readValue(json, OrderPaymentGateWayResponse.class);
        return orderPaymentResponse;


    }

    public CheckBalanceGateWayResponse checkBalance(String email) throws IOException, InterruptedException {
        HashMap values = new HashMap<String, String>();
        values.put("email", email);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(values);
        String uri = "http://localhost:3600/paymentgateway/checkbalance";
        String json = gatewayResponse(uri, requestBody);
        CheckBalanceGateWayResponse checkBalanceResponse = objectMapper.readValue(json, CheckBalanceGateWayResponse.class);
        return checkBalanceResponse;

    }


}
