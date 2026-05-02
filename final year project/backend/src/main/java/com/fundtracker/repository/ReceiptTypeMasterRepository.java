package com.fundtracker.repository;

import com.fundtracker.model.ReceiptTypeMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ReceiptTypeMasterRepository extends MongoRepository<ReceiptTypeMaster, String> {
    boolean existsByReceiptTypeCode(String receiptTypeCode);
    boolean existsByReceiptTypeName(String receiptTypeName);
    List<ReceiptTypeMaster> findAllByOrderByReceiptTypeNameAsc();
    List<ReceiptTypeMaster> findByCatName(String catName);
}
