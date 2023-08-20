package com.beginnner.gharaana.Service;

import com.beginnner.gharaana.Entity.*;
import com.beginnner.gharaana.Object.*;

import com.beginnner.gharaana.Repo.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    public AcceptOrderResponse acceptOrder(AcceptOrderRequest acceptOrderRequest) {
        Order order = orderRepository.findByOrderId(acceptOrderRequest.orderId);
        if (order.getOrderStatus().equals(OrderStatus.NOT_ACCEPTED)) {
            order.getTimes().orderAcceptedAt = createOrderTime();
            String token = acceptOrderRequest.token;
            Worker worker = userService.getWorkerByToken(token);
            order.setOrderStatus(OrderStatus.ACCEPTED);
            order.setGharaanaAgent(worker.email);
            orderRepository.save(order);
            return new AcceptOrderResponse("Order Accepted", order, true);
        } else if (order.getOrderStatus().equals(OrderStatus.ACCEPTED)) {
            return new AcceptOrderResponse("Order Already Accepted", null, false);

        }
        return new AcceptOrderResponse("Order Already Completed", null, false);

    }

    public StartOrderResponse startOrder(StartOrderRequest startOrderRequest) {
        Worker worker = userService.getWorkerByToken(startOrderRequest.token);
        Boolean verifyAgent = verifyGharaanaAgent(startOrderRequest.orderId, worker);
        if (verifyAgent) {
            Order order = orderRepository.findByOrderId(startOrderRequest.orderId);
            Times times = order.getTimes();
            times.startTime = createOrderTime();
            order.setOrderStatus(OrderStatus.IN_PROGRESS);
            order.setTimes(times);
            orderRepository.save(order);
            Otp otp = new Otp(order.getOrderId(), Util.generateOtp());
            otpRepository.save(otp);
            return new StartOrderResponse("Order Started", true, order);
        }
        return new StartOrderResponse("Agent Mismatch", false, null);

    }

    public CheckOrdersResponse checkOrders(CheckOrdersRequest checkOrdersRequest) {
        Worker worker = userService.getWorkerByToken(checkOrdersRequest.token);
        List<Order> orders = orderRepository.findByLocationAndExpertiseAndOrderStatus(worker.location, worker.expertise, OrderStatus.NOT_ACCEPTED);
        return new CheckOrdersResponse("Current Orders Are", orders);
    }

    public MyOrderResponse myOrder(MyOrderRequest myOrderRequest) {
        String token = myOrderRequest.token;
        Customer customer = userService.getCustomerByToken(token);
        List<Order> orderList = orderRepository.findByEmail(customer.email);
        return new MyOrderResponse(true, "Your Orders Are", orderList);
    }

    public OrderStatusResponse orderStatus(OrderStatusRequest orderStatusRequest) {
        Customer customer = userService.getCustomerByToken(orderStatusRequest.token);
        Order order = orderRepository.findByOrderId(orderStatusRequest.orderId);
        if (order != null) {
            if (order.getEmail().equals(customer.email)) {
                OrderStatusResponse orderStatusresponse = new OrderStatusResponse(true, "Your Gharaana Agent is " + order.getGharaanaAgent(), order);
                return orderStatusresponse;
            }
            return new OrderStatusResponse(false, "Enter Correct order Id", null);
        } else {
            return new OrderStatusResponse(false, "Enter Correct OrderId", null);
        }
    }


    public boolean verifyOtp(CompleteOrderRequest completeOrderRequest) {
        Otp checkOtp = otpRepository.findOneByOrderId(completeOrderRequest.orderId);
        if (checkOtp.otp == completeOrderRequest.otp) {
            return true;
        }
        return false;
    }


    public CompleteOrderResponse completeOrder(CompleteOrderRequest completeOrderRequest) {
        Worker worker = userService.getWorkerByToken(completeOrderRequest.token);
        Boolean verifyAgent = verifyGharaanaAgent(completeOrderRequest.orderId, worker);
        if (verifyAgent) {
            Boolean verifyWorkerOtp = verifyOtp(completeOrderRequest);
            if (verifyWorkerOtp) {
                Order order = orderRepository.findByOrderId(completeOrderRequest.orderId);
                Times times = order.getTimes();
                times.completeTime = createOrderTime();
                order.setTimes(times);
                order.setOrderStatus(OrderStatus.COMPLETED);

                orderRepository.save(order);
                return new CompleteOrderResponse("Order Completed", true);
            }
            return new CompleteOrderResponse("Wrong Otp", false);
        }
        return new CompleteOrderResponse("Agent Mismatch", false);

    }

    public GetOtpResponse getOtp(GetOtpRequest getOtpRequest) {
        Otp currentOtp = otpRepository.findOneByOrderId(getOtpRequest.orderId);
        if (currentOtp == null) {
            return new GetOtpResponse(null, "Order id " + getOtpRequest.orderId + " Doesn't exist", false);
        }
        return new GetOtpResponse(currentOtp.otp, "Your Otp is", true);
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

    public String createOrderTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:dd:MM:yyyy");
        LocalDateTime date = LocalDateTime.now();
        String orderTimes = dtf.format(date);
        return orderTimes;

    }

    public Boolean verifyGharaanaAgent(String orderId, Worker worker) {
        Order order = orderRepository.findByOrderId(orderId);
        if (order.getGharaanaAgent().equals(worker.email)) {
            return true;
        }
        return false;
    }

    public OrderResponse placeOrder(OrderRequest orderRequest) throws ParseException {
        try {
            Expertise expertise = Expertise.checkExpertise(valueOf(orderRequest.expertise));
            if (expertise == null) {
                String response = userService.availableExpertises();
                return new OrderResponse(false, null, response);
            }
            String orderId = createOrderId(orderRequest);
            saveOrder(orderRequest, orderId);
            OrderResponse orderresponse = new OrderResponse(true, orderId, "Order Successful");
            return orderresponse;
        } catch (ParseException e) {
            OrderResponse orderresponse = new OrderResponse(false, null, "Enter Time in Format HH-dd/MM/yyyy");
            return orderresponse;
        }

    }
    public CancelOrderResponse cancelOrder(CancelOrderRequest cancelOrderRequest){
        Order order=orderRepository.findByOrderId(cancelOrderRequest.orderId);
        if(order!=null){
            if(order.getOrderStatus().equals(OrderStatus.CANCELLED)){
                return new CancelOrderResponse("Order Already Cancelled",false,order);
            }
            if(order.getOrderStatus().equals(OrderStatus.COMPLETED)){
                return new CancelOrderResponse("Completed Order Cant Be Cancelled",false,order);
            }
            order.setOrderStatus(OrderStatus.CANCELLED);
            orderRepository.save(order);
            return new CancelOrderResponse("Order Cancelled",true,order);
        }

        return new CancelOrderResponse("OrderId Doesnt Exist",false,null);


    }

}