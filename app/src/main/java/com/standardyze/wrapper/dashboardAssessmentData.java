package com.standardyze.wrapper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;

public class dashboardAssessmentData {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("assessmentUniqueId")
    @Expose
    private Integer assessmentUniqueId;
    @SerializedName("departmentName")
    @Expose
    private String departmentName;
    @SerializedName("propertyName")
    @Expose
    private String propertyName;
    @SerializedName("trainerName")
    @Expose
    private String trainerName;
    @SerializedName("assessmentTitle")
    @Expose
    private String assessmentTitle;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("score")
    @Expose
    private Integer score;
    @SerializedName("category_scores")
    @Expose
    private String categoryScores;
    @SerializedName("employees")
    @Expose
    private String employees;
    @SerializedName("general_infromations")
    @Expose
    private String generalInfromations;
    @SerializedName("datetime")
    @Expose
    private String datetime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAssessmentUniqueId() {
        return assessmentUniqueId;
    }

    public void setAssessmentUniqueId(Integer assessmentUniqueId) {
        this.assessmentUniqueId = assessmentUniqueId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    public String getAssessmentTitle() {
        return assessmentTitle;
    }

    public void setAssessmentTitle(String assessmentTitle) {
        this.assessmentTitle = assessmentTitle;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getCategoryScores() {
        return categoryScores;
    }

    public void setCategoryScores(String categoryScores) {
        this.categoryScores = categoryScores;
    }

    public String getEmployees() {
        return employees;
    }

    public void setEmployees(String employees) {
        this.employees = employees;
    }

    public String getGeneralInfromations() {
        return generalInfromations;
    }

    public void setGeneralInfromations(String generalInfromations) {
        this.generalInfromations = generalInfromations;
    }

    public String  getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
