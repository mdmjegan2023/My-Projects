package com.fundtracker.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Document(collection = "purchase_invoice")
public class PurchaseInvoice {

    @Id private String id;
    private String iPurCode;
    private LocalDate entryDate;
    private String company;
    private String catName;
    private String invoiceNo;
    private LocalDate invoiceDate;
    private String supName;
    private String supAddress;
    private String supPhone;
    private String supEmail;
    private Double invoiceAmount;
    private Double supAdvance;
    private LocalDate invoiceDueDate;
    private String remarks;

    public PurchaseInvoice() {}

    public PurchaseInvoice(String id, String iPurCode, LocalDate entryDate, String company, String catName, String invoiceNo, LocalDate invoiceDate, String supName, String supAddress, String supPhone, String supEmail, Double invoiceAmount, Double supAdvance, LocalDate invoiceDueDate, String remarks) {
        this.id = id;
        this.iPurCode = iPurCode;
        this.entryDate = entryDate;
        this.company = company;
        this.catName = catName;
        this.invoiceNo = invoiceNo;
        this.invoiceDate = invoiceDate;
        this.supName = supName;
        this.supAddress = supAddress;
        this.supPhone = supPhone;
        this.supEmail = supEmail;
        this.invoiceAmount = invoiceAmount;
        this.supAdvance = supAdvance;
        this.invoiceDueDate = invoiceDueDate;
        this.remarks = remarks;
    }

    public String getId() { return id; }
    public String getIPurCode() { return iPurCode; }
    public LocalDate getEntryDate() { return entryDate; }
    public String getCompany() { return company; }
    public String getCatName() { return catName; }
    public String getInvoiceNo() { return invoiceNo; }
    public LocalDate getInvoiceDate() { return invoiceDate; }
    public String getSupName() { return supName; }
    public String getSupAddress() { return supAddress; }
    public String getSupPhone() { return supPhone; }
    public String getSupEmail() { return supEmail; }
    public Double getInvoiceAmount() { return invoiceAmount; }
    public Double getSupAdvance() { return supAdvance; }
    public LocalDate getInvoiceDueDate() { return invoiceDueDate; }
    public String getRemarks() { return remarks; }

    public void setId(String id) { this.id = id; }
    public void setIPurCode(String iPurCode) { this.iPurCode = iPurCode; }
    public void setEntryDate(LocalDate entryDate) { this.entryDate = entryDate; }
    public void setCompany(String company) { this.company = company; }
    public void setCatName(String catName) { this.catName = catName; }
    public void setInvoiceNo(String invoiceNo) { this.invoiceNo = invoiceNo; }
    public void setInvoiceDate(LocalDate invoiceDate) { this.invoiceDate = invoiceDate; }
    public void setSupName(String supName) { this.supName = supName; }
    public void setSupAddress(String supAddress) { this.supAddress = supAddress; }
    public void setSupPhone(String supPhone) { this.supPhone = supPhone; }
    public void setSupEmail(String supEmail) { this.supEmail = supEmail; }
    public void setInvoiceAmount(Double invoiceAmount) { this.invoiceAmount = invoiceAmount; }
    public void setSupAdvance(Double supAdvance) { this.supAdvance = supAdvance; }
    public void setInvoiceDueDate(LocalDate invoiceDueDate) { this.invoiceDueDate = invoiceDueDate; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
}