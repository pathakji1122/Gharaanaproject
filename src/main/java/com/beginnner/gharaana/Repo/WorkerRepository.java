package com.beginnner.gharaana.Repo;
import com.beginnner.gharaana.Entity.Worker;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface WorkerRepository extends MongoRepository<Worker,String> {
    Worker findOneByEmail(String email);
    Worker save(Worker worker);

    Worker findOneByPhoneNoAndEmail(String phoneNo, String email);
}

