package com.beginnner.gharaana.Service;

import com.beginnner.gharaana.Entity.Customer;
import com.beginnner.gharaana.Entity.Order;
import com.beginnner.gharaana.Entity.OrderStatus;
import com.beginnner.gharaana.Entity.Worker;
import com.beginnner.gharaana.Repo.CustomerRepository;
import com.beginnner.gharaana.Repo.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service
public class OrderService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    UserService userService;
    @Autowired
    OrderRepository orderRepository;
    public String createOrderId(OrderRequest orderRequest){
        long id= orderRepository.count()+1;
        return String.valueOf(id);

    }
    public void saveOrder(OrderRequest orderRequest,String orderId){
        Customer customer=userService.getCustomerByToken(orderRequest.token);
        OrderStatus orderStatus=OrderStatus.NOT_ACCEPTED;
        Order order=new Order(customer.email,customer.name,customer.location,orderRequest.expertise,orderRequest.price,orderStatus,orderRequest.time,orderId,null);
        orderRepository.save(order);
    }
    public Order acceptOrder(AcceptOrderRequest acceptOrderRequest){
        Order order=orderRepository.findByOrderId(acceptOrderRequest.orderId);
        String token=acceptOrderRequest.token;
        Worker worker=userService.getWorkerByToken(token);
        order.orderStatus=OrderStatus.ACCEPTED;
        order.GharanaAgent= worker.email;
        orderRepository.save(order);
        return order;
    }
    public List<Order>checkOrders(CheckOrdersRequest checkOrdersRequest){
        Worker worker=userService.getWorkerByToken(checkOrdersRequest.token);
        List<Order>orders=orderRepository.findByLocationAndExpertise(worker.location,worker.expertise);
        return orders;
    }
    public List<Order>myOrder(MyOrderReques myOrderReques){
        String token= myOrderReques.token;
        Customer customer=userService.getCustomerByToken(token);
        return orderRepository.findByEmail(customer.email);
    }
    public Order orderStatus(OrderStatusRequest orderStatusRequest){
        Customer customer=userService.getCustomerByToken(orderStatusRequest.token);

        Order order=orderRepository.findByOrderId(orderStatusRequest.orderId);
        if(order.email.equals(customer.email)==false||order==null){
            return null;
        }
        return order;
    }
}
