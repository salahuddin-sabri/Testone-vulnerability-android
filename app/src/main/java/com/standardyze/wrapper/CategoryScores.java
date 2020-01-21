package com.standardyze.wrapper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryScores{

    @SerializedName("cata")
    @Expose
    private Integer cata;
    @SerializedName("catb")
    @Expose
    private Integer catb;
    @SerializedName("catc")
    @Expose
    private Integer catc;
    @SerializedName("catd")
    @Expose
    private Integer catd;

    public Integer getCata() {
        return cata;
    }

    public void setCata(Integer cata) {
        this.cata = cata;
    }

    public Integer getCatb() {
        return catb;
    }

    public void setCatb(Integer catb) {
        this.catb = catb;
    }

    public Integer getCatc() {
        return catc;
    }

    public void setCatc(Integer catc) {
        this.catc = catc;
    }

    public Integer getCatd() {
        return catd;
    }

    public void setCatd(Integer catd) {
        this.catd = catd;
    }

}