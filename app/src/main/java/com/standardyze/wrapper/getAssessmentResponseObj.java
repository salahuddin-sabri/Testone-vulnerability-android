package com.standardyze.wrapper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.HashMap;

public class getAssessmentResponseObj implements Serializable {

    @SerializedName("responseTblId")
    @Expose
    private Integer responseTblId;
    @SerializedName("selection")
    @Expose
    private String selection;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("imgId")
    @Expose
    private String imgId;
    @SerializedName("questionText")
    @Expose
    private String questionText;
    @SerializedName("categories")
    @Expose
    private  HashMap<String,String>  categories;
    int count ;
     private  String questionsCategories;
    String imageURL ;
    @SerializedName("assessmentUniqueId")
    @Expose
    private Integer assessmentUniqueId;

    public Integer getAssessmentUniqueId() {
        return assessmentUniqueId;
    }

    public void setAssessmentUniqueId(Integer assessmentUniqueId) {
        this.assessmentUniqueId = assessmentUniqueId;
    }

  /*  public getAssessmentResponseObj(Integer responseTblId, String selection, String comment, String imgId, String questionText, Integer assessmentUniqueId) {
        this.responseTblId = responseTblId;
        this.selection = selection;
        this.comment = comment;
        this.imgId = imgId;
        this.questionText = questionText;
        this.assessmentUniqueId = assessmentUniqueId;

    }*/

    public String getQuestionsCategories() {
        return questionsCategories;
    }

    public void setQuestionsCategories(String questionsCategories) {
        this.questionsCategories = questionsCategories;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getSelection() {
        return selection;
    }

    public void setSelection(String selection) {
        this.selection = selection;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getImgId() {
        return imgId;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public HashMap<String,String>  getCategories() {
        return categories;
    }

    public void setCategories(HashMap<String,String> categories) {
        this.categories = categories;
    }


}
