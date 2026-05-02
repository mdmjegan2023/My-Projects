package com.fundtracker.service;

import com.fundtracker.exception.BusinessException;
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
public class PurchaseInvoiceService {

    @Autowired private PurchaseInvoiceRepository purchaseRepo;
    @Autowired private PurchaseDebitOpenCloseRepository debitOpenCloseRepo;
    @Autowired private SupplierCreditOpenCloseRepository supplierCreditRepo;
    @Autowired private SupplierAdvanceOpenCloseRepository supplierAdvanceRepo;
    @Autowired private SupplierLedgerRepository supplierLedgerRepo;

    /** AUTO_DOCNO logic: count existing + 1, padded */
    public String generateDocNo() {
        long count = purchaseRepo.count() + 1;
        return "PUR" + String.format("%05d", count);
    }

    public List<PurchaseInvoice> getAll() { return purchaseRepo.findAll(); }

    public PurchaseInvoice getById(String id) {
        return purchaseRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase Invoice not found."));
    }

    public List<PurchaseInvoice> getPaymentAlerts() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        return purchaseRepo.findByInvoiceDueDateBetween(today, tomorrow);
    }

    public List<PurchaseInvoice> getOverdueBills() {
        return purchaseRepo.findByInvoiceDueDateBefore(LocalDate.now());
    }

    /**
     * Full INSERT logic from purchaseInvoice.vb:
     * 1. Check invoice+supplier duplicate
     * 2. Insert purchase_invoice
     * 3. Insert purchase_debit_openclose
     * 4. Upsert supplier_credit_openclose (add invoice amount to credit)
     * 5. Adjust supplier_advance_openclose if advance exists
     * 6. Insert supplier_ledger entry
     */
    @Transactional
    public PurchaseInvoice save(PurchaseInvoice invoice) {
        // Duplicate check
        if (purchaseRepo.existsByInvoiceNoAndSupName(invoice.getInvoiceNo(), invoice.getSupName())) {
            throw new DuplicateResourceException("Bill No Already Exist For Selected Supplier.");
        }

        invoice.setEntryDate(LocalDate.now());
        if (invoice.getIPurCode() == null || invoice.getIPurCode().isBlank()) {
            invoice.setIPurCode(generateDocNo());
        }
        PurchaseInvoice saved = purchaseRepo.save(invoice);

        // Insert purchase_debit_openclose
        PurchaseDebitOpenClose debitOC = new PurchaseDebitOpenClose(null,
                invoice.getInvoiceNo(), invoice.getSupName(), invoice.getSupAddress(),
                invoice.getSupPhone(), invoice.getSupEmail(), invoice.getInvoiceAmount(), 0.0);
        debitOpenCloseRepo.save(debitOC);

        // Upsert supplier_credit_openclose
        boolean creditExists = supplierCreditRepo.existsBySupNameAndSupAddressAndSupPhoneAndSupEmail(
                invoice.getSupName(), invoice.getSupAddress(), invoice.getSupPhone(), invoice.getSupEmail());
        if (!creditExists) {
            SupplierCreditOpenClose credit = new SupplierCreditOpenClose(null,
                    invoice.getSupName(), invoice.getSupAddress(),
                    invoice.getSupPhone(), invoice.getSupEmail(), invoice.getInvoiceAmount());
            supplierCreditRepo.save(credit);
        } else {
            SupplierCreditOpenClose credit = supplierCreditRepo
                    .findBySupNameAndSupAddressAndSupPhoneAndSupEmail(
                            invoice.getSupName(), invoice.getSupAddress(),
                            invoice.getSupPhone(), invoice.getSupEmail())
                    .orElseThrow();
            credit.setSupCredit(credit.getSupCredit() + invoice.getInvoiceAmount());
            supplierCreditRepo.save(credit);
        }

        // Adjust supplier_advance_openclose
        supplierAdvanceRepo.findBySupNameAndSupAddressAndSupPhoneAndSupEmail(
                invoice.getSupName(), invoice.getSupAddress(),
                invoice.getSupPhone(), invoice.getSupEmail())
                .ifPresent(adv -> {
                    double deductAmt = (adv.getSupAdvance() >= invoice.getInvoiceAmount())
                            ? invoice.getInvoiceAmount() : invoice.getSupAdvance();
                    adv.setSupAdvance(adv.getSupAdvance() - deductAmt);
                    supplierAdvanceRepo.save(adv);
                });

        // Insert supplier_ledger
        double clsBal = supplierCreditRepo.findBySupName(invoice.getSupName())
                .map(SupplierCreditOpenClose::getSupCredit).orElse(invoice.getInvoiceAmount());
        SupplierLedger ledger = new SupplierLedger(null,
                LocalDate.now(), invoice.getInvoiceDate(), null,
                invoice.getCompany(), invoice.getSupName(),
                null, null, null, null,
                0.0, invoice.getInvoiceAmount(), clsBal, invoice.getRemarks());
        supplierLedgerRepo.save(ledger);

        return saved;
    }
}
