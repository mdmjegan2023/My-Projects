package com.fundtracker.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Document(collection = "receipt")
public class Receipt {

    @Id private String id;
    private String docNo;
    private LocalDate entryDate;
    private LocalDate receiptDate;
    private String receiptName;
    private String receiptType;
    private String company;
    private String description;
    private Double amount;
    private String transMode;
    private String bankName;
    private String accNo;
    private String accType;
    private String bankBranch;
    private String ifscCode;
    private String swiftCode;
    private String remarks;

    public Receipt() {}

    public Receipt(String id, String docNo, LocalDate entryDate, LocalDate receiptDate, String receiptName, String receiptType, String company, String description, Double amount, String transMode, String bankName, String accNo, String accType, String bankBranch, String ifscCode, String swiftCode, String remarks) {
        this.id = id;
        this.docNo = docNo;
        this.entryDate = entryDate;
        this.receiptDate = receiptDate;
        this.receiptName = receiptName;
        this.receiptType = receiptType;
        this.company = company;
        this.description = description;
        this.amount = amount;
        this.transMode = transMode;
        this.bankName = bankName;
        this.accNo = accNo;
        this.accType = accType;
        this.bankBranch = bankBranch;
        this.ifscCode = ifscCode;
        this.swiftCode = swiftCode;
        this.remarks = remarks;
    }

    public String getId() { return id; }
    public String getDocNo() { return docNo; }
    public LocalDate getEntryDate() { return entryDate; }
    public LocalDate getReceiptDate() { return receiptDate; }
    public String getReceiptName() { return receiptName; }
    public String getReceiptType() { return receiptType; }
    public String getCompany() { return company; }
    public String getDescription() { return description; }
    public Double getAmount() { return amount; }
    public String getTransMode() { return transMode; }
    public String getBankName() { return bankName; }
    public String getAccNo() { return accNo; }
    public String getAccType() { return accType; }
    public String getBankBranch() { return bankBranch; }
    public String getIfscCode() { return ifscCode; }
    public String getSwiftCode() { return swiftCode; }
    public String getRemarks() { return remarks; }

    public void setId(String id) { this.id = id; }
    public void setDocNo(String docNo) { this.docNo = docNo; }
    public void setEntryDate(LocalDate entryDate) { this.entryDate = entryDate; }
    public void setReceiptDate(LocalDate receiptDate) { this.receiptDate = receiptDate; }
    public void setReceiptName(String receiptName) { this.receiptName = receiptName; }
    public void setReceiptType(String receiptType) { this.receiptType = receiptType; }
    public void setCompany(String company) { this.company = company; }
    public void setDescription(String description) { this.description = description; }
    public void setAmount(Double amount) { this.amount = amount; }
    public void setTransMode(String transMode) { this.transMode = transMode; }
    public void setBankName(String bankName) { this.bankName = bankName; }
    public void setAccNo(String accNo) { this.accNo = accNo; }
    public void setAccType(String accType) { this.accType = accType; }
    public void setBankBranch(String bankBranch) { this.bankBranch = bankBranch; }
    public void setIfscCode(String ifscCode) { this.ifscCode = ifscCode; }
    public void setSwiftCode(String swiftCode) { this.swiftCode = swiftCode; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
}