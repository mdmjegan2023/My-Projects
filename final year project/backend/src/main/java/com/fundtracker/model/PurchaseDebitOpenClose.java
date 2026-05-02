package com.fundtracker.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "purchase_debit_openclose")
public class PurchaseDebitOpenClose {
    @Id private String id;
    private String invoiceNo;
    private String supName;
    private String supAddress;
    private String supPhone;
    private String supEmail;
    private Double invoiceAmount;
    private Double supDebit;

    public PurchaseDebitOpenClose() {}
    public PurchaseDebitOpenClose(String id, String invoiceNo, String supName, String supAddress,
                                   String supPhone, String supEmail, Double invoiceAmount, Double supDebit) {
        this.id = id; this.invoiceNo = invoiceNo; this.supName = supName;
        this.supAddress = supAddress; this.supPhone = supPhone; this.supEmail = supEmail;
        this.invoiceAmount = invoiceAmount; this.supDebit = supDebit;
    }
    public String getId()            { return id; }
    public String getInvoiceNo()     { return invoiceNo; }
    public String getSupName()       { return supName; }
    public String getSupAddress()    { return supAddress; }
    public String getSupPhone()      { return supPhone; }
    public String getSupEmail()      { return supEmail; }
    public Double getInvoiceAmount() { return invoiceAmount; }
    public Double getSupDebit()      { return supDebit; }
    public void setId(String id)                      { this.id = id; }
    public void setInvoiceNo(String invoiceNo)        { this.invoiceNo = invoiceNo; }
    public void setSupName(String supName)            { this.supName = supName; }
    public void setSupAddress(String supAddress)      { this.supAddress = supAddress; }
    public void setSupPhone(String supPhone)          { this.supPhone = supPhone; }
    public void setSupEmail(String supEmail)          { this.supEmail = supEmail; }
    public void setInvoiceAmount(Double invoiceAmount){ this.invoiceAmount = invoiceAmount; }
    public void setSupDebit(Double supDebit)          { this.supDebit = supDebit; }
}
