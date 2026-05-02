package com.fundtracker.repository;

import com.fundtracker.model.PurchaseInvoice;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PurchaseInvoiceRepository extends MongoRepository<PurchaseInvoice, String> {
    boolean existsByInvoiceNoAndSupName(String invoiceNo, String supName);
    Optional<PurchaseInvoice> findByIPurCode(String iPurCode);
    long countByIPurCodeStartingWith(String prefix);
    long count();
    List<PurchaseInvoice> findByInvoiceDueDateBetween(LocalDate from, LocalDate to);
    List<PurchaseInvoice> findByInvoiceDueDateBefore(LocalDate date);
    List<PurchaseInvoice> findByEntryDateBetween(LocalDate from, LocalDate to);
    List<PurchaseInvoice> findBySupName(String supName);
    List<PurchaseInvoice> findByCompany(String company);
    List<PurchaseInvoice> findByCatName(String catName);
    List<PurchaseInvoice> findByEntryDateBetweenAndSupName(LocalDate from, LocalDate to, String supName);
    List<PurchaseInvoice> findByEntryDateBetweenAndCompany(LocalDate from, LocalDate to, String company);
    List<PurchaseInvoice> findByEntryDateBetweenAndCatName(LocalDate from, LocalDate to, String catName);
}
