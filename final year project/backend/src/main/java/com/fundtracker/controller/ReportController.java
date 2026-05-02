package com.fundtracker.controller;

import com.fundtracker.dto.ApiResponse;
import com.fundtracker.dto.ReportFilterRequest;
import com.fundtracker.model.*;
import com.fundtracker.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired private ReportService service;

    // ─── TRANSACTION REPORTS ─────────────────────────────────────────────────────
    @PostMapping("/payments")
    public ResponseEntity<ApiResponse<List<Payment>>> paymentsReport(@RequestBody ReportFilterRequest filter) {
        return ResponseEntity.ok(ApiResponse.success(service.paymentsReport(filter)));
    }

    @PostMapping("/receipts")
    public ResponseEntity<ApiResponse<List<Receipt>>> receiptsReport(@RequestBody ReportFilterRequest filter) {
        return ResponseEntity.ok(ApiResponse.success(service.receiptsReport(filter)));
    }

    @PostMapping("/purchase-invoices")
    public ResponseEntity<ApiResponse<List<PurchaseInvoice>>> purchaseInvoiceReport(@RequestBody ReportFilterRequest filter) {
        return ResponseEntity.ok(ApiResponse.success(service.purchaseInvoiceReport(filter)));
    }

    @PostMapping("/due-date-crossed")
    public ResponseEntity<ApiResponse<List<PurchaseInvoice>>> dueDateCrossedReport(@RequestBody ReportFilterRequest filter) {
        return ResponseEntity.ok(ApiResponse.success(service.dueDateCrossedReport(filter)));
    }

    @PostMapping("/export-invoices")
    public ResponseEntity<ApiResponse<List<ExportInvoice>>> exportInvoiceReport(@RequestBody ReportFilterRequest filter) {
        return ResponseEntity.ok(ApiResponse.success(service.exportInvoiceReport(filter)));
    }

    @PostMapping("/retail-invoices")
    public ResponseEntity<ApiResponse<List<RetailInvoice>>> retailInvoiceReport(@RequestBody ReportFilterRequest filter) {
        return ResponseEntity.ok(ApiResponse.success(service.retailInvoiceReport(filter)));
    }

    @PostMapping("/retail-receipts")
    public ResponseEntity<ApiResponse<List<RetailReceipt>>> retailReceiptReport(@RequestBody ReportFilterRequest filter) {
        return ResponseEntity.ok(ApiResponse.success(service.retailReceiptReport(filter)));
    }

    @PostMapping("/export-receipts")
    public ResponseEntity<ApiResponse<List<ExportReceipt>>> exportReceiptReport(@RequestBody ReportFilterRequest filter) {
        return ResponseEntity.ok(ApiResponse.success(service.exportReceiptReport(filter)));
    }

    @PostMapping("/debit-notes")
    public ResponseEntity<ApiResponse<List<DebitNote>>> debitNotesReport(@RequestBody ReportFilterRequest filter) {
        return ResponseEntity.ok(ApiResponse.success(service.debitNotesReport(filter)));
    }

    @PostMapping("/incentives")
    public ResponseEntity<ApiResponse<List<SalexportIncentives>>> incentivesReport(@RequestBody ReportFilterRequest filter) {
        return ResponseEntity.ok(ApiResponse.success(service.incentivesReport(filter)));
    }

    @PostMapping("/supplier-advance")
    public ResponseEntity<ApiResponse<List<SupplierAdvance>>> supplierAdvanceReport(@RequestBody ReportFilterRequest filter) {
        return ResponseEntity.ok(ApiResponse.success(service.supplierAdvanceReport(filter)));
    }

    @PostMapping("/supplier-credit")
    public ResponseEntity<ApiResponse<List<SupplierCreditOpenClose>>> supplierCreditReport(@RequestBody ReportFilterRequest filter) {
        return ResponseEntity.ok(ApiResponse.success(service.supplierCreditReport(filter)));
    }

    @PostMapping("/supplier-ledger/{supName}")
    public ResponseEntity<ApiResponse<List<SupplierLedger>>> supplierLedgerReport(
            @PathVariable String supName, @RequestBody ReportFilterRequest filter) {
        return ResponseEntity.ok(ApiResponse.success(service.supplierLedgerReport(supName, filter)));
    }

    @GetMapping("/supplier-ledger/suppliers")
    public ResponseEntity<ApiResponse<List<String>>> getLedgerSupplierNames() {
        return ResponseEntity.ok(ApiResponse.success(service.getDistinctSupplierNamesFromLedger()));
    }

    // ─── MASTER REPORTS ──────────────────────────────────────────────────────────
    @GetMapping("/masters/banks")           public ResponseEntity<ApiResponse<List<BankMaster>>>        bankMasterReport()        { return ResponseEntity.ok(ApiResponse.success(service.bankMasterReport())); }
    @GetMapping("/masters/categories")     public ResponseEntity<ApiResponse<List<CategoryMaster>>>    categoryMasterReport()    { return ResponseEntity.ok(ApiResponse.success(service.categoryMasterReport())); }
    @GetMapping("/masters/clients")        public ResponseEntity<ApiResponse<List<ClientMaster>>>      clientMasterReport()      { return ResponseEntity.ok(ApiResponse.success(service.clientMasterReport())); }
    @GetMapping("/masters/companies")      public ResponseEntity<ApiResponse<List<CompanyMaster>>>     companyMasterReport()     { return ResponseEntity.ok(ApiResponse.success(service.companyMasterReport())); }
    @GetMapping("/masters/currencies")     public ResponseEntity<ApiResponse<List<CurrencyMaster>>>    currencyMasterReport()    { return ResponseEntity.ok(ApiResponse.success(service.currencyMasterReport())); }
    @GetMapping("/masters/receipt-names")  public ResponseEntity<ApiResponse<List<ReceiptNameMaster>>> receiptNameMasterReport() { return ResponseEntity.ok(ApiResponse.success(service.receiptNameMasterReport())); }
    @GetMapping("/masters/receipt-types")  public ResponseEntity<ApiResponse<List<ReceiptTypeMaster>>> receiptTypeMasterReport() { return ResponseEntity.ok(ApiResponse.success(service.receiptTypeMasterReport())); }
    @GetMapping("/masters/suppliers")      public ResponseEntity<ApiResponse<List<SupplierMaster>>>    supplierMasterReport()    { return ResponseEntity.ok(ApiResponse.success(service.supplierMasterReport())); }
    @GetMapping("/masters/transactions")   public ResponseEntity<ApiResponse<List<TransactionMaster>>> transactionMasterReport() { return ResponseEntity.ok(ApiResponse.success(service.transactionMasterReport())); }

    // ─── FILTER DROPDOWN OPTIONS ─────────────────────────────────────────────────
    @GetMapping("/filter-options/payments")
    public ResponseEntity<ApiResponse<Map<String, List<String>>>> paymentFilterOptions() {
        return ResponseEntity.ok(ApiResponse.success(service.getPaymentFilterOptions()));
    }

    @GetMapping("/filter-options/receipts")
    public ResponseEntity<ApiResponse<Map<String, List<String>>>> receiptFilterOptions() {
        return ResponseEntity.ok(ApiResponse.success(service.getReceiptFilterOptions()));
    }
}
