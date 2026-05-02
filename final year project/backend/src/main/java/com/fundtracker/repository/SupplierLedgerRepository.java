package com.fundtracker.repository;

import com.fundtracker.model.SupplierLedger;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.time.LocalDate;
import java.util.List;

public interface SupplierLedgerRepository extends MongoRepository<SupplierLedger, String> {
    List<SupplierLedger> findBySupNameOrderByEntryDateAsc(String supName);
    List<SupplierLedger> findByEntryDateBetweenAndSupName(LocalDate from, LocalDate to, String supName);
    List<String> findDistinctSupNameBy();
}
