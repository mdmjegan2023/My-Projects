package com.fundtracker.repository;

import com.fundtracker.model.ReceiptNameMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ReceiptNameMasterRepository extends MongoRepository<ReceiptNameMaster, String> {
    boolean existsByReceiptCode(String receiptCode);
    boolean existsByReceiptName(String receiptName);
    List<ReceiptNameMaster> findAllByOrderByReceiptNameAsc();
}
