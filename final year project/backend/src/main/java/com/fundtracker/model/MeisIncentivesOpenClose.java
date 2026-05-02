package com.fundtracker.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "meis_incentives_openclose")
public class MeisIncentivesOpenClose {
    @Id private String id;
    private String iExpCode;
    private String invoiceNo;
    private String docNo;
    private String meis;
    private String status;

    public MeisIncentivesOpenClose() {}
    public MeisIncentivesOpenClose(String id, String iExpCode, String invoiceNo,
                                    String docNo, String meis, String status) {
        this.id = id; this.iExpCode = iExpCode; this.invoiceNo = invoiceNo;
        this.docNo = docNo; this.meis = meis; this.status = status;
    }
    public String getId()        { return id; }
    public String getIExpCode()  { return iExpCode; }
    public String getInvoiceNo() { return invoiceNo; }
    public String getDocNo()     { return docNo; }
    public String getMeis()      { return meis; }
    public String getStatus()    { return status; }
    public void setId(String id)              { this.id = id; }
    public void setIExpCode(String iExpCode)  { this.iExpCode = iExpCode; }
    public void setInvoiceNo(String invoiceNo){ this.invoiceNo = invoiceNo; }
    public void setDocNo(String docNo)        { this.docNo = docNo; }
    public void setMeis(String meis)          { this.meis = meis; }
    public void setStatus(String status)      { this.status = status; }
}
