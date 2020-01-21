package com.standardyze.wrapper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DepartmentAssessmentMapping {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("departmentId")
@Expose
private Integer departmentId;
@SerializedName("assessmentId")
@Expose
private Integer assessmentId;
@SerializedName("companyId")
@Expose
private Integer companyId;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public Integer getDepartmentId() {
return departmentId;
}

public void setDepartmentId(Integer departmentId) {
this.departmentId = departmentId;
}

public Integer getAssessmentId() {
return assessmentId;
}

public void setAssessmentId(Integer assessmentId) {
this.assessmentId = assessmentId;
}

public Integer getCompanyId() {
return companyId;
}

public void setCompanyId(Integer companyId) {
this.companyId = companyId;
}

}