package com.fundtracker.repository;

import com.fundtracker.model.CompanyMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;
import java.util.List;

public interface CompanyMasterRepository extends MongoRepository<CompanyMaster, String> {
    Optional<CompanyMaster> findByCompCode(String compCode);
    Optional<CompanyMaster> findByCompName(String compName);
    boolean existsByCompCode(String compCode);
    boolean existsByCompName(String compName);
    List<CompanyMaster> findAllByOrderByCompNameAsc();
}
