package com.example.punto_verde.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class ImagePlace {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("placeID")
    @Expose
    private int placeID;
    @SerializedName("url")
    @Expose
    private String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlaceID() {
        return placeID;
    }

    public void setPlaceID(int placeID) {
        this.placeID = placeID;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
