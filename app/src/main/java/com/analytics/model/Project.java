package com.analytics.model;

import java.math.BigInteger;

public class Project {

    private BigInteger id;
    private String name;
    private String urlfollowed;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlfollowed() {
        return urlfollowed;
    }

    public void setUrlfollowed(String urlfollowed) {
        this.urlfollowed = urlfollowed;
    }


}
