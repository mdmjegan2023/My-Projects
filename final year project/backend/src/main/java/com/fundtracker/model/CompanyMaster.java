package com.fundtracker.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "company_master")
public class CompanyMaster {

    @Id
    private String id;
    private String compCode;
    private String compName;
    private String compAddress;
    private String compPhone;
    private String compEmail;
    private String taxType;
    private String taxNo;
    private String remarks;

    public CompanyMaster() {}

    public CompanyMaster(String id, String compCode, String compName, String compAddress, String compPhone, String compEmail, String taxType, String taxNo, String remarks) {
        this.id = id;
        this.compCode = compCode;
        this.compName = compName;
        this.compAddress = compAddress;
        this.compPhone = compPhone;
        this.compEmail = compEmail;
        this.taxType = taxType;
        this.taxNo = taxNo;
        this.remarks = remarks;
    }

    public String getId() { return id; }
    public String getCompCode() { return compCode; }
    public String getCompName() { return compName; }
    public String getCompAddress() { return compAddress; }
    public String getCompPhone() { return compPhone; }
    public String getCompEmail() { return compEmail; }
    public String getTaxType() { return taxType; }
    public String getTaxNo() { return taxNo; }
    public String getRemarks() { return remarks; }

    public void setId(String id) { this.id = id; }
    public void setCompCode(String compCode) { this.compCode = compCode; }
    public void setCompName(String compName) { this.compName = compName; }
    public void setCompAddress(String compAddress) { this.compAddress = compAddress; }
    public void setCompPhone(String compPhone) { this.compPhone = compPhone; }
    public void setCompEmail(String compEmail) { this.compEmail = compEmail; }
    public void setTaxType(String taxType) { this.taxType = taxType; }
    public void setTaxNo(String taxNo) { this.taxNo = taxNo; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
}