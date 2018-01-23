package com.a123.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by SONI on 8/28/2017.
 */

public class AppointmentData implements Serializable {
    private static final long serialVersionUID = 124564564541L;
    private String status;
    private String message;
    private List<Info> info;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Info> getInfo() {
        return info;
    }

    public void setInfo(List<Info> info) {
        this.info = info;
    }

    public class Info implements Serializable {
        private static final long serialVersionUID = 1243456541L;
        private Appoitment appoitment;
        private Client client;
        private Property property;

        public Appoitment getAppoitment() {
            return appoitment;
        }

        public void setAppoitment(Appoitment appoitment) {
            this.appoitment = appoitment;
        }

        public Client getClient() {
            return client;
        }

        public void setClient(Client client) {
            this.client = client;
        }

        public Property getProperty() {
            return property;
        }

        public void setProperty(Property property) {
            this.property = property;
        }
    }


}
