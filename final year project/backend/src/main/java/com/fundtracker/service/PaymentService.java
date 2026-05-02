package com.fundtracker.service;

import com.fundtracker.exception.BusinessException;
import com.fundtracker.exception.ResourceNotFoundException;
import com.fundtracker.model.*;
import com.fundtracker.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class PaymentService {

    @Autowired private PaymentRepository paymentRepo;
    @Autowired private BankBalanceRepository bankBalanceRepo;
    @Autowired private SupplierCreditOpenCloseRepository supplierCreditRepo;
    @Autowired private SupplierLedgerRepository supplierLedgerRepo;

    public String generateDocNo() {
        long count = paymentRepo.count() + 1;
        return "PAY" + String.format("%05d", count);
    }

    public List<Payment> getAll() { return paymentRepo.findAll(); }

    public Payment getById(String id) {
        return paymentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found."));
    }

    /**
     * Full INSERT logic from paymentsForm.vb:
     * 1. Check bank balance >= paidAmount
     * 2. Check supplier credit >= paidAmount
     * 3. Insert payment
     * 4. Update supplier_credit_openclose (subtract paidAmount)
     * 5. Update bank_balance (subtract paidAmount)
     * 6. Insert supplier_ledger entry
     */
    @Transactional
    public Payment save(Payment payment) {
        // Check bank balance
        BankBalance bankBal = bankBalanceRepo
                .findByBankNameAndAccNoAndAccType(payment.getBankName(), payment.getAccNo(), payment.getAccType())
                .orElseThrow(() -> new BusinessException("Bank account not found or has no balance."));
        if (bankBal.getBalance() < payment.getPaidAmount()) {
            throw new BusinessException("Insufficient bank balance. Available: " + bankBal.getBalance());
        }

        // Check supplier credit
        SupplierCreditOpenClose supCredit = supplierCreditRepo.findBySupName(payment.getSupName())
                .orElseThrow(() -> new BusinessException("No credit balance found for supplier: " + payment.getSupName()));
        if (supCredit.getSupCredit() < payment.getPaidAmount()) {
            throw new BusinessException("Payment amount exceeds supplier credit balance: " + supCredit.getSupCredit());
        }

        payment.setEntryDate(LocalDate.now());
        if (payment.getPPayCode() == null || payment.getPPayCode().isBlank()) {
            payment.setPPayCode(generateDocNo());
        }
        payment.setSupCredit(supCredit.getSupCredit());
        Payment saved = paymentRepo.save(payment);

        // Update supplier_credit_openclose
        supCredit.setSupCredit(supCredit.getSupCredit() - payment.getPaidAmount());
        supplierCreditRepo.save(supCredit);

        // Update bank_balance
        bankBal.setBalance(bankBal.getBalance() - payment.getPaidAmount());
        bankBalanceRepo.save(bankBal);

        // Insert supplier_ledger
        double clsBal = supCredit.getSupCredit(); // already updated
        SupplierLedger ledger = new SupplierLedger(null,
                LocalDate.now(), null, payment.getPaymentDate(),
                null, payment.getSupName(),
                payment.getTransMode(), payment.getDescription(),
                payment.getBankName(), payment.getAccNo(),
                payment.getPaidAmount(), 0.0, clsBal, payment.getRemarks());
        supplierLedgerRepo.save(ledger);

        return saved;
    }
}
