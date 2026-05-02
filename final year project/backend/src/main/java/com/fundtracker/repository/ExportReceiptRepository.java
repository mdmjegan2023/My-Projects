package com.fundtracker.repository;

import com.fundtracker.model.ExportReceipt;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExportReceiptRepository extends MongoRepository<ExportReceipt, String> {
    Optional<ExportReceipt> findByRExpCode(String rExpCode);
    long count();
    List<ExportReceipt> findByClientName(String clientName);
    List<ExportReceipt> findByEntryDateBetween(LocalDate from, LocalDate to);
    List<ExportReceipt> findByEntryDateBetweenAndClientName(LocalDate from, LocalDate to, String clientName);
    List<ExportReceipt> findByEntryDateBetweenAndTransMode(LocalDate from, LocalDate to, String transMode);
    List<ExportReceipt> findByEntryDateBetweenAndBankName(LocalDate from, LocalDate to, String bankName);
}
