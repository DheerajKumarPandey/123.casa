package com.a123.adapter;

/**
 * Created by DJ-PC on 5/6/2017.
 */

public class DummyHotelItem {
    private Integer HotelImage;

    public Integer getHotelImage() {
        return HotelImage;
    }

    public void setHotelImage(Integer hotelImage) {
        HotelImage = hotelImage;
    }

    public String getSrNo() {
        return SrNo;
    }

    public void setSrNo(String srNo) {
        SrNo = srNo;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    private String SrNo;
    private String Date;
    private String Description;
    private String Address;
}
