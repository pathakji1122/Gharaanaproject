package com.beginnner.gharaana.Repo;
import com.beginnner.gharaana.Entity.*;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends MongoRepository<Customer,String > {
    Customer findOneByEmail(String email);
    Customer findOneByPhoneNoAndEmail(String phoneNo,String email);
    Customer save(Customer customer);
    Customer deleteByEmail(String email);
}
