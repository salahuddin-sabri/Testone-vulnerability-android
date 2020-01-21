package com.standardyze.wrapper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AssmessmentModel {

@SerializedName("id")
@Expose
private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("companyId")
    @Expose
    private Integer companyId;
    @SerializedName("departmentId")
    @Expose
    private Integer departmentId;
    @SerializedName("rolesId")
    @Expose
    private Integer rolesId;
    @SerializedName("assessmentKey")
    @Expose
    private String assessmentKey;
    @SerializedName("action")
    @Expose
    private  String action;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getRolesId() {
        return rolesId;
    }

    public void setRolesId(Integer rolesId) {
        this.rolesId = rolesId;
    }

    public String getAssessmentKey() {
        return assessmentKey;
    }

    public void setAssessmentKey(String assessmentKey) {
        this.assessmentKey = assessmentKey;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}