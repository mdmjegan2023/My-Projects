package com.fundtracker.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "category_master")
public class CategoryMaster {

    @Id
    private String id;
    private String catCode;
    private String catName;
    private String remarks;

    public CategoryMaster() {}

    public CategoryMaster(String id, String catCode, String catName, String remarks) {
        this.id = id;
        this.catCode = catCode;
        this.catName = catName;
        this.remarks = remarks;
    }

    public String getId() { return id; }
    public String getCatCode() { return catCode; }
    public String getCatName() { return catName; }
    public String getRemarks() { return remarks; }

    public void setId(String id) { this.id = id; }
    public void setCatCode(String catCode) { this.catCode = catCode; }
    public void setCatName(String catName) { this.catName = catName; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
}