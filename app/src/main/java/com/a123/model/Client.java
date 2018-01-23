package com.a123.model;

import java.io.Serializable;

/**
 * Created by SONI on 8/28/2017.
 */

public class Client implements Serializable {
    private static final long serialVersionUID = 164564541L;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private String name;
    private String address;


}
