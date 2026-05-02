package com.fundtracker.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Document(collection = "supplier_ledger")
public class SupplierLedger {

    @Id
    private String id;           // ← was stripped by auto-expander; service passes null here
    private LocalDate entryDate;
    private LocalDate invoiceDate;
    private LocalDate paymentDate;
    private String company;
    private String supName;
    private String transMode;
    private String description;
    private String bankName;
    private String accNo;
    private Double debit;
    private double credit;       // primitive double — matches service: 0.0 literal
    private double clsBal;       // primitive double — matches service: clsBal variable
    private String remarks;

    public SupplierLedger() {}

    // 14-arg constructor — matches DebitNoteService call:
    // new SupplierLedger(null, LocalDate.now(), invoiceDate, null,
    //                    company, supName, null, null, null, null,
    //                    supDebit, 0.0, clsBal, remarks)
    public SupplierLedger(String id, LocalDate entryDate, LocalDate invoiceDate,
                          LocalDate paymentDate, String company, String supName,
                          String transMode, String description, String bankName, String accNo,
                          Double debit, double credit, double clsBal, String remarks) {
        this.id          = id;
        this.entryDate   = entryDate;
        this.invoiceDate = invoiceDate;
        this.paymentDate = paymentDate;
        this.company     = company;
        this.supName     = supName;
        this.transMode   = transMode;
        this.description = description;
        this.bankName    = bankName;
        this.accNo       = accNo;
        this.debit       = debit;
        this.credit      = credit;
        this.clsBal      = clsBal;
        this.remarks     = remarks;
    }

    // Getters
    public String getId()             { return id; }
    public LocalDate getEntryDate()   { return entryDate; }
    public LocalDate getInvoiceDate() { return invoiceDate; }
    public LocalDate getPaymentDate() { return paymentDate; }
    public String getCompany()        { return company; }
    public String getSupName()        { return supName; }
    public String getTransMode()      { return transMode; }
    public String getDescription()    { return description; }
    public String getBankName()       { return bankName; }
    public String getAccNo()          { return accNo; }
    public Double getDebit()          { return debit; }
    public double getCredit()         { return credit; }
    public double getClsBal()         { return clsBal; }
    public String getRemarks()        { return remarks; }

    // Setters
    public void setId(String id)                    { this.id          = id; }
    public void setEntryDate(LocalDate entryDate)   { this.entryDate   = entryDate; }
    public void setInvoiceDate(LocalDate v)         { this.invoiceDate = v; }
    public void setPaymentDate(LocalDate v)         { this.paymentDate = v; }
    public void setCompany(String company)          { this.company     = company; }
    public void setSupName(String supName)          { this.supName     = supName; }
    public void setTransMode(String transMode)      { this.transMode   = transMode; }
    public void setDescription(String description)  { this.description = description; }
    public void setBankName(String bankName)        { this.bankName    = bankName; }
    public void setAccNo(String accNo)              { this.accNo       = accNo; }
    public void setDebit(Double debit)              { this.debit       = debit; }
    public void setCredit(double credit)            { this.credit      = credit; }
    public void setClsBal(double clsBal)            { this.clsBal      = clsBal; }
    public void setRemarks(String remarks)          { this.remarks     = remarks; }
}
