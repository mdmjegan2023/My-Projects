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
public class DebitNoteService {

    @Autowired private DebitNoteRepository debitNoteRepo;
    @Autowired private PurchaseDebitOpenCloseRepository purchaseDebitOCRepo;
    @Autowired private SupplierCreditOpenCloseRepository supplierCreditRepo;
    @Autowired private SupplierAdvanceOpenCloseRepository supplierAdvanceRepo;
    @Autowired private SupplierAdvanceRepository supplierAdvanceEntryRepo;
    @Autowired private SupplierLedgerRepository supplierLedgerRepo;
    @Autowired private PurchaseInvoiceRepository purchaseInvoiceRepo;

    public String generateDocNo() {
        long count = debitNoteRepo.count() + 1;
        return "DBT" + String.format("%05d", count);
    }

    public List<DebitNote> getAll() { return debitNoteRepo.findAll(); }

    public DebitNote getById(String id) {
        return debitNoteRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Debit Note not found."));
    }

    /** Returns invoice numbers for a given supplier that have existing purchase invoices */
    public List<String> getInvoiceNosBySupplier(String supName) {
        return purchaseInvoiceRepo.findBySupName(supName)
                .stream().map(PurchaseInvoice::getInvoiceNo).toList();
    }

    /**
     * Full INSERT logic from debitNote.vb:
     * 1. Validate supDebit <= invoiceAmount
     * 2. Insert purchase_debit_note
     * 3. Update purchase_debit_openclose (add supDebit)
     * 4. Update supplier_credit_openclose (subtract supDebit)
     * 5. If supplier has advance > 0: increase advance + insert supplier_advance entry
     * 6. Insert supplier_ledger entry
     */
    @Transactional
    public DebitNote save(DebitNote debitNote) {
        if (debitNote.getSupDebit() > debitNote.getInvoiceAmount()) {
            throw new BusinessException("Debit amount cannot exceed invoice amount.");
        }
        debitNote.setEntryDate(LocalDate.now());
        if (debitNote.getIDbtCode() == null || debitNote.getIDbtCode().isBlank()) {
            debitNote.setIDbtCode(generateDocNo());
        }
        DebitNote saved = debitNoteRepo.save(debitNote);

        // Update purchase_debit_openclose
        purchaseDebitOCRepo.findByInvoiceNoAndSupName(debitNote.getInvoiceNo(), debitNote.getSupName())
                .ifPresent(oc -> {
                    oc.setInvoiceAmount(debitNote.getInvoiceAmount());
                    oc.setSupDebit(oc.getSupDebit() + debitNote.getSupDebit());
                    purchaseDebitOCRepo.save(oc);
                });

        // Update supplier_credit_openclose (subtract debit)
        supplierCreditRepo.findBySupNameAndSupAddressAndSupPhoneAndSupEmail(
                debitNote.getSupName(), debitNote.getSupAddress(),
                debitNote.getSupPhone(), debitNote.getSupEmail())
                .ifPresent(credit -> {
                    credit.setSupCredit(credit.getSupCredit() - debitNote.getSupDebit());
                    supplierCreditRepo.save(credit);
                });

        // If supplier has advance > 0, increase it and log entry
        supplierAdvanceRepo.findBySupNameAndSupAddressAndSupPhoneAndSupEmail(
                debitNote.getSupName(), debitNote.getSupAddress(),
                debitNote.getSupPhone(), debitNote.getSupEmail())
                .ifPresent(adv -> {
                    if (adv.getSupAdvance() > 0) {
                        adv.setSupAdvance(adv.getSupAdvance() + debitNote.getSupDebit());
                        supplierAdvanceRepo.save(adv);
                        SupplierAdvance advEntry = new SupplierAdvance(null,
                                debitNote.getIDbtCode(), LocalDate.now(),
                                debitNote.getSupName(), debitNote.getSupAddress(),
                                debitNote.getSupPhone(), debitNote.getSupEmail(),
                                debitNote.getSupDebit(), null, null, null, null, null,
                                debitNote.getRemarks());
                        supplierAdvanceEntryRepo.save(advEntry);
                    }
                });

        // Insert supplier_ledger entry
        double clsBal = supplierCreditRepo.findBySupName(debitNote.getSupName())
                .map(SupplierCreditOpenClose::getSupCredit).orElse(0.0);
        SupplierLedger ledger = new SupplierLedger(null,
                LocalDate.now(), debitNote.getInvoiceDate(), null,
                debitNote.getCompany(), debitNote.getSupName(),
                null, null, null, null,
                debitNote.getSupDebit(), 0.0, clsBal, debitNote.getRemarks());
        supplierLedgerRepo.save(ledger);

        return saved;
    }
}
