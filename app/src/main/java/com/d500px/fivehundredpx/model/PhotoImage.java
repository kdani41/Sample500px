package com.d500px.fivehundredpx.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by KD on 2/16/16.
 *
 * Model representing single image.
 */
public class PhotoImage {
    @SerializedName("size")
    private int mSize;
    @SerializedName("url")
    private String mUrl;
    @SerializedName("format")
    private String mFormat;

    public PhotoImage(int size, String url, String format) {
        mSize = size;
        mUrl = url;
        mFormat = format;
    }

    public String getFormat() {
        return mFormat;
    }

    public int getSize() {
        return mSize;
    }

    public String getUrl() {
        return mUrl;
    }

}
