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
public class RetailReceiptService {

    @Autowired private RetailReceiptRepository retailReceiptRepo;
    @Autowired private RetailBillOpenCloseRepository retailBillOCRepo;
    @Autowired private BankBalanceRepository bankBalanceRepo;
    @Autowired private ReceiptService receiptService;

    public String generateDocNo() {
        long count = retailReceiptRepo.count() + 1;
        return "RRT" + String.format("%05d", count);
    }

    public List<RetailReceipt> getAll() { return retailReceiptRepo.findAll(); }

    public RetailReceipt getById(String id) {
        return retailReceiptRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Retail Receipt not found."));
    }

    /**
     * Full INSERT logic from retailReceipt.vb:
     * 1. Check open invoice exists and remaining pairs > receipt pairs
     * 2. Insert retail_receipt with status=Open
     * 3. Update retail_bill_openclose (deduct pairs; close if 0)
     * 4. Upsert bank_balance (add inrAmount)
     */
    @Transactional
    public RetailReceipt save(RetailReceipt receipt) {
        RetailBillOpenClose oc = retailBillOCRepo.findByInvoiceNo(receipt.getInvoiceNo())
                .orElseThrow(() -> new BusinessException("Invoice not found in open bills."));
        if (oc.getTotalPair() <= receipt.getTotalPair()) {
            throw new BusinessException("Receipt pairs must be less than remaining open pairs (" + oc.getTotalPair() + ").");
        }

        receipt.setEntryDate(LocalDate.now());
        receipt.setStatus("Open");
        receipt.setInrCurrencyType("INR");
        if (receipt.getRRtlCode() == null || receipt.getRRtlCode().isBlank()) {
            receipt.setRRtlCode(generateDocNo());
        }
        RetailReceipt saved = retailReceiptRepo.save(receipt);

        // Update retail_bill_openclose
        oc.setRRtlCode(receipt.getRRtlCode());
        oc.setTotalPair(oc.getTotalPair() - receipt.getTotalPair());
        if (oc.getTotalPair() == 0) oc.setStatus("Closed");
        retailBillOCRepo.save(oc);

        // Upsert bank_balance
        receiptService.upsertBankBalance(receipt.getBankName(), receipt.getAccNo(), receipt.getAccType(),
                receipt.getBankBranch(), receipt.getIfscCode(), receipt.getSwiftCode(),
                receipt.getInrAmount(), true);

        return saved;
    }
}
