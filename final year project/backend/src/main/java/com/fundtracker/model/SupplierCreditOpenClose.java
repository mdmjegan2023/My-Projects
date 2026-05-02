package com.fundtracker.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "supplier_credit_openclose")
public class SupplierCreditOpenClose {
    @Id private String id;
    private String supName;
    private String supAddress;
    private String supPhone;
    private String supEmail;
    private Double supCredit;

    public SupplierCreditOpenClose() {}
    public SupplierCreditOpenClose(String id, String supName, String supAddress,
                                    String supPhone, String supEmail, Double supCredit) {
        this.id = id; this.supName = supName; this.supAddress = supAddress;
        this.supPhone = supPhone; this.supEmail = supEmail; this.supCredit = supCredit;
    }
    public String getId()         { return id; }
    public String getSupName()    { return supName; }
    public String getSupAddress() { return supAddress; }
    public String getSupPhone()   { return supPhone; }
    public String getSupEmail()   { return supEmail; }
    public Double getSupCredit()  { return supCredit; }
    public void setId(String id)                  { this.id = id; }
    public void setSupName(String supName)        { this.supName = supName; }
    public void setSupAddress(String supAddress)  { this.supAddress = supAddress; }
    public void setSupPhone(String supPhone)      { this.supPhone = supPhone; }
    public void setSupEmail(String supEmail)      { this.supEmail = supEmail; }
    public void setSupCredit(Double supCredit)    { this.supCredit = supCredit; }
}
