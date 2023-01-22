package com.beginnner.gharaana.Repo;

import com.beginnner.gharaana.Entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends MongoRepository<Customer,String > {
    Customer findOneByEmail(String email);
    Customer save(Customer customer);
}
