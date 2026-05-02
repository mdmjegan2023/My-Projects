package com.fundtracker.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "dbk_incentives_openclose")
public class DbkIncentivesOpenClose {
    @Id private String id;
    private String iExpCode;
    private String invoiceNo;
    private String docNo;
    private String dbk;
    private String status;

    public DbkIncentivesOpenClose() {}
    public DbkIncentivesOpenClose(String id, String iExpCode, String invoiceNo,
                                   String docNo, String dbk, String status) {
        this.id = id; this.iExpCode = iExpCode; this.invoiceNo = invoiceNo;
        this.docNo = docNo; this.dbk = dbk; this.status = status;
    }
    public String getId()        { return id; }
    public String getIExpCode()  { return iExpCode; }
    public String getInvoiceNo() { return invoiceNo; }
    public String getDocNo()     { return docNo; }
    public String getDbk()       { return dbk; }
    public String getStatus()    { return status; }
    public void setId(String id)              { this.id = id; }
    public void setIExpCode(String iExpCode)  { this.iExpCode = iExpCode; }
    public void setInvoiceNo(String invoiceNo){ this.invoiceNo = invoiceNo; }
    public void setDocNo(String docNo)        { this.docNo = docNo; }
    public void setDbk(String dbk)            { this.dbk = dbk; }
    public void setStatus(String status)      { this.status = status; }
}
