package com.beginnner.gharaana.Repo;

import com.beginnner.gharaana.Entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User,String > {
    User findOneByEmail(String email);
    User save(User user);
    User deleteByEmail(String email);
}
