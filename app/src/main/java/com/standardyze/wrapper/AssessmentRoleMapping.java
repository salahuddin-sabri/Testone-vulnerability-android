package com.standardyze.wrapper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AssessmentRoleMapping {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("assessmentId")
@Expose
private Integer assessmentId;
@SerializedName("roleId")
@Expose
private Integer roleId;
@SerializedName("companyId")
@Expose
private Integer companyId;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public Integer getAssessmentId() {
return assessmentId;
}

public void setAssessmentId(Integer assessmentId) {
this.assessmentId = assessmentId;
}

public Integer getRoleId() {
return roleId;
}

public void setRoleId(Integer roleId) {
this.roleId = roleId;
}

public Integer getCompanyId() {
return companyId;
}

public void setCompanyId(Integer companyId) {
this.companyId = companyId;
}

}