package com.standardyze.wrapper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

public class CategoryResults {


        @SerializedName("fbquality")
        @Expose
        private String categorya;
        @SerializedName("service-guest-retention")
        @Expose
        private String categoryb;
        @SerializedName("cleanliness-condition")
        @Expose
        private String categoryc;
        @SerializedName("revenue-impact")
        @Expose
        private String categoryd;

        public String getCategorya() {
            return categorya;
        }

        public void setCategorya(String categorya) {
            this.categorya = categorya;
        }

        public String getCategoryb() {
            return categoryb;
        }

        public void setCategoryb(String categoryb) {
            this.categoryb = categoryb;
        }

        public String getCategoryc() {
            return categoryc;
        }

        public void setCategoryc(String categoryc) {
            this.categoryc = categoryc;
        }

        public String getCategoryd() {
            return categoryd;
        }

        public void setCategoryd(String categoryd) {
            this.categoryd = categoryd;
        }


}
