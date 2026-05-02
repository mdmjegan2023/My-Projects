package com.fundtracker.repository;

import com.fundtracker.model.MeisIncentivesOpenClose;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface MeisIncentivesOpenCloseRepository extends MongoRepository<MeisIncentivesOpenClose, String> {
    Optional<MeisIncentivesOpenClose> findByInvoiceNo(String invoiceNo);
    List<MeisIncentivesOpenClose> findByStatus(String status);
}
