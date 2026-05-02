package com.fundtracker.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Document(collection = "purchase_debit_note")
public class DebitNote {

    @Id private String id;
    private String iDbtCode;
    private LocalDate entryDate;
    private String invoiceNo;
    private String supName;
    private String supAddress;
    private String supPhone;
    private String supEmail;
    private String company;
    private String catName;
    private Double billInvoiceAmount;
    private Double supDebit;
    private Double invoiceAmount;
    private LocalDate invoiceDate;
    private LocalDate invoiceDueDate;
    private String remarks;

    public DebitNote() {}

    public DebitNote(String id, String iDbtCode, LocalDate entryDate, String invoiceNo, String supName, String supAddress, String supPhone, String supEmail, String company, String catName, Double billInvoiceAmount, Double supDebit, Double invoiceAmount, LocalDate invoiceDate, LocalDate invoiceDueDate, String remarks) {
        this.id = id;
        this.iDbtCode = iDbtCode;
        this.entryDate = entryDate;
        this.invoiceNo = invoiceNo;
        this.supName = supName;
        this.supAddress = supAddress;
        this.supPhone = supPhone;
        this.supEmail = supEmail;
        this.company = company;
        this.catName = catName;
        this.billInvoiceAmount = billInvoiceAmount;
        this.supDebit = supDebit;
        this.invoiceAmount = invoiceAmount;
        this.invoiceDate = invoiceDate;
        this.invoiceDueDate = invoiceDueDate;
        this.remarks = remarks;
    }

    public String getId() { return id; }
    public String getIDbtCode() { return iDbtCode; }
    public LocalDate getEntryDate() { return entryDate; }
    public String getInvoiceNo() { return invoiceNo; }
    public String getSupName() { return supName; }
    public String getSupAddress() { return supAddress; }
    public String getSupPhone() { return supPhone; }
    public String getSupEmail() { return supEmail; }
    public String getCompany() { return company; }
    public String getCatName() { return catName; }
    public Double getBillInvoiceAmount() { return billInvoiceAmount; }
    public Double getSupDebit() { return supDebit; }
    public Double getInvoiceAmount() { return invoiceAmount; }
    public LocalDate getInvoiceDate() { return invoiceDate; }
    public LocalDate getInvoiceDueDate() { return invoiceDueDate; }
    public String getRemarks() { return remarks; }

    public void setId(String id) { this.id = id; }
    public void setIDbtCode(String iDbtCode) { this.iDbtCode = iDbtCode; }
    public void setEntryDate(LocalDate entryDate) { this.entryDate = entryDate; }
    public void setInvoiceNo(String invoiceNo) { this.invoiceNo = invoiceNo; }
    public void setSupName(String supName) { this.supName = supName; }
    public void setSupAddress(String supAddress) { this.supAddress = supAddress; }
    public void setSupPhone(String supPhone) { this.supPhone = supPhone; }
    public void setSupEmail(String supEmail) { this.supEmail = supEmail; }
    public void setCompany(String company) { this.company = company; }
    public void setCatName(String catName) { this.catName = catName; }
    public void setBillInvoiceAmount(Double billInvoiceAmount) { this.billInvoiceAmount = billInvoiceAmount; }
    public void setSupDebit(Double supDebit) { this.supDebit = supDebit; }
    public void setInvoiceAmount(Double invoiceAmount) { this.invoiceAmount = invoiceAmount; }
    public void setInvoiceDate(LocalDate invoiceDate) { this.invoiceDate = invoiceDate; }
    public void setInvoiceDueDate(LocalDate invoiceDueDate) { this.invoiceDueDate = invoiceDueDate; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
}