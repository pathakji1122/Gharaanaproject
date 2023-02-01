package com.beginnner.gharaana.Repo;

import com.beginnner.gharaana.Entity.Expertise;
import com.beginnner.gharaana.Entity.Location;
import com.beginnner.gharaana.Entity.Order;
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository

public interface OrderRepository extends MongoRepository<Order,String> {
    Order save(Order order);
     long count();
     Order findByOrderId(String orderId);
     List<Order>findByEmail(String email);
    List<Order> findByLocationAndExpertise(Location location, Expertise expertise);
}
