package com.fundtracker.repository;

import com.fundtracker.model.RetailReceipt;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RetailReceiptRepository extends MongoRepository<RetailReceipt, String> {
    Optional<RetailReceipt> findByRRtlCode(String rRtlCode);
    long count();
    List<RetailReceipt> findByClientName(String clientName);
    List<RetailReceipt> findByEntryDateBetween(LocalDate from, LocalDate to);
    List<RetailReceipt> findByEntryDateBetweenAndClientName(LocalDate from, LocalDate to, String clientName);
    List<RetailReceipt> findByEntryDateBetweenAndTransMode(LocalDate from, LocalDate to, String transMode);
    List<RetailReceipt> findByEntryDateBetweenAndBankName(LocalDate from, LocalDate to, String bankName);
    List<RetailReceipt> findByEntryDateBetweenAndAccNo(LocalDate from, LocalDate to, String accNo);
}
