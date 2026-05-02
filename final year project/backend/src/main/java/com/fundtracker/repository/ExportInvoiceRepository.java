package com.fundtracker.repository;

import com.fundtracker.model.ExportInvoice;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExportInvoiceRepository extends MongoRepository<ExportInvoice, String> {
    boolean existsByInvoiceNo(String invoiceNo);
    Optional<ExportInvoice> findByIExpCode(String iExpCode);
    long count();
    List<ExportInvoice> findByStatus(String status);
    List<ExportInvoice> findByClientName(String clientName);
    List<ExportInvoice> findByEntryDateBetween(LocalDate from, LocalDate to);
    List<ExportInvoice> findByEntryDateBetweenAndClientName(LocalDate from, LocalDate to, String clientName);
    List<ExportInvoice> findByEntryDateBetweenAndStatus(LocalDate from, LocalDate to, String status);
}
