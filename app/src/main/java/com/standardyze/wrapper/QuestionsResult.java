package com.standardyze.wrapper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.util.HashMap;

public class QuestionsResult  {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("assessments")
    @Expose
    private AssessmentResult assessments;
    @SerializedName("roles")
    @Expose
    private roles_result roles;
    @SerializedName("categories")
    @Expose
    private HashMap<String,String> categories;
    @SerializedName("companyId")
    @Expose
    private Integer companyId;

       boolean isSelected ;

    @SerializedName("selection")
    @Expose
    private String selection;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("imgId")
    @Expose
    private String imgId;

    @SerializedName("responseTblId")
    @Expose
    private Integer responseTblId;
    @SerializedName("AssesmentResponse_Categories")
    @Expose
    private AssesmentResponse_Categories response_categories;

    String imgURL;

    public QuestionsResult() {
    }

    public QuestionsResult(Integer id, String question, String selection, String comment, String imgId, Integer responseTblId) {
        this.id = id;
        this.question = question;
        this.selection = selection;
        this.comment = comment;
        this.imgId = imgId;
        this.responseTblId = responseTblId;


    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    int isChecked;
    int count;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Integer getResponseTblId() {
        return responseTblId;
    }

    public void setResponseTblId(Integer responseTblId) {
        this.responseTblId = responseTblId;
    }

    public AssesmentResponse_Categories getResponse_categories() {
        return response_categories;
    }

    public void setResponse_categories(AssesmentResponse_Categories response_categories) {
        this.response_categories = response_categories;
    }

    public int getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(int isChecked) {
        this.isChecked = isChecked;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public AssessmentResult getAssessments() {
        return assessments;
    }

    public void setAssessments(AssessmentResult assessments) {
        this.assessments = assessments;
    }

    public roles_result getRoles() {
        return roles;
    }

    public void setRoles(roles_result roles) {
        this.roles = roles;
    }

    public HashMap<String,String> getCategories() {
        return categories;
    }

    public void setCategories(HashMap<String,String> categories) {
        this.categories = categories;
    }



    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }







    public String getSelection() {
        return selection;
    }

    public String setSelection(String selection) {
        this.selection = selection;
        return selection;
    }

    public String getComment() {
        return comment;
    }

    public String setComment(String comment) {
        this.comment = comment;
        return comment;
    }

    public String getImgId() {
        return imgId;
    }

    public String setImgId(String imgId) {
        this.imgId = imgId;
        return imgId;
    }
}
