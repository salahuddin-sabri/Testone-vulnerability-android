package com.standardyze.wrapper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AssessmentResult {
    @SerializedName("allpurposerestaurant")
    @Expose
    private String allpurposerestaurant;
    @SerializedName("specialtyrestaurant")
    @Expose
    private String specialtyrestaurant;
    @SerializedName("barlounge")
    @Expose
    private String barlounge;
    @SerializedName("roomservice")
    @Expose
    private String roomservice;
    @SerializedName("pool")
    @Expose
    private String pool;
    @SerializedName("culinary")
    @Expose
    private String culinary;

    public String getAllpurposerestaurant() {
        return allpurposerestaurant;
    }

    public void setAllpurposerestaurant(String allpurposerestaurant) {
        this.allpurposerestaurant = allpurposerestaurant;
    }

    public String getSpecialtyrestaurant() {
        return specialtyrestaurant;
    }

    public void setSpecialtyrestaurant(String specialtyrestaurant) {
        this.specialtyrestaurant = specialtyrestaurant;
    }

    public String getBarlounge() {
        return barlounge;
    }

    public void setBarlounge(String barlounge) {
        this.barlounge = barlounge;
    }

    public String getRoomservice() {
        return roomservice;
    }

    public void setRoomservice(String roomservice) {
        this.roomservice = roomservice;
    }

    public String getPool() {
        return pool;
    }

    public void setPool(String pool) {
        this.pool = pool;
    }

    public String getCulinary() {
        return culinary;
    }

    public void setCulinary(String culinary) {
        this.culinary = culinary;
    }


}
