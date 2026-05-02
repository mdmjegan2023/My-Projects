package com.fundtracker.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "retail_bill_openclose")
public class RetailBillOpenClose {
    @Id private String id;
    private String iRtlCode;   // invoice doc code
    private String rRtlCode;   // receipt doc code (set when receipt is recorded)
    private String invoiceNo;
    private Integer totalPair;
    private String status;

    public RetailBillOpenClose() {}
    public RetailBillOpenClose(String id, String iRtlCode, String invoiceNo, Integer totalPair, String status) {
        this.id = id; this.iRtlCode = iRtlCode; this.invoiceNo = invoiceNo;
        this.totalPair = totalPair; this.status = status;
    }
    public String getId()        { return id; }
    public String getIRtlCode()  { return iRtlCode; }
    public String getRRtlCode()  { return rRtlCode; }
    public String getInvoiceNo() { return invoiceNo; }
    public Integer getTotalPair(){ return totalPair; }
    public String getStatus()    { return status; }
    public void setId(String id)               { this.id = id; }
    public void setIRtlCode(String iRtlCode)   { this.iRtlCode = iRtlCode; }
    public void setRRtlCode(String rRtlCode)   { this.rRtlCode = rRtlCode; }
    public void setInvoiceNo(String invoiceNo) { this.invoiceNo = invoiceNo; }
    public void setTotalPair(Integer totalPair){ this.totalPair = totalPair; }
    public void setStatus(String status)       { this.status = status; }
}
