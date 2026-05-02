package com.fundtracker.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Document(collection = "export_receipt")
public class ExportReceipt {

    @Id private String id;
    private String rExpCode;
    private LocalDate entryDate;
    private String invoiceType;
    private Double discountPercent;
    private String invoiceNo;
    private LocalDate invoiceDate;
    private String clientName;
    private String poNo;
    private Integer totalPair;
    private String otrCurrencyType;
    private Double otrExchangeRate;
    private Double otrAmount;
    private String inrCurrencyType;
    private Double inrExchangeRate;
    private Double inrAmount;
    private Double sgstPercent;
    private Double sgstValue;
    private Double cgstPercent;
    private Double cgstValue;
    private Double igstPercent;
    private Double igstValue;
    private String transMode;
    private String bankName;
    private String accNo;
    private String accType;
    private String bankBranch;
    private String ifscCode;
    private String swiftCode;
    private String status;
    private String remarks;

    public ExportReceipt() {}

    public ExportReceipt(String id, String rExpCode, LocalDate entryDate, String invoiceType, Double discountPercent, String invoiceNo, LocalDate invoiceDate, String clientName, String poNo, Integer totalPair, String otrCurrencyType, Double otrExchangeRate, Double otrAmount, String inrCurrencyType, Double inrExchangeRate, Double inrAmount, Double sgstPercent, Double sgstValue, Double cgstPercent, Double cgstValue, Double igstPercent, Double igstValue, String transMode, String bankName, String accNo, String accType, String bankBranch, String ifscCode, String swiftCode, String status, String remarks) {
        this.id = id;
        this.rExpCode = rExpCode;
        this.entryDate = entryDate;
        this.invoiceType = invoiceType;
        this.discountPercent = discountPercent;
        this.invoiceNo = invoiceNo;
        this.invoiceDate = invoiceDate;
        this.clientName = clientName;
        this.poNo = poNo;
        this.totalPair = totalPair;
        this.otrCurrencyType = otrCurrencyType;
        this.otrExchangeRate = otrExchangeRate;
        this.otrAmount = otrAmount;
        this.inrCurrencyType = inrCurrencyType;
        this.inrExchangeRate = inrExchangeRate;
        this.inrAmount = inrAmount;
        this.sgstPercent = sgstPercent;
        this.sgstValue = sgstValue;
        this.cgstPercent = cgstPercent;
        this.cgstValue = cgstValue;
        this.igstPercent = igstPercent;
        this.igstValue = igstValue;
        this.transMode = transMode;
        this.bankName = bankName;
        this.accNo = accNo;
        this.accType = accType;
        this.bankBranch = bankBranch;
        this.ifscCode = ifscCode;
        this.swiftCode = swiftCode;
        this.status = status;
        this.remarks = remarks;
    }

    public String getId() { return id; }
    public String getRExpCode() { return rExpCode; }
    public LocalDate getEntryDate() { return entryDate; }
    public String getInvoiceType() { return invoiceType; }
    public Double getDiscountPercent() { return discountPercent; }
    public String getInvoiceNo() { return invoiceNo; }
    public LocalDate getInvoiceDate() { return invoiceDate; }
    public String getClientName() { return clientName; }
    public String getPoNo() { return poNo; }
    public Integer getTotalPair() { return totalPair; }
    public String getOtrCurrencyType() { return otrCurrencyType; }
    public Double getOtrExchangeRate() { return otrExchangeRate; }
    public Double getOtrAmount() { return otrAmount; }
    public String getInrCurrencyType() { return inrCurrencyType; }
    public Double getInrExchangeRate() { return inrExchangeRate; }
    public Double getInrAmount() { return inrAmount; }
    public Double getSgstPercent() { return sgstPercent; }
    public Double getSgstValue() { return sgstValue; }
    public Double getCgstPercent() { return cgstPercent; }
    public Double getCgstValue() { return cgstValue; }
    public Double getIgstPercent() { return igstPercent; }
    public Double getIgstValue() { return igstValue; }
    public String getTransMode() { return transMode; }
    public String getBankName() { return bankName; }
    public String getAccNo() { return accNo; }
    public String getAccType() { return accType; }
    public String getBankBranch() { return bankBranch; }
    public String getIfscCode() { return ifscCode; }
    public String getSwiftCode() { return swiftCode; }
    public String getStatus() { return status; }
    public String getRemarks() { return remarks; }

    public void setId(String id) { this.id = id; }
    public void setRExpCode(String rExpCode) { this.rExpCode = rExpCode; }
    public void setEntryDate(LocalDate entryDate) { this.entryDate = entryDate; }
    public void setInvoiceType(String invoiceType) { this.invoiceType = invoiceType; }
    public void setDiscountPercent(Double discountPercent) { this.discountPercent = discountPercent; }
    public void setInvoiceNo(String invoiceNo) { this.invoiceNo = invoiceNo; }
    public void setInvoiceDate(LocalDate invoiceDate) { this.invoiceDate = invoiceDate; }
    public void setClientName(String clientName) { this.clientName = clientName; }
    public void setPoNo(String poNo) { this.poNo = poNo; }
    public void setTotalPair(Integer totalPair) { this.totalPair = totalPair; }
    public void setOtrCurrencyType(String otrCurrencyType) { this.otrCurrencyType = otrCurrencyType; }
    public void setOtrExchangeRate(Double otrExchangeRate) { this.otrExchangeRate = otrExchangeRate; }
    public void setOtrAmount(Double otrAmount) { this.otrAmount = otrAmount; }
    public void setInrCurrencyType(String inrCurrencyType) { this.inrCurrencyType = inrCurrencyType; }
    public void setInrExchangeRate(Double inrExchangeRate) { this.inrExchangeRate = inrExchangeRate; }
    public void setInrAmount(Double inrAmount) { this.inrAmount = inrAmount; }
    public void setSgstPercent(Double sgstPercent) { this.sgstPercent = sgstPercent; }
    public void setSgstValue(Double sgstValue) { this.sgstValue = sgstValue; }
    public void setCgstPercent(Double cgstPercent) { this.cgstPercent = cgstPercent; }
    public void setCgstValue(Double cgstValue) { this.cgstValue = cgstValue; }
    public void setIgstPercent(Double igstPercent) { this.igstPercent = igstPercent; }
    public void setIgstValue(Double igstValue) { this.igstValue = igstValue; }
    public void setTransMode(String transMode) { this.transMode = transMode; }
    public void setBankName(String bankName) { this.bankName = bankName; }
    public void setAccNo(String accNo) { this.accNo = accNo; }
    public void setAccType(String accType) { this.accType = accType; }
    public void setBankBranch(String bankBranch) { this.bankBranch = bankBranch; }
    public void setIfscCode(String ifscCode) { this.ifscCode = ifscCode; }
    public void setSwiftCode(String swiftCode) { this.swiftCode = swiftCode; }
    public void setStatus(String status) { this.status = status; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
}