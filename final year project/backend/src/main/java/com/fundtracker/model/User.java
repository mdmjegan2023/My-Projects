package com.fundtracker.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

@Document(collection = "users")
public class User {

    @Id
    private String id;

    @Indexed(unique = true)
    private String uid;       // UID from original: users.UID

    private String username;  // UName from original: users.UName
    private String password;  // Pass from original: users.Pass (hashed in new system)
    private String userType;  // UType: Administrator, Admin, User
    private String remarks;

    // No-arg constructor
    public User() {}

    // All-args constructor
    public User(String id, String uid, String username, String password,
                String userType, String remarks) {
        this.id       = id;
        this.uid      = uid;
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.remarks  = remarks;
    }

    // Getters
    public String getId()       { return id; }
    public String getUid()      { return uid; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getUserType() { return userType; }
    public String getRemarks()  { return remarks; }

    // Setters  ← these are what UserService calls; missing setters = "method undefined" error
    public void setId(String id)             { this.id       = id; }
    public void setUid(String uid)           { this.uid      = uid; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setUserType(String userType) { this.userType = userType; }
    public void setRemarks(String remarks)   { this.remarks  = remarks; }
}
