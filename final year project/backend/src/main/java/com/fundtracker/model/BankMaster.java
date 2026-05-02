package com.fundtracker.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

@Document(collection = "bank_master")
public class BankMaster {

    @Id
    private String id;
    private String bankCode;
    private String bankName;
    private String bankBranch;
    private String accType;
    private String accNo;
    private String ifscCode;
    private String swiftCode;
    private String remarks;

    public BankMaster() {}

    public BankMaster(String id, String bankCode, String bankName, String bankBranch, String accType, String accNo, String ifscCode, String swiftCode, String remarks) {
        this.id = id;
        this.bankCode = bankCode;
        this.bankName = bankName;
        this.bankBranch = bankBranch;
        this.accType = accType;
        this.accNo = accNo;
        this.ifscCode = ifscCode;
        this.swiftCode = swiftCode;
        this.remarks = remarks;
    }

    public String getId() { return id; }
    public String getBankCode() { return bankCode; }
    public String getBankName() { return bankName; }
    public String getBankBranch() { return bankBranch; }
    public String getAccType() { return accType; }
    public String getAccNo() { return accNo; }
    public String getIfscCode() { return ifscCode; }
    public String getSwiftCode() { return swiftCode; }
    public String getRemarks() { return remarks; }

    public void setId(String id) { this.id = id; }
    public void setBankCode(String bankCode) { this.bankCode = bankCode; }
    public void setBankName(String bankName) { this.bankName = bankName; }
    public void setBankBranch(String bankBranch) { this.bankBranch = bankBranch; }
    public void setAccType(String accType) { this.accType = accType; }
    public void setAccNo(String accNo) { this.accNo = accNo; }
    public void setIfscCode(String ifscCode) { this.ifscCode = ifscCode; }
    public void setSwiftCode(String swiftCode) { this.swiftCode = swiftCode; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
}