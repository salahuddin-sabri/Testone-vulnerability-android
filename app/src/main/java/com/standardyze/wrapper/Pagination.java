package com.standardyze.wrapper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pagination {





    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("current")
    @Expose
    private Integer current;
    @SerializedName("perPage")
    @Expose
    private Integer perPage;
    @SerializedName("next")
    @Expose
    private Integer next;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getPerPage() {
        return perPage;
    }

    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    public Integer getNext() {
        return next;
    }

    public void setNext(Integer next) {
        this.next = next;
    }

}

