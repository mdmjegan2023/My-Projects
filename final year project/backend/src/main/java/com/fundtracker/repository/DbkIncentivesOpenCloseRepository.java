package com.fundtracker.repository;

import com.fundtracker.model.DbkIncentivesOpenClose;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface DbkIncentivesOpenCloseRepository extends MongoRepository<DbkIncentivesOpenClose, String> {
    Optional<DbkIncentivesOpenClose> findByInvoiceNo(String invoiceNo);
    List<DbkIncentivesOpenClose> findByStatus(String status);
}
