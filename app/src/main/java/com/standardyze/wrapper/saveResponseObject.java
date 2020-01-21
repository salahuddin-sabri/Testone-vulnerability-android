package com.standardyze.wrapper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class saveResponseObject {


@SerializedName("questionId")
@Expose
private Integer questionId;
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
String imgURL;


    public saveResponseObject(Integer questionId, String selection, String comment, String imgId, String questionText,String img_url) {
        this.questionId = questionId;
        this.selection = selection;
        this.comment = comment;
        this.imgId = imgId;
        this.questionText = questionText;
        this.imgURL =img_url;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public Integer getQuestionId() {
return questionId;
}

public void setQuestionId(Integer questionId) {
this.questionId = questionId;
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

}