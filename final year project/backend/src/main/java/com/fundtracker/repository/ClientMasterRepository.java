package com.fundtracker.repository;

import com.fundtracker.model.ClientMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;
import java.util.List;

public interface ClientMasterRepository extends MongoRepository<ClientMaster, String> {
    Optional<ClientMaster> findByClientCode(String clientCode);
    Optional<ClientMaster> findByClientName(String clientName);
    boolean existsByClientCode(String clientCode);
    boolean existsByClientName(String clientName);
    List<ClientMaster> findAllByOrderByClientNameAsc();
}
