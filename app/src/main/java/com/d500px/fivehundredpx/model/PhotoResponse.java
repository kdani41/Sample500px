package com.d500px.fivehundredpx.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by KD on 2/16/16.
 * </p>
 * Model used to store json data locally.
 */
public class PhotoResponse {

    @SerializedName("current_page")
    private int mCurrentPage;

    @SerializedName("photos")
    private List<Photo> mPhotos;

    public PhotoResponse(List<Photo> photos, int currentPage) {
        mPhotos = photos;
        mCurrentPage = currentPage;
    }

    public int getCurrentPage() {
        return mCurrentPage;
    }

    public List<Photo> getPhotos() {
        return mPhotos;
    }
}
