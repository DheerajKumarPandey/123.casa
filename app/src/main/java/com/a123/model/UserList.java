package com.a123.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by SONI on 8/28/2017.
 */

public class UserList implements Serializable {
    private static final long serialVersionUID = 12456541L;

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

    private String status;
    private String message;
    private List<Info> info;

    public class Info implements Serializable {
        private static final long serialVersionUID = 12456541L;
        private String id;
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType_of_property() {
            return type_of_property;
        }

        public void setType_of_property(String type_of_property) {
            this.type_of_property = type_of_property;
        }

        public String getProperty_variant() {
            return property_variant;
        }

        public void setProperty_variant(String property_variant) {
            this.property_variant = property_variant;
        }

        public String getType_of_operation() {
            return type_of_operation;
        }

        public void setType_of_operation(String type_of_operation) {
            this.type_of_operation = type_of_operation;
        }

        public String getMin_price() {
            return min_price;
        }

        public void setMin_price(String min_price) {
            this.min_price = min_price;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getMax_price() {
            return max_price;
        }

        public void setMax_price(String max_price) {
            this.max_price = max_price;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getKeys() {
            return keys;
        }

        public void setKeys(String keys) {
            this.keys = keys;
        }

        public String getAppointment() {
            return appointment;
        }

        public void setAppointment(String appointment) {
            this.appointment = appointment;
        }

        public String getOpen() {
            return open;
        }

        public void setOpen(String open) {
            this.open = open;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public String getThumbnailImage() {
            return thumbnailImage;
        }

        public void setThumbnailImage(String thumbnailImage) {
            this.thumbnailImage = thumbnailImage;
        }

        private String type_of_property;
        private String property_variant;
        private String type_of_operation;
        private String min_price;
        private String city;
        private String address;
        private String max_price;
        private String latitude;
        private String longitude;
        private String keys;
        private String appointment;
        private String open;
        private String created_at;
        private String updated_at;
        private String thumbnailImage;
        /*private List<PropertyImages>property_images;
        public class PropertyImages implements Serializable{

        }*/

    }


}
