package com.fundtracker.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "client_master")
public class ClientMaster {

    @Id
    private String id;
    private String clientCode;
    private String clientName;
    private String clientAddress;
    private String clientPhone;
    private String clientEmail;
    private String taxType;
    private String taxNo;
    private String remarks;

    public ClientMaster() {}

    public ClientMaster(String id, String clientCode, String clientName, String clientAddress, String clientPhone, String clientEmail, String taxType, String taxNo, String remarks) {
        this.id = id;
        this.clientCode = clientCode;
        this.clientName = clientName;
        this.clientAddress = clientAddress;
        this.clientPhone = clientPhone;
        this.clientEmail = clientEmail;
        this.taxType = taxType;
        this.taxNo = taxNo;
        this.remarks = remarks;
    }

    public String getId() { return id; }
    public String getClientCode() { return clientCode; }
    public String getClientName() { return clientName; }
    public String getClientAddress() { return clientAddress; }
    public String getClientPhone() { return clientPhone; }
    public String getClientEmail() { return clientEmail; }
    public String getTaxType() { return taxType; }
    public String getTaxNo() { return taxNo; }
    public String getRemarks() { return remarks; }

    public void setId(String id) { this.id = id; }
    public void setClientCode(String clientCode) { this.clientCode = clientCode; }
    public void setClientName(String clientName) { this.clientName = clientName; }
    public void setClientAddress(String clientAddress) { this.clientAddress = clientAddress; }
    public void setClientPhone(String clientPhone) { this.clientPhone = clientPhone; }
    public void setClientEmail(String clientEmail) { this.clientEmail = clientEmail; }
    public void setTaxType(String taxType) { this.taxType = taxType; }
    public void setTaxNo(String taxNo) { this.taxNo = taxNo; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
}