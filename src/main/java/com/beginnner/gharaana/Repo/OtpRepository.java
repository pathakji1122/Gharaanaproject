package com.beginnner.gharaana.Repo;

import com.beginnner.gharaana.Entity.Otp;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpRepository extends MongoRepository<Otp, String> {
    Otp save(Otp otp);
    Otp findOneByOrderId(String OrderId);
}
