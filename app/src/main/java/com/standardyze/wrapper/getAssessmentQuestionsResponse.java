package com.standardyze.wrapper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class getAssessmentQuestionsResponse {
    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("results")
    @Expose
    private AssesmentResponse_Results results;




    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public AssesmentResponse_Results getResults() {
        return results;
    }

    public void setResults(AssesmentResponse_Results results) {
        this.results = results;
    }
}
