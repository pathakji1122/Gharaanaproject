package com.beginnner.gharaana.Repo;

import com.beginnner.gharaana.Entity.*;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
    Order save(Order order);

    Order findByOrderId(String orderId);

    List<Order> findByEmail(String email);
    Order deleteByOrderId(String orderId);
   List<Order>findByExpert(String email);
    List<Order> findByLocationAndExpertiseAndOrderStatus(Location location, Expertise expertise, OrderStatus orderStatus);
}
