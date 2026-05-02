package com.fundtracker.service;

import com.fundtracker.exception.DuplicateResourceException;
import com.fundtracker.exception.ResourceNotFoundException;
import com.fundtracker.model.*;
import com.fundtracker.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class RetailInvoiceService {

    @Autowired private RetailInvoiceRepository retailInvoiceRepo;
    @Autowired private RetailBillOpenCloseRepository retailBillOCRepo;

    public String generateDocNo() {
        long count = retailInvoiceRepo.count() + 1;
        return "RTL" + String.format("%05d", count);
    }

    public List<RetailInvoice> getAll() { return retailInvoiceRepo.findAll(); }

    public RetailInvoice getById(String id) {
        return retailInvoiceRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Retail Invoice not found."));
    }

    public List<String> getOpenInvoiceNos() {
        return retailBillOCRepo.findByStatus("Open").stream()
                .map(RetailBillOpenClose::getInvoiceNo).toList();
    }

    /**
     * Full INSERT logic from retailInvoice.vb:
     * 1. Duplicate check
     * 2. Insert retail_invoice with status=Open
     * 3. Insert retail_bill_openclose
     */
    @Transactional
    public RetailInvoice save(RetailInvoice invoice) {
        if (retailInvoiceRepo.existsByInvoiceNo(invoice.getInvoiceNo())) {
            throw new DuplicateResourceException("Retail Invoice No Already Exist.");
        }
        invoice.setEntryDate(LocalDate.now());
        invoice.setStatus("Open");
        invoice.setInrCurrencyType("INR");
        if (invoice.getIRtlCode() == null || invoice.getIRtlCode().isBlank()) {
            invoice.setIRtlCode(generateDocNo());
        }
        RetailInvoice saved = retailInvoiceRepo.save(invoice);

        retailBillOCRepo.save(new RetailBillOpenClose(null,
                invoice.getIRtlCode(), invoice.getInvoiceNo(), invoice.getTotalPair(), "Open"));

        return saved;
    }
}
