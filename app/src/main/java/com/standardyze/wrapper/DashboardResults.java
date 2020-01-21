package com.standardyze.wrapper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DashboardResults {
    @SerializedName("assessments")
    @Expose
    private List<dashboardAssessmentData> assessments = null;
    @SerializedName("pagination")
    @Expose
    private Pagination pagination;

    public List<dashboardAssessmentData> getAssessments() {
        return assessments;
    }

    public void setAssessments(List<dashboardAssessmentData> assessments) {
        this.assessments = assessments;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
