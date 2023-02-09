package com.beginnner.gharaana.Service;

import com.beginnner.gharaana.Entity.*;
import com.beginnner.gharaana.Repo.OrderRepository;
import com.beginnner.gharaana.Repo.OtpRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import static java.lang.String.valueOf;

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

    public void saveOrder(OrderRequest orderRequest, String orderId) throws ParseException {
        Customer customer = userService.getCustomerByToken(orderRequest.token);
        OrderStatus orderStatus = OrderStatus.NOT_ACCEPTED;
        Times times = placingOrderTimes(orderRequest);

        Order order = new Order.OrderBuilder(orderId).setOrderStatus(orderStatus).setLocation(customer.location).setPrice(orderRequest.price).setTimes(times).setExpertise(orderRequest.expertise).setEmail(customer.email).setName(customer.name).build();
        orderRepository.save(order);
    }

    public AcceptOrderResponce acceptOrder(AcceptOrderRequest acceptOrderRequest) {
        Order order = orderRepository.findByOrderId(acceptOrderRequest.orderId);
        if (order.getOrderStatus().equals(OrderStatus.NOT_ACCEPTED)) {
            order.getTimes().orderAcceptedAt = orderTimes();
            String token = acceptOrderRequest.token;
            Worker worker = userService.getWorkerByToken(token);
            order.setOrderStatus(OrderStatus.ACCEPTED);
            order.setGharanaAgent(worker.email);
            orderRepository.save(order);
            return new AcceptOrderResponce("Order Accepted", order, true);
        } else if (order.getOrderStatus().equals(OrderStatus.ACCEPTED)) {
            return new AcceptOrderResponce("Order Already Accepted", null, false);

        }
        return new AcceptOrderResponce("Order Already Completed", null, false);

    }

    public StartOrderResponce startOrder(StartOrderRequest startOrderRequest) {
        Worker worker = userService.getWorkerByToken(startOrderRequest.token);
        Boolean verifyAgent = verifyGharanaAgent(startOrderRequest.orderId, worker);
        if (verifyAgent) {
            Order order = orderRepository.findByOrderId(startOrderRequest.orderId);
            Times times = order.getTimes();
            times.startTime = orderTimes();
            order.setTimes(times);
            orderRepository.save(order);
            Otp otp = new Otp(order.getOrderId(), Util.generateOtp());
            otpRepository.save(otp);
            return new StartOrderResponce("Order Started", true, order);
        }
        return new StartOrderResponce("Agent Mismatch", false, null);

    }

    public CheckOrdersResponce checkOrders(CheckOrdersRequest checkOrdersRequest) {
        Worker worker = userService.getWorkerByToken(checkOrdersRequest.token);
        List<Order> orders = orderRepository.findByLocationAndExpertiseAndOrderStatus(worker.location, worker.expertise, OrderStatus.NOT_ACCEPTED);
        return new CheckOrdersResponce("Current Orders Are", orders);
    }

    public MyOrderResponce myOrder(MyOrderRequest myOrderRequest) {
        String token = myOrderRequest.token;
        Customer customer = userService.getCustomerByToken(token);
        List<Order> orderList = orderRepository.findByEmail(customer.email);
        return new MyOrderResponce(true, "Your Orders Are", orderList);
    }

    public OrderStatusResponce orderStatus(OrderStatusRequest orderStatusRequest) {
        Order order = orderRepository.findByOrderId(orderStatusRequest.orderId);
        if (order != null) {
            OrderStatusResponce orderStatusResponce = new OrderStatusResponce(true, "Your Order Status is", order);
            return orderStatusResponce;
        } else {
            return new OrderStatusResponce(false, "Enter Correct OrderId", null);
        }
    }


    public boolean verifyOtp(CompleteOrderRequest completeOrderRequest) {
        Otp checkOtp = otpRepository.findOneByOrderId(completeOrderRequest.orderId);
        if (checkOtp.otp == completeOrderRequest.otp) {
            return true;
        }
        return false;
    }


    public CompleteOrderResponce completeOrder(CompleteOrderRequest completeOrderRequest) {
        Worker worker = userService.getWorkerByToken(completeOrderRequest.token);
        Boolean verifyAgent = verifyGharanaAgent(completeOrderRequest.orderId, worker);
        if (verifyAgent) {
            Boolean verifyWorkerOtp = verifyOtp(completeOrderRequest);
            if (verifyWorkerOtp) {
                Order order = orderRepository.findByOrderId(completeOrderRequest.orderId);
                Times times = order.getTimes();
                times.completeTime = orderTimes();
                order.setTimes(times);
                order.setOrderStatus(OrderStatus.COMPLETED);
                orderRepository.save(order);
                return new CompleteOrderResponce("Order Completed", true);
            }
            return new CompleteOrderResponce("Wrong Otp", false);
        }
        return new CompleteOrderResponce("Agent Mismatch", false);

    }

    public GetOtpResponce getOtp(GetOtpRequest getOtpRequest) {
        Otp currentOtp = otpRepository.findOneByOrderId(getOtpRequest.orderId);
        return new GetOtpResponce(currentOtp.otp, "Your Otp is", true);
    }


    public Times placingOrderTimes(OrderRequest orderRequest) throws ParseException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:dd:MM:yyyy");
        LocalDateTime date = LocalDateTime.now();
        String placedAt = dtf.format(date);
        LocalDateTime datePlacedFor = LocalDateTime.parse(orderRequest.placedFor, dtf);
        String placedFor = dtf.format(datePlacedFor);

        Times times = new Times(placedAt, placedFor, null, null, null);
        return times;
    }

    public String orderTimes() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:dd:MM:yyyy");
        LocalDateTime date = LocalDateTime.now();
        String orderTimes = dtf.format(date);
        return orderTimes;

    }

    public Boolean verifyGharanaAgent(String orderId, Worker worker) {
        Order order = orderRepository.findByOrderId(orderId);
        if (order.getGharanaAgent().equals(worker.email)) {
            return true;
        }
        return false;
    }

    public OrderResponce placeOrder(OrderRequest orderRequest) throws ParseException {
        try {
            Expertise expertise = Expertise.checkExpertise(valueOf(orderRequest.expertise));
            if (expertise == null) {
                String responce = userService.availableExpertises();
                return new OrderResponce(false, null, responce);
            }
            String orderId = createOrderId(orderRequest);
            saveOrder(orderRequest, orderId);
            OrderResponce orderResponce = new OrderResponce(true, orderId, "Order Successful");
            return orderResponce;
        } catch (ParseException e) {
            OrderResponce orderResponce = new OrderResponce(false, null, "Enter Time in Format HH-dd/MM/yyyy");
            return orderResponce;
        }

    }
}