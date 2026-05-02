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
public class ExportInvoiceService {

    @Autowired private ExportInvoiceRepository exportInvoiceRepo;
    @Autowired private ExportBillOpenCloseRepository exportBillOCRepo;
    @Autowired private DbkIncentivesOpenCloseRepository dbkRepo;
    @Autowired private MeisIncentivesOpenCloseRepository meisRepo;

    public String generateDocNo() {
        long count = exportInvoiceRepo.count() + 1;
        return "EXP" + String.format("%05d", count);
    }

    public List<ExportInvoice> getAll() { return exportInvoiceRepo.findAll(); }

    public ExportInvoice getById(String id) {
        return exportInvoiceRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Export Invoice not found."));
    }

    public List<String> getOpenInvoiceNos() {
        return exportBillOCRepo.findByStatus("Open").stream()
                .map(ExportBillOpenClose::getInvoiceNo).toList();
    }

    public List<String> getOpenDbkInvoiceNos() {
        return dbkRepo.findByStatus("Open").stream()
                .map(DbkIncentivesOpenClose::getInvoiceNo).toList();
    }

    public List<String> getOpenMeisInvoiceNos() {
        return meisRepo.findByStatus("Open").stream()
                .map(MeisIncentivesOpenClose::getInvoiceNo).toList();
    }

    /**
     * Full INSERT logic from exportInvoice.vb:
     * 1. Duplicate check
     * 2. Insert export_invoice with status=Open
     * 3. Insert export_bill_openclose
     * 4. Insert dbk_incentives_openclose
     * 5. Insert meis_incentives_openclose
     */
    @Transactional
    public ExportInvoice save(ExportInvoice invoice) {
        if (exportInvoiceRepo.existsByInvoiceNo(invoice.getInvoiceNo())) {
            throw new DuplicateResourceException("Export Invoice No Already Exist.");
        }
        invoice.setEntryDate(LocalDate.now());
        invoice.setStatus("Open");
        invoice.setInrCurrencyType("INR");
        if (invoice.getIExpCode() == null || invoice.getIExpCode().isBlank()) {
            invoice.setIExpCode(generateDocNo());
        }
        ExportInvoice saved = exportInvoiceRepo.save(invoice);

        // export_bill_openclose
        exportBillOCRepo.save(new ExportBillOpenClose(null,
                invoice.getIExpCode(), invoice.getInvoiceNo(), invoice.getTotalPair(), "Open"));

        // dbk_incentives_openclose
        dbkRepo.save(new DbkIncentivesOpenClose(null,
                invoice.getIExpCode(), invoice.getInvoiceNo(), null, null, "Open"));

        // meis_incentives_openclose
        meisRepo.save(new MeisIncentivesOpenClose(null,
                invoice.getIExpCode(), invoice.getInvoiceNo(), null, null, "Open"));

        return saved;
    }
}
