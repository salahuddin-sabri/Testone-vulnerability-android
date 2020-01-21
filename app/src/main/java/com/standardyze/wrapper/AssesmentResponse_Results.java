package com.standardyze.wrapper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;

public  class AssesmentResponse_Results {
    @SerializedName("assessmentTblId")
    @Expose
    private Integer assessmentTblId;
    @SerializedName("assessmentTitle")
    @Expose
    private String assessmentTitle;
    @SerializedName("datetime")
    @Expose
    private String datetime;
    @SerializedName("roles")
    @Expose
    private HashMap<String,String> roles;
    @SerializedName("responseObject")
    @Expose
    private List<getAssessmentResponseObj> responseObject = null;

    public Integer getAssessmentTblId() {
        return assessmentTblId;
    }

    public void setAssessmentTblId(Integer assessmentTblId) {
        this.assessmentTblId = assessmentTblId;
    }

    public String getAssessmentTitle() {
        return assessmentTitle;
    }

    public void setAssessmentTitle(String assessmentTitle) {
        this.assessmentTitle = assessmentTitle;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public  HashMap<String,String> getRoles() {
        return roles;
    }

    public void setRoles( HashMap<String,String> roles) {
        this.roles = roles;
    }

    public List<getAssessmentResponseObj> getResponseObject() {
        return responseObject;
    }

    public void setResponseObject(List<getAssessmentResponseObj> responseObject) {
        this.responseObject = responseObject;
    }
}
