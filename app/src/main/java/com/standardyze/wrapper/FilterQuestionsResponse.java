package com.standardyze.wrapper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FilterQuestionsResponse {

    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("results")
    @Expose
    private List<QuestionsResult> results = null;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public List<QuestionsResult> getResults() {
        return results;
    }

    public void setResults(List<QuestionsResult> results) {
        this.results = results;
    }
}
