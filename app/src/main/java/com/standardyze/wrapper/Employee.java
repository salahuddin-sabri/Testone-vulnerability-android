package com.standardyze.wrapper;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Employee {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("fname")
    @Expose
    private String fname;
    @SerializedName("lname")
    @Expose
    private String lname;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("companyId")
    @Expose
    private Integer companyId;
    @SerializedName("departmentId")
    @Expose
    private Integer departmentId;
    @SerializedName("action")
    @Expose
    private String action;
    private Integer isEmployeeAdded;
    private String customEmployeeName;

    public String getCustomEmployeeName() {
        return customEmployeeName;
    }

    public void setCustomEmployeeName(String customEmployeeName) {
        this.customEmployeeName = customEmployeeName;
    }

    public Integer isEmployeeAdded() {
        return isEmployeeAdded;
    }

    public void setEmployeeAdded(Integer employeeAdded) {
        isEmployeeAdded = employeeAdded;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public String setFname(String fname) {
        this.fname = fname;
        return fname;
    }

    int isChecked;

    public int getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(int isChecked) {
        this.isChecked = isChecked;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Employee) {
            Employee current = (Employee) obj;
            String employeeNameCurrent = (current.getFname() != null ? current.getFname() : "")
                    + (current.getLname() != null ? (" " + current.getLname()) : "");
            String employeeNamethis = (this.getFname() != null ? this.getFname() : "")
                    + (this.getLname() != null ? (" " + this.getLname()) : "");
            if (employeeNameCurrent.equalsIgnoreCase(employeeNamethis)) {
                return true;
            }
        }
        return false;
    }
}