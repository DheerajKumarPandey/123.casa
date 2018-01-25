package com.a123.model;

import java.io.Serializable;

/**
 * Created by SONI on 8/28/2017.
 */

public class UserAppoitment implements Serializable {
    private static final long serialVersionUID = 15264564541L;
    private String id;
    private String appointment_time;
    private String appointment_description;
    private String phone_no;
    private String is_accepted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppointment_time() {
        return appointment_time;
    }

    public void setAppointment_time(String appointment_time) {
        this.appointment_time = appointment_time;
    }

    public String getAppointment_description() {
        return appointment_description;
    }

    public void setAppointment_description(String appointment_description) {
        this.appointment_description = appointment_description;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getIs_accepted() {
        return is_accepted;
    }

    public void setIs_accepted(String is_accepted) {
        this.is_accepted = is_accepted;
    }




}
