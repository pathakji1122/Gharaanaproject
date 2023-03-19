package com.beginnner.gharaana.Repo;

import com.beginnner.gharaana.Entity.AgentInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AgentInfoRepository extends MongoRepository<AgentInfo,String> {
    AgentInfo save(AgentInfo agentInfo);
    AgentInfo findOneByEmail(String email);
}
