package com.fundtracker.repository;

import com.fundtracker.model.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends MongoRepository<Payment, String> {
    Optional<Payment> findByPPayCode(String pPayCode);
    long count();
    List<Payment> findBySupName(String supName);
    List<Payment> findByPaymentName(String paymentName);
    List<Payment> findByPaymentType(String paymentType);
    List<Payment> findByTransMode(String transMode);
    List<Payment> findByBankName(String bankName);
    List<Payment> findByAccNo(String accNo);
    List<Payment> findByAccType(String accType);
    List<Payment> findByEntryDateBetween(LocalDate from, LocalDate to);
    List<Payment> findByEntryDateBetweenAndPaymentDateBetween(LocalDate eFrom, LocalDate eTo, LocalDate pFrom, LocalDate pTo);
    List<Payment> findByEntryDateBetweenAndPaymentName(LocalDate from, LocalDate to, String paymentName);
    List<Payment> findByEntryDateBetweenAndPaymentType(LocalDate from, LocalDate to, String paymentType);
    List<Payment> findByEntryDateBetweenAndSupName(LocalDate from, LocalDate to, String supName);
    List<Payment> findByEntryDateBetweenAndTransMode(LocalDate from, LocalDate to, String transMode);
    List<Payment> findByEntryDateBetweenAndBankName(LocalDate from, LocalDate to, String bankName);
    List<Payment> findByEntryDateBetweenAndAccNo(LocalDate from, LocalDate to, String accNo);
    List<Payment> findByEntryDateBetweenAndAccType(LocalDate from, LocalDate to, String accType);
}
