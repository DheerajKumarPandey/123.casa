package com.a123.model;

import java.io.Serializable;

/**
 * Created by SONI on 8/28/2017.
 */

public class Property implements Serializable {
    private static final long serialVersionUID = 164564541L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType_of_operation() {
        return type_of_operation;
    }

    public void setType_of_operation(String type_of_operation) {
        this.type_of_operation = type_of_operation;
    }

    public String getType_of_property() {
        return type_of_property;
    }

    public void setType_of_property(String type_of_property) {
        this.type_of_property = type_of_property;
    }

    private String id;
    private String type_of_operation;
    private String type_of_property;

}
