package com.fundtracker.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {

    @NotBlank
    private String uid;

    @NotBlank
    private String password;

    // No-arg constructor
    public LoginRequest() {}

    // All-args constructor
    public LoginRequest(String uid, String password) {
        this.uid      = uid;
        this.password = password;
    }

    // Getters
    public String getUid()      { return uid; }
    public String getPassword() { return password; }

    // Setters
    public void setUid(String uid)           { this.uid      = uid; }
    public void setPassword(String password) { this.password = password; }
}
