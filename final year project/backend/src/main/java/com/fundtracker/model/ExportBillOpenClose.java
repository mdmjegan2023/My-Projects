package com.fundtracker.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "export_bill_openclose")
public class ExportBillOpenClose {
    @Id private String id;
    private String iExpCode;   // invoice doc code
    private String rExpCode;   // receipt doc code (set when receipt is recorded)
    private String invoiceNo;
    private Integer totalPair;
    private String status;

    public ExportBillOpenClose() {}
    public ExportBillOpenClose(String id, String iExpCode, String invoiceNo, Integer totalPair, String status) {
        this.id = id; this.iExpCode = iExpCode; this.invoiceNo = invoiceNo;
        this.totalPair = totalPair; this.status = status;
    }
    public String getId()        { return id; }
    public String getIExpCode()  { return iExpCode; }
    public String getRExpCode()  { return rExpCode; }
    public String getInvoiceNo() { return invoiceNo; }
    public Integer getTotalPair(){ return totalPair; }
    public String getStatus()    { return status; }
    public void setId(String id)               { this.id = id; }
    public void setIExpCode(String iExpCode)   { this.iExpCode = iExpCode; }
    public void setRExpCode(String rExpCode)   { this.rExpCode = rExpCode; }
    public void setInvoiceNo(String invoiceNo) { this.invoiceNo = invoiceNo; }
    public void setTotalPair(Integer v)        { this.totalPair = v; }
    public void setStatus(String status)       { this.status = status; }
}
