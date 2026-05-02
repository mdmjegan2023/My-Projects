package com.fundtracker.repository;

import com.fundtracker.model.SupplierMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;
import java.util.List;

public interface SupplierMasterRepository extends MongoRepository<SupplierMaster, String> {
    Optional<SupplierMaster> findBySupCode(String supCode);
    Optional<SupplierMaster> findBySupName(String supName);
    boolean existsBySupCode(String supCode);
    boolean existsBySupName(String supName);
    List<SupplierMaster> findAllByOrderBySupNameAsc();
    List<SupplierMaster> findByCatName(String catName);
}
