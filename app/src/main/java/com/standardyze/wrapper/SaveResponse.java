package com.standardyze.wrapper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SaveResponse
{
    @SerializedName("assessmentUniqueId")
    @Expose
    private Integer assessmentUniqueId;
    @SerializedName("companyId")
    @Expose
    private Integer companyId;
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("departmentName")
    @Expose
    private String departmentName;
    @SerializedName("propertyName")
    @Expose
    private String propertyName;
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
    private CategoryScores categoryScores;
    @SerializedName("roles")
    @Expose
    private roles_ResponseModel roles;
    @SerializedName("propertyId")
    @Expose
    private Integer propertyId;
    @SerializedName("assessmetId")
    @Expose
    private Integer assessmetId;
    @SerializedName("departmentId")
    @Expose
    private Integer departmentId;
    @SerializedName("employees")
    @Expose
    private String employees;
    @SerializedName("general_infromations")
    private General_infromations generalInfromations;

    @Expose
    private List<saveResponseObject> saveResponseObjectresults = null;
    @SerializedName("responseObject")


    public Integer getAssessmentUniqueId() {
        return assessmentUniqueId;
    }

    public void setAssessmentUniqueId(Integer assessmentUniqueId) {
        this.assessmentUniqueId = assessmentUniqueId;
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

    public CategoryScores getCategoryScores() {
        return categoryScores;
    }

    public void setCategoryScores(CategoryScores categoryScores) {
        this.categoryScores = categoryScores;
    }

    public roles_ResponseModel getRoles() {
        return roles;
    }

    public void setRoles(roles_ResponseModel roles) {
        this.roles = roles;
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

    public String getEmployees() {
        return employees;
    }

    public void setEmployees(String employees) {
        this.employees = employees;
    }

    public General_infromations getGeneralInfromations() {
        return generalInfromations;
    }

    public void setGeneralInfromations(General_infromations generalInfromations) {
        this.generalInfromations = generalInfromations;
    }
    public List<saveResponseObject> getSaveResponseObject() {
        return saveResponseObjectresults;
    }

    public void setSaveResponseObject(List<saveResponseObject> results) {
        this.saveResponseObjectresults = results;
    }
}
