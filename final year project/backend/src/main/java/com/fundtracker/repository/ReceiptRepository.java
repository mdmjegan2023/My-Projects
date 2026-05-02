package com.fundtracker.repository;

import com.fundtracker.model.Receipt;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReceiptRepository extends MongoRepository<Receipt, String> {
    Optional<Receipt> findByDocNo(String docNo);
    long count();
    List<Receipt> findByEntryDateBetween(LocalDate from, LocalDate to);
    List<Receipt> findByReceiptName(String receiptName);
    List<Receipt> findByReceiptType(String receiptType);
    List<Receipt> findByCompany(String company);
    List<Receipt> findByTransMode(String transMode);
    List<Receipt> findByBankName(String bankName);
    List<Receipt> findByAccNo(String accNo);
    List<Receipt> findByAccType(String accType);
    List<Receipt> findByEntryDateBetweenAndReceiptName(LocalDate from, LocalDate to, String receiptName);
    List<Receipt> findByEntryDateBetweenAndReceiptType(LocalDate from, LocalDate to, String receiptType);
    List<Receipt> findByEntryDateBetweenAndCompany(LocalDate from, LocalDate to, String company);
    List<Receipt> findByEntryDateBetweenAndTransMode(LocalDate from, LocalDate to, String transMode);
    List<Receipt> findByEntryDateBetweenAndBankName(LocalDate from, LocalDate to, String bankName);
    List<Receipt> findByEntryDateBetweenAndAccNo(LocalDate from, LocalDate to, String accNo);
    List<Receipt> findByEntryDateBetweenAndAccType(LocalDate from, LocalDate to, String accType);
}
