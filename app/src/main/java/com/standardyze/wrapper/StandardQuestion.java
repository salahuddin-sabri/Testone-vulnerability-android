package com.standardyze.wrapper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StandardQuestion {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("assessmentNumber")
@Expose
private String assessmentNumber;
@SerializedName("assessmentQuestion")
@Expose
private String assessmentQuestion;
@SerializedName("sectionName")
@Expose
private String sectionName;
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

public String getAssessmentNumber() {
return assessmentNumber;
}

public void setAssessmentNumber(String assessmentNumber) {
this.assessmentNumber = assessmentNumber;
}

public String getAssessmentQuestion() {
return assessmentQuestion;
}

public void setAssessmentQuestion(String assessmentQuestion) {
this.assessmentQuestion = assessmentQuestion;
}

public String getSectionName() {
return sectionName;
}

public void setSectionName(String sectionName) {
this.sectionName = sectionName;
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