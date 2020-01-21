package com.standardyze.wrapper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class dashboardCategoryScores {
    @SerializedName("service-guest-retention")
    @Expose
    private String serviceGuestRetention;
    @SerializedName("revenue-impact")
    @Expose
    private String revenueImpact;
    @SerializedName("fbquality")
    @Expose
    private String fbquality;
    @SerializedName("cleanliness-condition")
    @Expose
    private String cleanlinessCondition;

    public String getServiceGuestRetention() {
        return serviceGuestRetention;
    }

    public void setServiceGuestRetention(String serviceGuestRetention) {
        this.serviceGuestRetention = serviceGuestRetention;
    }

    public String getRevenueImpact() {
        return revenueImpact;
    }

    public void setRevenueImpact(String revenueImpact) {
        this.revenueImpact = revenueImpact;
    }

    public String getFbquality() {
        return fbquality;
    }

    public void setFbquality(String fbquality) {
        this.fbquality = fbquality;
    }

    public String getCleanlinessCondition() {
        return cleanlinessCondition;
    }

    public void setCleanlinessCondition(String cleanlinessCondition) {
        this.cleanlinessCondition = cleanlinessCondition;
    }
}
