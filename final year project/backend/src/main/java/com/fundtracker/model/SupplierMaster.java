package com.fundtracker.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

@Document(collection = "supplier_master")
public class SupplierMaster {

    @Id
    private String id;
    private String supCode;
    private String supName;
    private String supAddress;
    private String supPhone;
    private String supEmail;
    private String taxType;
    private String taxNo;
    private String dueDays;
    private String catName;
    private String remarks;

    public SupplierMaster() {}

    public SupplierMaster(String id, String supCode, String supName, String supAddress, String supPhone, String supEmail, String taxType, String taxNo, String dueDays, String catName, String remarks) {
        this.id = id;
        this.supCode = supCode;
        this.supName = supName;
        this.supAddress = supAddress;
        this.supPhone = supPhone;
        this.supEmail = supEmail;
        this.taxType = taxType;
        this.taxNo = taxNo;
        this.dueDays = dueDays;
        this.catName = catName;
        this.remarks = remarks;
    }

    public String getId() { return id; }
    public String getSupCode() { return supCode; }
    public String getSupName() { return supName; }
    public String getSupAddress() { return supAddress; }
    public String getSupPhone() { return supPhone; }
    public String getSupEmail() { return supEmail; }
    public String getTaxType() { return taxType; }
    public String getTaxNo() { return taxNo; }
    public String getDueDays() { return dueDays; }
    public String getCatName() { return catName; }
    public String getRemarks() { return remarks; }

    public void setId(String id) { this.id = id; }
    public void setSupCode(String supCode) { this.supCode = supCode; }
    public void setSupName(String supName) { this.supName = supName; }
    public void setSupAddress(String supAddress) { this.supAddress = supAddress; }
    public void setSupPhone(String supPhone) { this.supPhone = supPhone; }
    public void setSupEmail(String supEmail) { this.supEmail = supEmail; }
    public void setTaxType(String taxType) { this.taxType = taxType; }
    public void setTaxNo(String taxNo) { this.taxNo = taxNo; }
    public void setDueDays(String dueDays) { this.dueDays = dueDays; }
    public void setCatName(String catName) { this.catName = catName; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
}