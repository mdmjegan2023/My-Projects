package com.fundtracker.dto;

import com.fundtracker.model.PurchaseInvoice;
import java.util.List;

public class DashboardSummary {

    private List<PurchaseInvoice> paymentAlerts;
    private long totalSuppliers;
    private long totalPurchaseInvoices;
    private long totalExportInvoices;
    private long totalRetailInvoices;
    private long totalPayments;
    private long totalReceipts;
    private long overdueBills;

    public DashboardSummary() {}

    public DashboardSummary(List<PurchaseInvoice> paymentAlerts, long totalSuppliers, long totalPurchaseInvoices, long totalExportInvoices, long totalRetailInvoices, long totalPayments, long totalReceipts, long overdueBills) {
        this.paymentAlerts = paymentAlerts;
        this.totalSuppliers = totalSuppliers;
        this.totalPurchaseInvoices = totalPurchaseInvoices;
        this.totalExportInvoices = totalExportInvoices;
        this.totalRetailInvoices = totalRetailInvoices;
        this.totalPayments = totalPayments;
        this.totalReceipts = totalReceipts;
        this.overdueBills = overdueBills;
    }

    public List<PurchaseInvoice> getPaymentAlerts() { return paymentAlerts; }
    public long getTotalSuppliers() { return totalSuppliers; }
    public long getTotalPurchaseInvoices() { return totalPurchaseInvoices; }
    public long getTotalExportInvoices() { return totalExportInvoices; }
    public long getTotalRetailInvoices() { return totalRetailInvoices; }
    public long getTotalPayments() { return totalPayments; }
    public long getTotalReceipts() { return totalReceipts; }
    public long getOverdueBills() { return overdueBills; }

    public void setPaymentAlerts(List<PurchaseInvoice> paymentAlerts) { this.paymentAlerts = paymentAlerts; }
    public void setTotalSuppliers(long totalSuppliers) { this.totalSuppliers = totalSuppliers; }
    public void setTotalPurchaseInvoices(long totalPurchaseInvoices) { this.totalPurchaseInvoices = totalPurchaseInvoices; }
    public void setTotalExportInvoices(long totalExportInvoices) { this.totalExportInvoices = totalExportInvoices; }
    public void setTotalRetailInvoices(long totalRetailInvoices) { this.totalRetailInvoices = totalRetailInvoices; }
    public void setTotalPayments(long totalPayments) { this.totalPayments = totalPayments; }
    public void setTotalReceipts(long totalReceipts) { this.totalReceipts = totalReceipts; }
    public void setOverdueBills(long overdueBills) { this.overdueBills = overdueBills; }
}