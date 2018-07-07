package com.analytics.model;


import java.math.BigInteger;

public class User {


    private BigInteger id;
    private String accountlevel;
    private String lastname;
    private String firstname;
    private String email;
    private String password;
    private String created_at;
    private String newPassword;
    private String token;

    public BigInteger getId() { return id; }

    public void setId(BigInteger id) { this.id = id; }

    public String getAccountLevel() { return accountlevel; }

    public void setAccountLevel(String accountlevel) { this.accountlevel = accountlevel;}

    public String getLastName() { return lastname; }

    public void setLastName(String lastname) {this.lastname = lastname; }

    public String getFirstName() { return firstname; }

    public void setFirstName(String firstName) { this.firstname = firstname; }

    public void setEmail(String email) { this.email = email; }

    public void setPassword(String password) {
        this.password = password;
    }



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


}
