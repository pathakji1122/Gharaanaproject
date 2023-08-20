package com.beginnner.gharaana.Repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.beginnner.gharaana.Entity.*;

@Repository
public interface ExpertRepository extends MongoRepository<Expert,String> {
    Expert findOneByEmail(String email);
    Expert save(Expert expert);

    Expert findOneByPhoneNoAndEmail(String phoneNo, String email);
}