package com.quintus.labs.grocerystore.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OfferResult {
    @SerializedName("offers")
    List<Offers> offerList;
    @SerializedName("code")
    int code;
    @SerializedName("status")
    String status;

    public List<Offers> getOfferList() {
        return offerList;
    }

    public void setOfferList(List<Offers> offerList) {
        this.offerList = offerList;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
