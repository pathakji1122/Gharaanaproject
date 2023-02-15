package com.beginnner.gharaana.Repo;
import com.beginnner.gharaana.Entity.Expertise;
import com.beginnner.gharaana.Entity.Location;
import com.beginnner.gharaana.Entity.User;
import com.beginnner.gharaana.Entity.Worker;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Locale;


public interface WorkerRepository extends MongoRepository<Worker,String> {
    Worker findOneByEmail(String email);
    Worker save(Worker worker);

    Worker findOneByPhoneNoAndEmail(String phoneNo, String email);
}

