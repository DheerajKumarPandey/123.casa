package com.a123.model;

import java.io.Serializable;

/**
 * Created by SONI on 8/28/2017.
 */

public class Agent implements Serializable {
    private static final long serialVersionUID = 1454364541L;

    private String id;
    private String name;
    private String phoneno;

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

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }



}
