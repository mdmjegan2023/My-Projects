package com.fundtracker.dto;

public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String uid;
    private String username;
    private String userType;

    // No-arg constructor
    public JwtResponse() {}

    // The constructor used everywhere — does NOT set `type`, so it stays "Bearer"
    public JwtResponse(String token, String uid, String username, String userType) {
        this.token    = token;
        this.uid      = uid;
        this.username = username;
        this.userType = userType;
    }

    // Getters
    public String getToken()    { return token; }
    public String getType()     { return type; }
    public String getUid()      { return uid; }
    public String getUsername() { return username; }
    public String getUserType() { return userType; }

    // Setters
    public void setToken(String token)       { this.token    = token; }
    public void setType(String type)         { this.type     = type; }
    public void setUid(String uid)           { this.uid      = uid; }
    public void setUsername(String username) { this.username = username; }
    public void setUserType(String userType) { this.userType = userType; }
}
