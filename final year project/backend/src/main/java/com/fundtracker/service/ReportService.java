package com.fundtracker.service;

import com.fundtracker.dto.ReportFilterRequest;
import com.fundtracker.model.*;
import com.fundtracker.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportService {

    @Autowired private PaymentRepository paymentRepo;
    @Autowired private ReceiptRepository receiptRepo;
    @Autowired private PurchaseInvoiceRepository purchaseInvoiceRepo;
    @Autowired private ExportInvoiceRepository exportInvoiceRepo;
    @Autowired private RetailInvoiceRepository retailInvoiceRepo;
    @Autowired private RetailReceiptRepository retailReceiptRepo;
    @Autowired private ExportReceiptRepository exportReceiptRepo;
    @Autowired private DebitNoteRepository debitNoteRepo;
    @Autowired private SalexportIncentivesRepository incentivesRepo;
    @Autowired private SupplierAdvanceRepository supplierAdvanceRepo;
    @Autowired private SupplierCreditOpenCloseRepository supplierCreditRepo;
    @Autowired private SupplierMasterRepository supplierMasterRepo;
    @Autowired private SupplierLedgerRepository supplierLedgerRepo;
    @Autowired private BankMasterRepository bankMasterRepo;
    @Autowired private CategoryMasterRepository categoryMasterRepo;
    @Autowired private ClientMasterRepository clientMasterRepo;
    @Autowired private CompanyMasterRepository companyMasterRepo;
    @Autowired private CurrencyMasterRepository currencyMasterRepo;
    @Autowired private ReceiptNameMasterRepository receiptNameMasterRepo;
    @Autowired private ReceiptTypeMasterRepository receiptTypeMasterRepo;
    @Autowired private TransactionMasterRepository transactionMasterRepo;

    // ─── PAYMENTS REPORT ────────────────────────────────────────────────────────
    public List<Payment> paymentsReport(ReportFilterRequest f) {
        if (f.getFromDate() == null) return paymentRepo.findAll();
        if (f.getFilterType() == null || f.getFilterType().isBlank())
            return paymentRepo.findByEntryDateBetween(f.getFromDate(), f.getToDate());
        return switch (f.getFilterType()) {
            case "paymentDate" -> paymentRepo.findByEntryDateBetweenAndPaymentDateBetween(
                    f.getFromDate(), f.getToDate(), f.getSecondFromDate(), f.getSecondToDate());
            case "paymentName" -> paymentRepo.findByEntryDateBetweenAndPaymentName(
                    f.getFromDate(), f.getToDate(), f.getFilterValue());
            case "paymentType" -> paymentRepo.findByEntryDateBetweenAndPaymentType(
                    f.getFromDate(), f.getToDate(), f.getFilterValue());
            case "supName"     -> paymentRepo.findByEntryDateBetweenAndSupName(
                    f.getFromDate(), f.getToDate(), f.getFilterValue());
            case "transMode"   -> paymentRepo.findByEntryDateBetweenAndTransMode(
                    f.getFromDate(), f.getToDate(), f.getFilterValue());
            case "bankName"    -> paymentRepo.findByEntryDateBetweenAndBankName(
                    f.getFromDate(), f.getToDate(), f.getFilterValue());
            case "accNo"       -> paymentRepo.findByEntryDateBetweenAndAccNo(
                    f.getFromDate(), f.getToDate(), f.getFilterValue());
            case "accType"     -> paymentRepo.findByEntryDateBetweenAndAccType(
                    f.getFromDate(), f.getToDate(), f.getFilterValue());
            default -> paymentRepo.findByEntryDateBetween(f.getFromDate(), f.getToDate());
        };
    }

    // ─── RECEIPTS REPORT ────────────────────────────────────────────────────────
    public List<Receipt> receiptsReport(ReportFilterRequest f) {
        if (f.getFromDate() == null) return receiptRepo.findAll();
        if (f.getFilterType() == null || f.getFilterType().isBlank())
            return receiptRepo.findByEntryDateBetween(f.getFromDate(), f.getToDate());
        return switch (f.getFilterType()) {
            case "receiptName" -> receiptRepo.findByEntryDateBetweenAndReceiptName(
                    f.getFromDate(), f.getToDate(), f.getFilterValue());
            case "receiptType" -> receiptRepo.findByEntryDateBetweenAndReceiptType(
                    f.getFromDate(), f.getToDate(), f.getFilterValue());
            case "company"     -> receiptRepo.findByEntryDateBetweenAndCompany(
                    f.getFromDate(), f.getToDate(), f.getFilterValue());
            case "transMode"   -> receiptRepo.findByEntryDateBetweenAndTransMode(
                    f.getFromDate(), f.getToDate(), f.getFilterValue());
            case "bankName"    -> receiptRepo.findByEntryDateBetweenAndBankName(
                    f.getFromDate(), f.getToDate(), f.getFilterValue());
            case "accNo"       -> receiptRepo.findByEntryDateBetweenAndAccNo(
                    f.getFromDate(), f.getToDate(), f.getFilterValue());
            case "accType"     -> receiptRepo.findByEntryDateBetweenAndAccType(
                    f.getFromDate(), f.getToDate(), f.getFilterValue());
            default -> receiptRepo.findByEntryDateBetween(f.getFromDate(), f.getToDate());
        };
    }

    // ─── PURCHASE INVOICE REPORT ────────────────────────────────────────────────
    public List<PurchaseInvoice> purchaseInvoiceReport(ReportFilterRequest f) {
        if (f.getFromDate() == null) return purchaseInvoiceRepo.findAll();
        if (f.getFilterType() == null || f.getFilterType().isBlank())
            return purchaseInvoiceRepo.findByEntryDateBetween(f.getFromDate(), f.getToDate());
        return switch (f.getFilterType()) {
            case "supName"  -> purchaseInvoiceRepo.findByEntryDateBetweenAndSupName(
                    f.getFromDate(), f.getToDate(), f.getFilterValue());
            case "company"  -> purchaseInvoiceRepo.findByEntryDateBetweenAndCompany(
                    f.getFromDate(), f.getToDate(), f.getFilterValue());
            case "catName"  -> purchaseInvoiceRepo.findByEntryDateBetweenAndCatName(
                    f.getFromDate(), f.getToDate(), f.getFilterValue());
            default -> purchaseInvoiceRepo.findByEntryDateBetween(f.getFromDate(), f.getToDate());
        };
    }

    // ─── DUE DATE CROSSED REPORT ────────────────────────────────────────────────
    public List<PurchaseInvoice> dueDateCrossedReport(ReportFilterRequest f) {
        LocalDate today = LocalDate.now();
        List<PurchaseInvoice> all = purchaseInvoiceRepo.findByInvoiceDueDateBefore(today);
        if (f.getFromDate() == null) return all;
        if (f.getFilterType() == null || f.getFilterType().isBlank()) {
            return all.stream().filter(p ->
                    !p.getEntryDate().isBefore(f.getFromDate()) &&
                    !p.getEntryDate().isAfter(f.getToDate())).toList();
        }
        return switch (f.getFilterType()) {
            case "supName"  -> all.stream().filter(p ->
                    !p.getEntryDate().isBefore(f.getFromDate()) &&
                    !p.getEntryDate().isAfter(f.getToDate()) &&
                    p.getSupName().equals(f.getFilterValue())).toList();
            case "company"  -> all.stream().filter(p ->
                    !p.getEntryDate().isBefore(f.getFromDate()) &&
                    !p.getEntryDate().isAfter(f.getToDate()) &&
                    p.getCompany().equals(f.getFilterValue())).toList();
            case "catName"  -> all.stream().filter(p ->
                    !p.getEntryDate().isBefore(f.getFromDate()) &&
                    !p.getEntryDate().isAfter(f.getToDate()) &&
                    p.getCatName().equals(f.getFilterValue())).toList();
            default -> all;
        };
    }

    // ─── EXPORT INVOICE REPORT ──────────────────────────────────────────────────
    public List<ExportInvoice> exportInvoiceReport(ReportFilterRequest f) {
        if (f.getFromDate() == null) return exportInvoiceRepo.findAll();
        if (f.getFilterType() == null || f.getFilterType().isBlank())
            return exportInvoiceRepo.findByEntryDateBetween(f.getFromDate(), f.getToDate());
        return switch (f.getFilterType()) {
            case "clientName" -> exportInvoiceRepo.findByEntryDateBetweenAndClientName(
                    f.getFromDate(), f.getToDate(), f.getFilterValue());
            case "status"     -> exportInvoiceRepo.findByEntryDateBetweenAndStatus(
                    f.getFromDate(), f.getToDate(), f.getFilterValue());
            default -> exportInvoiceRepo.findByEntryDateBetween(f.getFromDate(), f.getToDate());
        };
    }

    // ─── RETAIL INVOICE REPORT ──────────────────────────────────────────────────
    public List<RetailInvoice> retailInvoiceReport(ReportFilterRequest f) {
        if (f.getFromDate() == null) return retailInvoiceRepo.findAll();
        if (f.getFilterType() == null || f.getFilterType().isBlank())
            return retailInvoiceRepo.findByEntryDateBetween(f.getFromDate(), f.getToDate());
        return switch (f.getFilterType()) {
            case "clientName" -> retailInvoiceRepo.findByEntryDateBetweenAndClientName(
                    f.getFromDate(), f.getToDate(), f.getFilterValue());
            case "status"     -> retailInvoiceRepo.findByEntryDateBetweenAndStatus(
                    f.getFromDate(), f.getToDate(), f.getFilterValue());
            default -> retailInvoiceRepo.findByEntryDateBetween(f.getFromDate(), f.getToDate());
        };
    }

    // ─── RETAIL RECEIPT REPORT ──────────────────────────────────────────────────
    public List<RetailReceipt> retailReceiptReport(ReportFilterRequest f) {
        if (f.getFromDate() == null) return retailReceiptRepo.findAll();
        if (f.getFilterType() == null || f.getFilterType().isBlank())
            return retailReceiptRepo.findByEntryDateBetween(f.getFromDate(), f.getToDate());
        return switch (f.getFilterType()) {
            case "clientName" -> retailReceiptRepo.findByEntryDateBetweenAndClientName(
                    f.getFromDate(), f.getToDate(), f.getFilterValue());
            case "transMode"  -> retailReceiptRepo.findByEntryDateBetweenAndTransMode(
                    f.getFromDate(), f.getToDate(), f.getFilterValue());
            case "bankName"   -> retailReceiptRepo.findByEntryDateBetweenAndBankName(
                    f.getFromDate(), f.getToDate(), f.getFilterValue());
            case "accNo"      -> retailReceiptRepo.findByEntryDateBetweenAndAccNo(
                    f.getFromDate(), f.getToDate(), f.getFilterValue());
            default -> retailReceiptRepo.findByEntryDateBetween(f.getFromDate(), f.getToDate());
        };
    }

    // ─── EXPORT RECEIPT REPORT ──────────────────────────────────────────────────
    public List<ExportReceipt> exportReceiptReport(ReportFilterRequest f) {
        if (f.getFromDate() == null) return exportReceiptRepo.findAll();
        if (f.getFilterType() == null || f.getFilterType().isBlank())
            return exportReceiptRepo.findByEntryDateBetween(f.getFromDate(), f.getToDate());
        return switch (f.getFilterType()) {
            case "clientName" -> exportReceiptRepo.findByEntryDateBetweenAndClientName(
                    f.getFromDate(), f.getToDate(), f.getFilterValue());
            case "transMode"  -> exportReceiptRepo.findByEntryDateBetweenAndTransMode(
                    f.getFromDate(), f.getToDate(), f.getFilterValue());
            case "bankName"   -> exportReceiptRepo.findByEntryDateBetweenAndBankName(
                    f.getFromDate(), f.getToDate(), f.getFilterValue());
            default -> exportReceiptRepo.findByEntryDateBetween(f.getFromDate(), f.getToDate());
        };
    }

    // ─── DEBIT NOTES REPORT ─────────────────────────────────────────────────────
    public List<DebitNote> debitNotesReport(ReportFilterRequest f) {
        if (f.getFromDate() == null) return debitNoteRepo.findAll();
        if (f.getFilterType() == null || f.getFilterType().isBlank())
            return debitNoteRepo.findByEntryDateBetween(f.getFromDate(), f.getToDate());
        return switch (f.getFilterType()) {
            case "supName" -> debitNoteRepo.findByEntryDateBetweenAndSupName(
                    f.getFromDate(), f.getToDate(), f.getFilterValue());
            default -> debitNoteRepo.findByEntryDateBetween(f.getFromDate(), f.getToDate());
        };
    }

    // ─── INCENTIVES REPORT ──────────────────────────────────────────────────────
    public List<SalexportIncentives> incentivesReport(ReportFilterRequest f) {
        if (f.getFromDate() == null) return incentivesRepo.findAll();
        if (f.getFilterType() == null || f.getFilterType().isBlank())
            return incentivesRepo.findByEntryDateBetween(f.getFromDate(), f.getToDate());
        return switch (f.getFilterType()) {
            case "incentivesType" -> incentivesRepo.findByEntryDateBetweenAndIncentivesType(
                    f.getFromDate(), f.getToDate(), f.getFilterValue());
            default -> incentivesRepo.findByEntryDateBetween(f.getFromDate(), f.getToDate());
        };
    }

    // ─── SUPPLIER ADVANCE REPORT ────────────────────────────────────────────────
    public List<SupplierAdvance> supplierAdvanceReport(ReportFilterRequest f) {
        if (f.getFromDate() == null) return supplierAdvanceRepo.findAll();
        if (f.getFilterType() == null || f.getFilterType().isBlank())
            return supplierAdvanceRepo.findByEntryDateBetween(f.getFromDate(), f.getToDate());
        return switch (f.getFilterType()) {
            case "supName" -> supplierAdvanceRepo.findByEntryDateBetweenAndSupName(
                    f.getFromDate(), f.getToDate(), f.getFilterValue());
            default -> supplierAdvanceRepo.findByEntryDateBetween(f.getFromDate(), f.getToDate());
        };
    }

    // ─── SUPPLIER CREDIT REPORT ─────────────────────────────────────────────────
    public List<SupplierCreditOpenClose> supplierCreditReport(ReportFilterRequest f) {
        if (f.getFilterType() == null || f.getFilterType().isBlank())
            return supplierCreditRepo.findAllByOrderBySupNameAsc();
        return switch (f.getFilterType()) {
            case "supName" -> supplierCreditRepo.findBySupName(f.getFilterValue())
                    .map(List::of).orElse(List.of());
            case "dueDays" -> {
                List<String> matchingSuppliers = supplierMasterRepo
                        .findAll().stream()
                        .filter(s -> f.getFilterValue().equals(s.getDueDays()))
                        .map(SupplierMaster::getSupName).toList();
                yield supplierCreditRepo.findAll().stream()
                        .filter(c -> matchingSuppliers.contains(c.getSupName())).toList();
            }
            default -> supplierCreditRepo.findAllByOrderBySupNameAsc();
        };
    }

    // ─── SUPPLIER LEDGER REPORT ─────────────────────────────────────────────────
    public List<SupplierLedger> supplierLedgerReport(String supName, ReportFilterRequest f) {
        if (f.getFromDate() == null)
            return supplierLedgerRepo.findBySupNameOrderByEntryDateAsc(supName);
        return supplierLedgerRepo.findByEntryDateBetweenAndSupName(f.getFromDate(), f.getToDate(), supName);
    }

    public List<String> getDistinctSupplierNamesFromLedger() {
        return supplierLedgerRepo.findAll().stream()
                .map(SupplierLedger::getSupName).distinct().sorted().toList();
    }

    // ─── MASTER REPORTS ─────────────────────────────────────────────────────────
    public List<BankMaster>        bankMasterReport()        { return bankMasterRepo.findAllByOrderByBankNameAsc(); }
    public List<CategoryMaster>    categoryMasterReport()    { return categoryMasterRepo.findAllByOrderByCatNameAsc(); }
    public List<ClientMaster>      clientMasterReport()      { return clientMasterRepo.findAllByOrderByClientNameAsc(); }
    public List<CompanyMaster>     companyMasterReport()     { return companyMasterRepo.findAllByOrderByCompNameAsc(); }
    public List<CurrencyMaster>    currencyMasterReport()    { return currencyMasterRepo.findAllByOrderByCurrNameAsc(); }
    public List<ReceiptNameMaster> receiptNameMasterReport() { return receiptNameMasterRepo.findAllByOrderByReceiptNameAsc(); }
    public List<ReceiptTypeMaster> receiptTypeMasterReport() { return receiptTypeMasterRepo.findAllByOrderByReceiptTypeNameAsc(); }
    public List<SupplierMaster>    supplierMasterReport()    { return supplierMasterRepo.findAllByOrderBySupNameAsc(); }
    public List<TransactionMaster> transactionMasterReport() { return transactionMasterRepo.findAllByOrderByTransNameAsc(); }

    // ─── DISTINCT VALUES FOR FILTER DROPDOWNS ───────────────────────────────────
    public Map<String, List<String>> getPaymentFilterOptions() {
        return Map.of(
            "paymentName", paymentRepo.findAll().stream().map(Payment::getPaymentName).distinct().sorted().toList(),
            "paymentType", paymentRepo.findAll().stream().map(Payment::getPaymentType).distinct().sorted().toList(),
            "supName",     paymentRepo.findAll().stream().map(Payment::getSupName).distinct().sorted().toList(),
            "transMode",   paymentRepo.findAll().stream().map(Payment::getTransMode).distinct().sorted().toList(),
            "bankName",    paymentRepo.findAll().stream().map(Payment::getBankName).distinct().sorted().toList(),
            "accNo",       paymentRepo.findAll().stream().map(Payment::getAccNo).distinct().sorted().toList(),
            "accType",     paymentRepo.findAll().stream().map(Payment::getAccType).distinct().sorted().toList()
        );
    }

    public Map<String, List<String>> getReceiptFilterOptions() {
        return Map.of(
            "receiptName", receiptRepo.findAll().stream().map(Receipt::getReceiptName).distinct().sorted().toList(),
            "receiptType", receiptRepo.findAll().stream().map(Receipt::getReceiptType).distinct().sorted().toList(),
            "company",     receiptRepo.findAll().stream().map(Receipt::getCompany).distinct().sorted().toList(),
            "transMode",   receiptRepo.findAll().stream().map(Receipt::getTransMode).distinct().sorted().toList(),
            "bankName",    receiptRepo.findAll().stream().map(Receipt::getBankName).distinct().sorted().toList(),
            "accNo",       receiptRepo.findAll().stream().map(Receipt::getAccNo).distinct().sorted().toList(),
            "accType",     receiptRepo.findAll().stream().map(Receipt::getAccType).distinct().sorted().toList()
        );
    }
}
