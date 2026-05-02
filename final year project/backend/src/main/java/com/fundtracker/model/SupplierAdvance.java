package com.fundtracker.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Document(collection = "supplier_advance")
public class SupplierAdvance {

    @Id
    private String id;           // ← was stripped by auto-expander; service passes null here
    private String pAdvCode;
    private LocalDate entryDate;
    private String supName;
    private String supAddress;
    private String supPhone;
    private String supEmail;
    private Double supAdvance;
    private String transMode;
    private String bankName;
    private String accNo;
    private String accType;
    private String bankBranch;
    private String remarks;

    public SupplierAdvance() {}

    // 14-arg constructor — matches DebitNoteService call:
    // new SupplierAdvance(null, docNo, date, supName, supAddr, supPhone, supEmail,
    //                     supDebit, null, null, null, null, null, remarks)
    public SupplierAdvance(String id, String pAdvCode, LocalDate entryDate,
                           String supName, String supAddress, String supPhone, String supEmail,
                           Double supAdvance, String transMode, String bankName,
                           String accNo, String accType, String bankBranch, String remarks) {
        this.id         = id;
        this.pAdvCode   = pAdvCode;
        this.entryDate  = entryDate;
        this.supName    = supName;
        this.supAddress = supAddress;
        this.supPhone   = supPhone;
        this.supEmail   = supEmail;
        this.supAdvance = supAdvance;
        this.transMode  = transMode;
        this.bankName   = bankName;
        this.accNo      = accNo;
        this.accType    = accType;
        this.bankBranch = bankBranch;
        this.remarks    = remarks;
    }

    // Getters
    public String getId()          { return id; }
    public String getPAdvCode()    { return pAdvCode; }
    public LocalDate getEntryDate(){ return entryDate; }
    public String getSupName()     { return supName; }
    public String getSupAddress()  { return supAddress; }
    public String getSupPhone()    { return supPhone; }
    public String getSupEmail()    { return supEmail; }
    public Double getSupAdvance()  { return supAdvance; }
    public String getTransMode()   { return transMode; }
    public String getBankName()    { return bankName; }
    public String getAccNo()       { return accNo; }
    public String getAccType()     { return accType; }
    public String getBankBranch()  { return bankBranch; }
    public String getRemarks()     { return remarks; }

    // Setters
    public void setId(String id)                   { this.id         = id; }
    public void setPAdvCode(String pAdvCode)        { this.pAdvCode   = pAdvCode; }
    public void setEntryDate(LocalDate entryDate)  { this.entryDate  = entryDate; }
    public void setSupName(String supName)          { this.supName    = supName; }
    public void setSupAddress(String supAddress)    { this.supAddress = supAddress; }
    public void setSupPhone(String supPhone)        { this.supPhone   = supPhone; }
    public void setSupEmail(String supEmail)        { this.supEmail   = supEmail; }
    public void setSupAdvance(Double supAdvance)    { this.supAdvance = supAdvance; }
    public void setTransMode(String transMode)      { this.transMode  = transMode; }
    public void setBankName(String bankName)        { this.bankName   = bankName; }
    public void setAccNo(String accNo)              { this.accNo      = accNo; }
    public void setAccType(String accType)          { this.accType    = accType; }
    public void setBankBranch(String bankBranch)    { this.bankBranch = bankBranch; }
    public void setRemarks(String remarks)          { this.remarks    = remarks; }
}
