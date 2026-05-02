package com.fundtracker.dto;

import java.time.LocalDate;

public class ReportFilterRequest {

    private LocalDate fromDate;
    private LocalDate toDate;
    private LocalDate secondFromDate;
    private LocalDate secondToDate;
    private String filterType;
    private String filterValue;
    private String secondFilterValue;

    public ReportFilterRequest() {}

    public ReportFilterRequest(LocalDate fromDate, LocalDate toDate, LocalDate secondFromDate, LocalDate secondToDate, String filterType, String filterValue, String secondFilterValue) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.secondFromDate = secondFromDate;
        this.secondToDate = secondToDate;
        this.filterType = filterType;
        this.filterValue = filterValue;
        this.secondFilterValue = secondFilterValue;
    }

    public LocalDate getFromDate() { return fromDate; }
    public LocalDate getToDate() { return toDate; }
    public LocalDate getSecondFromDate() { return secondFromDate; }
    public LocalDate getSecondToDate() { return secondToDate; }
    public String getFilterType() { return filterType; }
    public String getFilterValue() { return filterValue; }
    public String getSecondFilterValue() { return secondFilterValue; }

    public void setFromDate(LocalDate fromDate) { this.fromDate = fromDate; }
    public void setToDate(LocalDate toDate) { this.toDate = toDate; }
    public void setSecondFromDate(LocalDate secondFromDate) { this.secondFromDate = secondFromDate; }
    public void setSecondToDate(LocalDate secondToDate) { this.secondToDate = secondToDate; }
    public void setFilterType(String filterType) { this.filterType = filterType; }
    public void setFilterValue(String filterValue) { this.filterValue = filterValue; }
    public void setSecondFilterValue(String secondFilterValue) { this.secondFilterValue = secondFilterValue; }
}