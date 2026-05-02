package com.fundtracker.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Document(collection = "retail_invoice")
public class RetailInvoice {

    @Id private String id;
    private String iRtlCode;
    private LocalDate entryDate;
    private String invoiceNo;
    private LocalDate invoiceDate;
    private String clientName;
    private String poNo;
    private Integer totalPair;
    private String inrCurrencyType;
    private Double inrExchangeRate;
    private Double inrAmount;
    private Double sgstPercent;
    private Double sgstValue;
    private Double cgstPercent;
    private Double cgstValue;
    private Double igstPercent;
    private Double igstValue;
    private String status;
    private String remarks;

    public RetailInvoice() {}

    public RetailInvoice(String id, String iRtlCode, LocalDate entryDate, String invoiceNo, LocalDate invoiceDate, String clientName, String poNo, Integer totalPair, String inrCurrencyType, Double inrExchangeRate, Double inrAmount, Double sgstPercent, Double sgstValue, Double cgstPercent, Double cgstValue, Double igstPercent, Double igstValue, String status, String remarks) {
        this.id = id;
        this.iRtlCode = iRtlCode;
        this.entryDate = entryDate;
        this.invoiceNo = invoiceNo;
        this.invoiceDate = invoiceDate;
        this.clientName = clientName;
        this.poNo = poNo;
        this.totalPair = totalPair;
        this.inrCurrencyType = inrCurrencyType;
        this.inrExchangeRate = inrExchangeRate;
        this.inrAmount = inrAmount;
        this.sgstPercent = sgstPercent;
        this.sgstValue = sgstValue;
        this.cgstPercent = cgstPercent;
        this.cgstValue = cgstValue;
        this.igstPercent = igstPercent;
        this.igstValue = igstValue;
        this.status = status;
        this.remarks = remarks;
    }

    public String getId() { return id; }
    public String getIRtlCode() { return iRtlCode; }
    public LocalDate getEntryDate() { return entryDate; }
    public String getInvoiceNo() { return invoiceNo; }
    public LocalDate getInvoiceDate() { return invoiceDate; }
    public String getClientName() { return clientName; }
    public String getPoNo() { return poNo; }
    public Integer getTotalPair() { return totalPair; }
    public String getInrCurrencyType() { return inrCurrencyType; }
    public Double getInrExchangeRate() { return inrExchangeRate; }
    public Double getInrAmount() { return inrAmount; }
    public Double getSgstPercent() { return sgstPercent; }
    public Double getSgstValue() { return sgstValue; }
    public Double getCgstPercent() { return cgstPercent; }
    public Double getCgstValue() { return cgstValue; }
    public Double getIgstPercent() { return igstPercent; }
    public Double getIgstValue() { return igstValue; }
    public String getStatus() { return status; }
    public String getRemarks() { return remarks; }

    public void setId(String id) { this.id = id; }
    public void setIRtlCode(String iRtlCode) { this.iRtlCode = iRtlCode; }
    public void setEntryDate(LocalDate entryDate) { this.entryDate = entryDate; }
    public void setInvoiceNo(String invoiceNo) { this.invoiceNo = invoiceNo; }
    public void setInvoiceDate(LocalDate invoiceDate) { this.invoiceDate = invoiceDate; }
    public void setClientName(String clientName) { this.clientName = clientName; }
    public void setPoNo(String poNo) { this.poNo = poNo; }
    public void setTotalPair(Integer totalPair) { this.totalPair = totalPair; }
    public void setInrCurrencyType(String inrCurrencyType) { this.inrCurrencyType = inrCurrencyType; }
    public void setInrExchangeRate(Double inrExchangeRate) { this.inrExchangeRate = inrExchangeRate; }
    public void setInrAmount(Double inrAmount) { this.inrAmount = inrAmount; }
    public void setSgstPercent(Double sgstPercent) { this.sgstPercent = sgstPercent; }
    public void setSgstValue(Double sgstValue) { this.sgstValue = sgstValue; }
    public void setCgstPercent(Double cgstPercent) { this.cgstPercent = cgstPercent; }
    public void setCgstValue(Double cgstValue) { this.cgstValue = cgstValue; }
    public void setIgstPercent(Double igstPercent) { this.igstPercent = igstPercent; }
    public void setIgstValue(Double igstValue) { this.igstValue = igstValue; }
    public void setStatus(String status) { this.status = status; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
}