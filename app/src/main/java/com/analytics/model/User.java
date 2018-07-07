package com.analytics.model;


import java.math.BigInteger;

public class User {


    private BigInteger id;
    private String accountLevel;
    private String lastName;
    private String firstName;
    private String email;
    private String password;
    private String created_at;
    private String newPassword;
    private String token;

    public void setName(String name) {
        this.lastName = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {this.lastName = lastName; }

    public String getEmail() {
        return email;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public BigInteger getId() { return id; }

    public void setId(BigInteger id) { this.id = id; }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }
}
