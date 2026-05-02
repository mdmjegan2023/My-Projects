package com.fundtracker.service;

import com.fundtracker.dto.DashboardSummary;
import com.fundtracker.model.*;
import com.fundtracker.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DashboardService {

    @Autowired private PurchaseInvoiceRepository purchaseInvoiceRepo;
    @Autowired private ExportInvoiceRepository exportInvoiceRepo;
    @Autowired private RetailInvoiceRepository retailInvoiceRepo;
    @Autowired private PaymentRepository paymentRepo;
    @Autowired private ReceiptRepository receiptRepo;
    @Autowired private SupplierMasterRepository supplierRepo;
    @Autowired private BankBalanceRepository bankBalanceRepo;
    @Autowired private SupplierCreditOpenCloseRepository supplierCreditRepo;

    /** LOAD_DGV_payment_alert: invoices due today or tomorrow */
    public List<PurchaseInvoice> getPaymentAlerts() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        return purchaseInvoiceRepo.findByInvoiceDueDateBetween(today, tomorrow);
    }

    /** Dashboard summary counts */
    public DashboardSummary getSummary() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        return new DashboardSummary(
                purchaseInvoiceRepo.findByInvoiceDueDateBetween(today, tomorrow),
                supplierRepo.count(),
                purchaseInvoiceRepo.count(),
                exportInvoiceRepo.count(),
                retailInvoiceRepo.count(),
                paymentRepo.count(),
                receiptRepo.count(),
                purchaseInvoiceRepo.findByInvoiceDueDateBefore(today).size()
        );
    }

    /** Bank balance for selected account (cmbAccNo on dashboard) */
    public Optional<BankBalance> getBankBalance(String accNo) {
        return bankBalanceRepo.findAll().stream()
                .filter(b -> b.getAccNo().equals(accNo))
                .findFirst();
    }

    /** All bank balances */
    public List<BankBalance> getAllBankBalances() {
        return bankBalanceRepo.findAll();
    }

    /** Supplier credit for selected supplier (cmbSupplierName on dashboard) */
    public Optional<SupplierCreditOpenClose> getSupplierCredit(String supName) {
        return supplierCreditRepo.findBySupName(supName);
    }

    /** All supplier credits */
    public List<SupplierCreditOpenClose> getAllSupplierCredits() {
        return supplierCreditRepo.findAllByOrderBySupNameAsc();
    }
}
