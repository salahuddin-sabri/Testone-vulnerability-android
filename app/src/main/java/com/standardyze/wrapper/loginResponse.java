package com.standardyze.wrapper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class loginResponse {
    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("results")
    @Expose
    private List<loginResult> results = null;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public List<loginResult> getResults() {
        return results;
    }

    public void setResults(List<loginResult> results) {
        this.results = results;
    }
}
