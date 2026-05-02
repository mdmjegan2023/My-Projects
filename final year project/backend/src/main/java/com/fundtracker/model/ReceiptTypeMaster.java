package com.fundtracker.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "receipttype_master")
public class ReceiptTypeMaster {

    @Id private String id;
    private String receiptTypeCode;
    private String receiptTypeName;
    private String catName;
    private String remarks;

    public ReceiptTypeMaster() {}

    public ReceiptTypeMaster(String id, String receiptTypeCode, String receiptTypeName, String catName, String remarks) {
        this.id = id;
        this.receiptTypeCode = receiptTypeCode;
        this.receiptTypeName = receiptTypeName;
        this.catName = catName;
        this.remarks = remarks;
    }

    public String getId() { return id; }
    public String getReceiptTypeCode() { return receiptTypeCode; }
    public String getReceiptTypeName() { return receiptTypeName; }
    public String getCatName() { return catName; }
    public String getRemarks() { return remarks; }

    public void setId(String id) { this.id = id; }
    public void setReceiptTypeCode(String receiptTypeCode) { this.receiptTypeCode = receiptTypeCode; }
    public void setReceiptTypeName(String receiptTypeName) { this.receiptTypeName = receiptTypeName; }
    public void setCatName(String catName) { this.catName = catName; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
}