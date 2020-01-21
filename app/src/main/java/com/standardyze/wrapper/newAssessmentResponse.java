package com.standardyze.wrapper;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;
import com.standardyze.App.App;


import java.util.List;

public class newAssessmentResponse  {
    @SerializedName("statusCode")
    @Expose
    private Integer statuCode;
    @SerializedName("users")
    @Expose
    private List<User> users = null;
    @SerializedName("properties")
    @Expose
    private List<propertyModel> properties = null;
    @SerializedName("departments")
    @Expose
    private List<departmentModel> departments = null;
    @SerializedName("property_department_mapping")
    @Expose
    private List<PropertyDepartmentMapping> propertyDepartmentMapping = null;
    @SerializedName("roles")
    @Expose
    private List<roles> roles = null;
    @SerializedName("employees")
    @Expose
    private List<Employee> employees = null;
    @SerializedName("assmessments")
    @Expose
    private List<AssmessmentModel> assmessments = null;
    @SerializedName("department_assessment_mapping")
    @Expose
    private List<DepartmentAssessmentMapping> departmentAssessmentMapping = null;
    @SerializedName("assessment_role_mapping")
    @Expose
    private List<AssessmentRoleMapping> assessmentRoleMapping = null;
    @SerializedName("standard_questions")
    @Expose
    private List<StandardQuestion> standardQuestions = null;
    @SerializedName("categories")
    @Expose
    private List<Category> categories = null;
    @SerializedName("property_trainer_mapping")
    @Expose
    private List<PropertyTrainerMapping> propertyTrainerMapping = null;
    @SerializedName("lastUpdateDate")
    @Expose
    private Integer lastUpdateDate;

    public Integer getStatuCode() {
        return statuCode;
    }

    public void setStatuCode(Integer statuCode) {
        this.statuCode = statuCode;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<propertyModel> getProperties() {
        return properties;
    }

    public void setProperties(List<propertyModel> properties) {
        this.properties = properties;
    }

    public List<departmentModel> getDepartments() {
        return departments;
    }

    public void setDepartments(List<departmentModel> departments) {
        this.departments = departments;
    }

    public List<PropertyDepartmentMapping> getPropertyDepartmentMapping() {
        return propertyDepartmentMapping;
    }

    public void setPropertyDepartmentMapping(List<PropertyDepartmentMapping> propertyDepartmentMapping) {
        this.propertyDepartmentMapping = propertyDepartmentMapping;
    }

    public List<roles> getRoles() {
        return roles;
    }

    public void setRoles(List<roles> roles) {
        this.roles = roles;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
    public List<AssmessmentModel> getAssmessments() {
        return assmessments;
    }

    public void setAssmessments(List<AssmessmentModel> assmessments) {
        this.assmessments = assmessments;
    }
    public List<DepartmentAssessmentMapping> getDepartmentAssessmentMapping() {
        return departmentAssessmentMapping;
    }

    public void setDepartmentAssessmentMapping(List<DepartmentAssessmentMapping> departmentAssessmentMapping) {
        this.departmentAssessmentMapping = departmentAssessmentMapping;
    }

    public List<AssessmentRoleMapping> getAssessmentRoleMapping() {
        return assessmentRoleMapping;
    }

    public void setAssessmentRoleMapping(List<AssessmentRoleMapping> assessmentRoleMapping) {
        this.assessmentRoleMapping = assessmentRoleMapping;
    }

    public List<StandardQuestion> getStandardQuestions() {
        return standardQuestions;
    }

    public void setStandardQuestions(List<StandardQuestion> standardQuestions) {
        this.standardQuestions = standardQuestions;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
    public List<PropertyTrainerMapping> getPropertyTrainerMapping() {
        return propertyTrainerMapping;
    }

    public void setPropertyTrainerMapping(List<PropertyTrainerMapping> propertyTrainerMapping) {
        this.propertyTrainerMapping = propertyTrainerMapping;
    }



    public Integer getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Integer lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }


}




