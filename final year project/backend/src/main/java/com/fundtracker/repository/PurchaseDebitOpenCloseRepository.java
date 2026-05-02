package com.fundtracker.repository;

import com.fundtracker.model.PurchaseDebitOpenClose;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface PurchaseDebitOpenCloseRepository extends MongoRepository<PurchaseDebitOpenClose, String> {
    Optional<PurchaseDebitOpenClose> findByInvoiceNoAndSupName(String invoiceNo, String supName);
}
