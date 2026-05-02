package com.fundtracker.repository;

import com.fundtracker.model.SupplierAdvance;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.time.LocalDate;
import java.util.List;

public interface SupplierAdvanceRepository extends MongoRepository<SupplierAdvance, String> {
    List<SupplierAdvance> findBySupName(String supName);
    List<SupplierAdvance> findByEntryDateBetween(LocalDate from, LocalDate to);
    List<SupplierAdvance> findByEntryDateBetweenAndSupName(LocalDate from, LocalDate to, String supName);
}
