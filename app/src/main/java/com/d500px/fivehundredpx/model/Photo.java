package com.d500px.fivehundredpx.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by KD on 2/16/16.
 * </p>
 * Model representing list of images with multiple urls of different sizes.
 */
public class Photo {
    public static String TAG = Photo.class.getSimpleName();

    @SerializedName("id")
    private int mPhotoId;

    @SerializedName("images")
    private List<PhotoImage> mImages;

    public Photo(List<PhotoImage> images) {
        mImages = images;
    }

    public List<PhotoImage> getImages() {
        return mImages;
    }

    public int getPhotoId() {
        return mPhotoId;
    }
}