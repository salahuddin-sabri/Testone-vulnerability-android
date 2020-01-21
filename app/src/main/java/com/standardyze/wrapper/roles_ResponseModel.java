package com.standardyze.wrapper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class roles_ResponseModel {
    @SerializedName("rolea")
    @Expose
    private String rolea;
    @SerializedName("roleb")
    @Expose
    private String roleb;
    @SerializedName("rolec")
    @Expose
    private String rolec;

    public String getRolea() {
        return rolea;
    }

    public void setRolea(String rolea) {
        this.rolea = rolea;
    }

    public String getRoleb() {
        return roleb;
    }

    public void setRoleb(String roleb) {
        this.roleb = roleb;
    }

    public String getRolec() {
        return rolec;
    }

    public void setRolec(String rolec) {
        this.rolec = rolec;
    }

}
