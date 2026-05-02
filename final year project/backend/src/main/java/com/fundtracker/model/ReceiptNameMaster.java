package com.fundtracker.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "receiptname_master")
public class ReceiptNameMaster {

    @Id private String id;
    private String receiptCode;
    private String receiptName;
    private String remarks;

    public ReceiptNameMaster() {}

    public ReceiptNameMaster(String id, String receiptCode, String receiptName, String remarks) {
        this.id = id;
        this.receiptCode = receiptCode;
        this.receiptName = receiptName;
        this.remarks = remarks;
    }

    public String getId() { return id; }
    public String getReceiptCode() { return receiptCode; }
    public String getReceiptName() { return receiptName; }
    public String getRemarks() { return remarks; }

    public void setId(String id) { this.id = id; }
    public void setReceiptCode(String receiptCode) { this.receiptCode = receiptCode; }
    public void setReceiptName(String receiptName) { this.receiptName = receiptName; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
}