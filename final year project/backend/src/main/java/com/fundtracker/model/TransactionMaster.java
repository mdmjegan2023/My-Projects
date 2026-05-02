package com.fundtracker.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "transaction_master")
public class TransactionMaster {

    @Id private String id;
    private String transCode;
    private String transName;
    private String catName;
    private String remarks;

    public TransactionMaster() {}

    public TransactionMaster(String id, String transCode, String transName, String catName, String remarks) {
        this.id = id;
        this.transCode = transCode;
        this.transName = transName;
        this.catName = catName;
        this.remarks = remarks;
    }

    public String getId() { return id; }
    public String getTransCode() { return transCode; }
    public String getTransName() { return transName; }
    public String getCatName() { return catName; }
    public String getRemarks() { return remarks; }

    public void setId(String id) { this.id = id; }
    public void setTransCode(String transCode) { this.transCode = transCode; }
    public void setTransName(String transName) { this.transName = transName; }
    public void setCatName(String catName) { this.catName = catName; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
}