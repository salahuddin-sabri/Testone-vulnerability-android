package com.standardyze.wrapper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PropertyDepartmentMapping {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("propertyId")
@Expose
private Integer propertyId;
@SerializedName("departmentId")
@Expose
private Integer departmentId;
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

public Integer getDepartmentId() {
return departmentId;
}

public void setDepartmentId(Integer departmentId) {
this.departmentId = departmentId;
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