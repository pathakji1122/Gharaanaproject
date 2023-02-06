package com.beginnner.gharaana.Service;

import com.beginnner.gharaana.Entity.*;
import com.beginnner.gharaana.Repo.OrderRepository;
import com.beginnner.gharaana.Repo.OtpRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@org.springframework.stereotype.Service
public class OrderService {
    @Autowired
    OtpRepository otpRepository;
    @Autowired
    UserService userService;
    @Autowired
    OrderRepository orderRepository;

    public String createOrderId(OrderRequest orderRequest) {
        long id = orderRepository.count() + 1;
        return String.valueOf(id);

    }

    public void saveOrder(OrderRequest orderRequest, String orderId) {
        Customer customer = userService.getCustomerByToken(orderRequest.token);
        OrderStatus orderStatus = OrderStatus.NOT_ACCEPTED;
        Times times = placedOrder(orderRequest);
        Order order = new Order(customer.email, customer.name, customer.location, orderRequest.expertise, orderRequest.price, orderStatus, orderId, null, times);
        orderRepository.save(order);
    }

    public Order acceptOrder(AcceptOrderRequest acceptOrderRequest) {
        Order order = orderRepository.findByOrderId(acceptOrderRequest.orderId);
        order.times.orderAcceptedAt = orderTimes();
        String token = acceptOrderRequest.token;
        Worker worker = userService.getWorkerByToken(token);
        order.orderStatus = OrderStatus.ACCEPTED;
        order.gharanaAgent = worker.email;
        orderRepository.save(order);

        return order;
    }

    public Order startOrder(StartOrderRequest startOrderRequest) {
        Order order = orderRepository.findByOrderId(startOrderRequest.orderId);
        order.times.startTime = orderTimes();
        orderRepository.save(order);
        Otp otp = new Otp(order.orderId, generateOtp());
        otpRepository.save(otp);
        return order;
    }

    public List<Order> checkOrders(CheckOrdersRequest checkOrdersRequest) {
        Worker worker = userService.getWorkerByToken(checkOrdersRequest.token);
        List<Order> orders = orderRepository.findByLocationAndExpertise(worker.location, worker.expertise);
        return orders;
    }

    public List<Order> myOrder(MyOrderReques myOrderReques) {
        String token = myOrderReques.token;
        Customer customer = userService.getCustomerByToken(token);
        return orderRepository.findByEmail(customer.email);
    }

    public Order orderStatus(OrderStatusRequest orderStatusRequest) {
        Customer customer = userService.getCustomerByToken(orderStatusRequest.token);
        Order order = orderRepository.findByOrderId(orderStatusRequest.orderId);
        if (order.email.equals(customer.email) == false || order == null) {
            return null;
        }
        return order;
    }

    public long generateOtp() {
        long min = 111111;
        long max = 999999;
        long otp = (long) (Math.random() * (max - min + 1) + min);
        return otp;
    }

    public boolean verifyOtp(CompleteOrderRequest completeOrderRequest) {
        Otp newOtp = otpRepository.findOneByOrderId(completeOrderRequest.orderId);
        if (newOtp.otp == completeOrderRequest.otp) {
            return true;
        }
        return false;
    }


    public void orderComplete(CompleteOrderRequest completeOrderRequest) {
        Order order = orderRepository.findByOrderId(completeOrderRequest.orderId);
        order.times.completeTime = orderTimes();
        order.orderStatus = OrderStatus.COMPLETED;
        orderRepository.save(order);

    }

    public Otp getOtp(GetOtpRequest getOtpRequest) {
        Otp newOtp = otpRepository.findOneByOrderId(getOtpRequest.orderId);
        return newOtp;
    }

    public void saveOtp(String orderId) {
        long otp = generateOtp();
        Otp newOtp = new Otp(orderId, otp);
        otpRepository.save(newOtp);
    }

    public Order getOrderByOrderId(String orderId) {
        Order order = orderRepository.findByOrderId(orderId);
        return order;
    }

    public Times placedOrder(OrderRequest orderRequest) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm-yyyy/MM/dd");
        LocalDateTime date = LocalDateTime.now();
        String placedAt = dtf.format(date);
        Times times = new Times(placedAt, orderRequest.placedFor, null, null, null);
        return times;
    }

    public String orderTimes() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm-yyyy/MM/dd");
        LocalDateTime date = LocalDateTime.now();
        String orderTimes = dtf.format(date);
        return orderTimes;

    }
}