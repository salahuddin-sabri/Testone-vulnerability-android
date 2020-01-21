package com.standardyze.wrapper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DashboardResponseModel {
    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("results")
    @Expose
    private DashboardResults results;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public DashboardResults getResults() {
        return results;
    }

    public void setResults(DashboardResults results) {
        this.results = results;
    }
}
