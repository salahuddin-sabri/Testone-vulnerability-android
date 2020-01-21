package com.standardyze.wrapper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AssesmentResponse_Categories {
    @SerializedName("fbquality")
    @Expose
    private String fbquality;
    @SerializedName("service-guest-retention")
    @Expose
    private String serviceGuestRetention;
    @SerializedName("cleanliness-condition")
    @Expose
    private String cleanlinessCondition;
    @SerializedName("revenue-impact")
    @Expose
    private String revenueImpact;

    public String getFbquality() {
        return fbquality;
    }

    public void setFbquality(String fbquality) {
        this.fbquality = fbquality;
    }

    public String getServiceGuestRetention() {
        return serviceGuestRetention;
    }

    public void setServiceGuestRetention(String serviceGuestRetention) {
        this.serviceGuestRetention = serviceGuestRetention;
    }

    public String getCleanlinessCondition() {
        return cleanlinessCondition;
    }

    public void setCleanlinessCondition(String cleanlinessCondition) {
        this.cleanlinessCondition = cleanlinessCondition;
    }

    public String getRevenueImpact() {
        return revenueImpact;
    }

    public void setRevenueImpact(String revenueImpact) {
        this.revenueImpact = revenueImpact;
    }
}
