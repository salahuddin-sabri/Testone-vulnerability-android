package com.standardyze.wrapper;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class General_infromations implements Serializable
{


        @SerializedName("room")
        @Expose
        private Integer room;
        @SerializedName("meal_period")
        @Expose
        private String mealPeriod;

    public General_infromations() {
    }

    public General_infromations(Integer room, String mealPeriod) {
        this.room = room;
        this.mealPeriod = mealPeriod;
    }

    public Integer getRoom() {
            return room;
        }

        public void setRoom(Integer room) {
            this.room = room;
        }

        public String getMealPeriod() {
            return mealPeriod;
        }

        public void setMealPeriod(String mealPeriod) {
            this.mealPeriod = mealPeriod;
        }


}
			
	