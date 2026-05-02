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
public class ExportReceiptService {

    @Autowired private ExportReceiptRepository exportReceiptRepo;
    @Autowired private ExportBillOpenCloseRepository exportBillOCRepo;
    @Autowired private ReceiptService receiptService;

    public String generateDocNo() {
        long count = exportReceiptRepo.count() + 1;
        return "REX" + String.format("%05d", count);
    }

    public List<ExportReceipt> getAll() { return exportReceiptRepo.findAll(); }

    public ExportReceipt getById(String id) {
        return exportReceiptRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Export Receipt not found."));
    }

    /**
     * Full INSERT logic from exportReceipt.vb:
     * 1. Check open export invoice and remaining pairs > receipt pairs
     * 2. Insert export_receipt
     * 3. Update export_bill_openclose (deduct pairs; close if 0)
     * 4. Upsert bank_balance (add inrAmount)
     */
    @Transactional
    public ExportReceipt save(ExportReceipt receipt) {
        ExportBillOpenClose oc = exportBillOCRepo.findByInvoiceNo(receipt.getInvoiceNo())
                .orElseThrow(() -> new BusinessException("Invoice not found in open export bills."));
        if (oc.getTotalPair() <= receipt.getTotalPair()) {
            throw new BusinessException("Receipt pairs must be less than remaining open pairs (" + oc.getTotalPair() + ").");
        }

        receipt.setEntryDate(LocalDate.now());
        receipt.setStatus("Open");
        receipt.setInrCurrencyType("INR");
        if (receipt.getRExpCode() == null || receipt.getRExpCode().isBlank()) {
            receipt.setRExpCode(generateDocNo());
        }
        ExportReceipt saved = exportReceiptRepo.save(receipt);

        // Update export_bill_openclose
        oc.setRExpCode(receipt.getRExpCode());
        oc.setTotalPair(oc.getTotalPair() - receipt.getTotalPair());
        if (oc.getTotalPair() == 0) oc.setStatus("Closed");
        exportBillOCRepo.save(oc);

        // Upsert bank_balance
        receiptService.upsertBankBalance(receipt.getBankName(), receipt.getAccNo(), receipt.getAccType(),
                receipt.getBankBranch(), receipt.getIfscCode(), receipt.getSwiftCode(),
                receipt.getInrAmount(), true);

        return saved;
    }
}
