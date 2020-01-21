package com.standardyze.wrapper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AssesmentResponse_Roles {


        @SerializedName("host")
        @Expose
        private String host;
        @SerializedName("bartender")
        @Expose
        private String bartender;

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public String getBartender() {
            return bartender;
        }

        public void setBartender(String bartender) {
            this.bartender = bartender;
        }
}
