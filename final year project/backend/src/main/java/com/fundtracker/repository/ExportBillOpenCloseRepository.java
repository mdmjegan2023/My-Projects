package com.fundtracker.repository;

import com.fundtracker.model.ExportBillOpenClose;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface ExportBillOpenCloseRepository extends MongoRepository<ExportBillOpenClose, String> {
    Optional<ExportBillOpenClose> findByInvoiceNo(String invoiceNo);
    List<ExportBillOpenClose> findByStatus(String status);
}
