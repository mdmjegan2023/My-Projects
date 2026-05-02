package com.fundtracker.service;

import com.fundtracker.exception.ResourceNotFoundException;
import com.fundtracker.model.*;
import com.fundtracker.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReceiptService {

    @Autowired private ReceiptRepository receiptRepo;
    @Autowired private BankBalanceRepository bankBalanceRepo;

    public String generateDocNo() {
        long count = receiptRepo.count() + 1;
        return "RCP" + String.format("%05d", count);
    }

    public List<Receipt> getAll() { return receiptRepo.findAll(); }

    public Receipt getById(String id) {
        return receiptRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Receipt not found."));
    }

    /**
     * Full INSERT logic from receiptsForm.vb:
     * 1. Insert receipt
     * 2. Upsert bank_balance (add amount)
     */
    @Transactional
    public Receipt save(Receipt receipt) {
        receipt.setEntryDate(LocalDate.now());
        if (receipt.getDocNo() == null || receipt.getDocNo().isBlank()) {
            receipt.setDocNo(generateDocNo());
        }
        Receipt saved = receiptRepo.save(receipt);

        // Upsert bank_balance
        upsertBankBalance(receipt.getBankName(), receipt.getAccNo(), receipt.getAccType(),
                receipt.getBankBranch(), receipt.getIfscCode(), receipt.getSwiftCode(),
                receipt.getAmount(), true);

        return saved;
    }

    void upsertBankBalance(String bankName, String accNo, String accType,
                           String bankBranch, String ifscCode, String swiftCode,
                           Double amount, boolean add) {
        boolean exists = bankBalanceRepo.existsByBankNameAndAccNoAndAccType(bankName, accNo, accType);
        if (!exists) {
            BankBalance bb = new BankBalance(null, bankName, accNo, accType,
                    bankBranch, ifscCode, swiftCode, add ? amount : -amount);
            bankBalanceRepo.save(bb);
        } else {
            BankBalance bb = bankBalanceRepo
                    .findByBankNameAndAccNoAndAccType(bankName, accNo, accType).orElseThrow();
            bb.setBalance(add ? bb.getBalance() + amount : bb.getBalance() - amount);
            bankBalanceRepo.save(bb);
        }
    }
}
