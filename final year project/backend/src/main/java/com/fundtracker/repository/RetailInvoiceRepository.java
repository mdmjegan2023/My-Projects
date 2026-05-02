package com.fundtracker.repository;

import com.fundtracker.model.RetailInvoice;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RetailInvoiceRepository extends MongoRepository<RetailInvoice, String> {
    boolean existsByInvoiceNo(String invoiceNo);
    Optional<RetailInvoice> findByIRtlCode(String iRtlCode);
    long count();
    List<RetailInvoice> findByStatus(String status);
    List<RetailInvoice> findByClientName(String clientName);
    List<RetailInvoice> findByEntryDateBetween(LocalDate from, LocalDate to);
    List<RetailInvoice> findByEntryDateBetweenAndClientName(LocalDate from, LocalDate to, String clientName);
    List<RetailInvoice> findByEntryDateBetweenAndStatus(LocalDate from, LocalDate to, String status);
}
