package com.a123.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by SONI on 8/28/2017.
 */

public class UserAppointmentData implements Serializable {
    private static final long serialVersionUID = 1334564564541L;
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
        private static final long serialVersionUID = 11243456541L;
        private UserAppoitment appoitment;

        public UserAppoitment getAppoitment() {
            return appoitment;
        }

        public void setAppoitment(UserAppoitment appoitment) {
            this.appoitment = appoitment;
        }

        public Agent getAgent() {
            return agent;
        }

        public void setAgent(Agent agent) {
            this.agent = agent;
        }

        public UserProperty getProperty() {
            return property;
        }

        public void setProperty(UserProperty property) {
            this.property = property;
        }

        private Agent agent;
        private UserProperty property;





    }


}
