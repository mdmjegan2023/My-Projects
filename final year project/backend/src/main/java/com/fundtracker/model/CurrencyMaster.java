package com.fundtracker.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Document(collection = "currency_master")
public class CurrencyMaster {

    @Id private String id;
    private String currCode;
    private String currName;
    private String remarks;

    public CurrencyMaster() {}

    public CurrencyMaster(String id, String currCode, String currName, String remarks) {
        this.id = id;
        this.currCode = currCode;
        this.currName = currName;
        this.remarks = remarks;
    }

    public String getId() { return id; }
    public String getCurrCode() { return currCode; }
    public String getCurrName() { return currName; }
    public String getRemarks() { return remarks; }

    public void setId(String id) { this.id = id; }
    public void setCurrCode(String currCode) { this.currCode = currCode; }
    public void setCurrName(String currName) { this.currName = currName; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
}