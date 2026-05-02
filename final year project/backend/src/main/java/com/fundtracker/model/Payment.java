package com.fundtracker.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Document(collection = "payment")
public class Payment {

    @Id private String id;
    private String pPayCode;
    private LocalDate entryDate;
    private LocalDate paymentDate;
    private String paymentName;
    private String paymentType;
    private String supName;
    private String supAddress;
    private String supPhone;
    private String supEmail;
    private Double supCredit;
    private Double paidAmount;
    private String transMode;
    private String description;
    private String bankName;
    private String accNo;
    private String accType;
    private String bankBranch;
    private String ifscCode;
    private String swiftCode;
    private String remarks;

    public Payment() {}

    public Payment(String id, String pPayCode, LocalDate entryDate, LocalDate paymentDate, String paymentName, String paymentType, String supName, String supAddress, String supPhone, String supEmail, Double supCredit, Double paidAmount, String transMode, String description, String bankName, String accNo, String accType, String bankBranch, String ifscCode, String swiftCode, String remarks) {
        this.id = id;
        this.pPayCode = pPayCode;
        this.entryDate = entryDate;
        this.paymentDate = paymentDate;
        this.paymentName = paymentName;
        this.paymentType = paymentType;
        this.supName = supName;
        this.supAddress = supAddress;
        this.supPhone = supPhone;
        this.supEmail = supEmail;
        this.supCredit = supCredit;
        this.paidAmount = paidAmount;
        this.transMode = transMode;
        this.description = description;
        this.bankName = bankName;
        this.accNo = accNo;
        this.accType = accType;
        this.bankBranch = bankBranch;
        this.ifscCode = ifscCode;
        this.swiftCode = swiftCode;
        this.remarks = remarks;
    }

    public String getId() { return id; }
    public String getPPayCode() { return pPayCode; }
    public LocalDate getEntryDate() { return entryDate; }
    public LocalDate getPaymentDate() { return paymentDate; }
    public String getPaymentName() { return paymentName; }
    public String getPaymentType() { return paymentType; }
    public String getSupName() { return supName; }
    public String getSupAddress() { return supAddress; }
    public String getSupPhone() { return supPhone; }
    public String getSupEmail() { return supEmail; }
    public Double getSupCredit() { return supCredit; }
    public Double getPaidAmount() { return paidAmount; }
    public String getTransMode() { return transMode; }
    public String getDescription() { return description; }
    public String getBankName() { return bankName; }
    public String getAccNo() { return accNo; }
    public String getAccType() { return accType; }
    public String getBankBranch() { return bankBranch; }
    public String getIfscCode() { return ifscCode; }
    public String getSwiftCode() { return swiftCode; }
    public String getRemarks() { return remarks; }

    public void setId(String id) { this.id = id; }
    public void setPPayCode(String pPayCode) { this.pPayCode = pPayCode; }
    public void setEntryDate(LocalDate entryDate) { this.entryDate = entryDate; }
    public void setPaymentDate(LocalDate paymentDate) { this.paymentDate = paymentDate; }
    public void setPaymentName(String paymentName) { this.paymentName = paymentName; }
    public void setPaymentType(String paymentType) { this.paymentType = paymentType; }
    public void setSupName(String supName) { this.supName = supName; }
    public void setSupAddress(String supAddress) { this.supAddress = supAddress; }
    public void setSupPhone(String supPhone) { this.supPhone = supPhone; }
    public void setSupEmail(String supEmail) { this.supEmail = supEmail; }
    public void setSupCredit(Double supCredit) { this.supCredit = supCredit; }
    public void setPaidAmount(Double paidAmount) { this.paidAmount = paidAmount; }
    public void setTransMode(String transMode) { this.transMode = transMode; }
    public void setDescription(String description) { this.description = description; }
    public void setBankName(String bankName) { this.bankName = bankName; }
    public void setAccNo(String accNo) { this.accNo = accNo; }
    public void setAccType(String accType) { this.accType = accType; }
    public void setBankBranch(String bankBranch) { this.bankBranch = bankBranch; }
    public void setIfscCode(String ifscCode) { this.ifscCode = ifscCode; }
    public void setSwiftCode(String swiftCode) { this.swiftCode = swiftCode; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
}