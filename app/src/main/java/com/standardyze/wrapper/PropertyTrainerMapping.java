package com.standardyze.wrapper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PropertyTrainerMapping {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("propertyId")
@Expose
private Integer propertyId;
@SerializedName("employeeId")
@Expose
private Integer employeeId;
@SerializedName("companyId")
@Expose
private Integer companyId;
@SerializedName("action")
@Expose
private String action;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public Integer getPropertyId() {
return propertyId;
}

public void setPropertyId(Integer propertyId) {
this.propertyId = propertyId;
}

public Integer getEmployeeId() {
return employeeId;
}

public void setEmployeeId(Integer employeeId) {
this.employeeId = employeeId;
}

public Integer getCompanyId() {
return companyId;
}

public void setCompanyId(Integer companyId) {
this.companyId = companyId;
}

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}