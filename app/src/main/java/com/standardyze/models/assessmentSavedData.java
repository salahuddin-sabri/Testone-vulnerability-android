package com.standardyze.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class assessmentSavedData {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("assessmentUniqueId")
    @Expose
    private Integer assessmentUniqueId;
    @SerializedName("companyId")
    @Expose
    private Integer companyId;
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("propertyId")
    @Expose
    private Integer propertyId;

    @SerializedName("assessmetId")
    @Expose
    private Integer assessmetId;
    @SerializedName("departmentId")
    @Expose
    private Integer departmentId;
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
    @SerializedName("roles")
    @Expose
    private String role;
    @SerializedName("employees")
    @Expose
    private String employees;
    @SerializedName("general_infromations")
    @Expose
    private String generalInfromations;
    @SerializedName("datetime")
    @Expose
    private String datetime;

    public assessmentSavedData() {
    }

    public assessmentSavedData(Integer assessmentUniqueId, Integer companyId, Integer userId, Integer propertyId,
                               Integer assessmetId, Integer departmentId, String departmentName, String propertyName, String trainerName, String assessmentTitle, String status, Integer score,
                               String categoryScores, String roles, String employees,
                               String generalInfromations, String dateTime) {
       // this.id = id;
        this.assessmentUniqueId = assessmentUniqueId;
        this.companyId = companyId;
        this.userId = userId;
        this.propertyId = propertyId;
        this.assessmetId = assessmetId;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.propertyName = propertyName;
        this.trainerName = trainerName;
        this.assessmentTitle = assessmentTitle;
        this.status = status;
        this.score = score;
        this.categoryScores = categoryScores;
        this.role = roles;
        this.employees = employees;
        this.generalInfromations = generalInfromations;
        this.datetime = dateTime;
    }

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

    public String   getEmployees() {
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

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Integer propertyId) {
        this.propertyId = propertyId;
    }

    public Integer getAssessmetId() {
        return assessmetId;
    }

    public void setAssessmetId(Integer assessmetId) {
        this.assessmetId = assessmetId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getRoles() {
        return role;
    }

    public void setRoles(String roles) {
        this.role = roles;
    }
}
