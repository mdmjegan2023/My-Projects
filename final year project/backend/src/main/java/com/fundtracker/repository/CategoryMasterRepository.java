package com.fundtracker.repository;

import com.fundtracker.model.CategoryMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;
import java.util.List;

public interface CategoryMasterRepository extends MongoRepository<CategoryMaster, String> {
    Optional<CategoryMaster> findByCatCode(String catCode);
    Optional<CategoryMaster> findByCatName(String catName);
    boolean existsByCatCode(String catCode);
    boolean existsByCatName(String catName);
    List<CategoryMaster> findAllByOrderByCatNameAsc();
}
