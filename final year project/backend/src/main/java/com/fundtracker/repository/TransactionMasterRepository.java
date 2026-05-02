package com.fundtracker.repository;

import com.fundtracker.model.TransactionMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface TransactionMasterRepository extends MongoRepository<TransactionMaster, String> {
    boolean existsByTransCode(String transCode);
    boolean existsByTransName(String transName);
    List<TransactionMaster> findAllByOrderByTransNameAsc();
    List<TransactionMaster> findByCatName(String catName);
}
