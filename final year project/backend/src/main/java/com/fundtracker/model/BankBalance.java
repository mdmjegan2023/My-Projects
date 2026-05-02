package com.fundtracker.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Document(collection = "bank_balance")
public class BankBalance {
    @Id private String id;
    private String bankName;
    private String accNo;
    private String accType;
    private String bankBranch;
    private String ifscCode;
    private String swiftCode;
    private Double balance;

    public BankBalance() {}
    public BankBalance(String id, String bankName, String accNo, String accType,
                       String bankBranch, String ifscCode, String swiftCode, Double balance) {
        this.id = id; this.bankName = bankName; this.accNo = accNo; this.accType = accType;
        this.bankBranch = bankBranch; this.ifscCode = ifscCode; this.swiftCode = swiftCode;
        this.balance = balance;
    }
    public String getId()         { return id; }
    public String getBankName()   { return bankName; }
    public String getAccNo()      { return accNo; }
    public String getAccType()    { return accType; }
    public String getBankBranch() { return bankBranch; }
    public String getIfscCode()   { return ifscCode; }
    public String getSwiftCode()  { return swiftCode; }
    public Double getBalance()    { return balance; }
    public void setId(String id)              { this.id = id; }
    public void setBankName(String bankName)  { this.bankName = bankName; }
    public void setAccNo(String accNo)        { this.accNo = accNo; }
    public void setAccType(String accType)    { this.accType = accType; }
    public void setBankBranch(String v)       { this.bankBranch = v; }
    public void setIfscCode(String v)         { this.ifscCode = v; }
    public void setSwiftCode(String v)        { this.swiftCode = v; }
    public void setBalance(Double balance)    { this.balance = balance; }
}
