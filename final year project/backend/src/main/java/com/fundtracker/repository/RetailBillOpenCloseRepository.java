package com.fundtracker.repository;

import com.fundtracker.model.RetailBillOpenClose;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface RetailBillOpenCloseRepository extends MongoRepository<RetailBillOpenClose, String> {
    Optional<RetailBillOpenClose> findByInvoiceNo(String invoiceNo);
    List<RetailBillOpenClose> findByStatus(String status);
}
