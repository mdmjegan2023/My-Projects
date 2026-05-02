package com.fundtracker.repository;

import com.fundtracker.model.CurrencyMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface CurrencyMasterRepository extends MongoRepository<CurrencyMaster, String> {
    boolean existsByCurrCode(String currCode);
    boolean existsByCurrName(String currName);
    List<CurrencyMaster> findAllByOrderByCurrNameAsc();
}
