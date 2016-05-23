package com.d500px.fivehundredpx.repository;

import com.d500px.fivehundredpx.model.Photo;
import com.d500px.fivehundredpx.model.PhotoResponse;

import java.util.List;

import javax.inject.Singleton;

/**
 * Created by KD on 2/15/16.
 * </p>
 * Singleton used  for maintaining the photos throughout the application scope.
 */
@Singleton
public class PhotoData {
    private boolean mIsAtEnd = false;

    private int mCurrentPage;

    private List<Photo> mPhotos;

    public List<Photo> getPhotos() {
        return mPhotos;
    }

    /**
     * Sets the updated response whenever a call to new page is requested
     *
     * @param photoResponse response to set
     * @see {@link ServiceRepositoryImpl#fetchPhotos(String, boolean)}
     */
    public void setPhotoResponse(PhotoResponse photoResponse) {
        if (mPhotos == null) {
            mPhotos = photoResponse.getPhotos();
        } else if (photoResponse.getPhotos().size() == 0) {
            mIsAtEnd = true;
        } else {
            mPhotos.addAll(photoResponse.getPhotos());
        }
        mCurrentPage = photoResponse.getCurrentPage();
    }

    /**
     * Returns true if singleton contains data
     */
    public boolean hasData() {
        return mPhotos != null;
    }

    /**
     * Returns the next page from the response
     */
    public int getNextPage() {
        if (mPhotos == null) {
            return 1;
        } else if (!mIsAtEnd) {
            return mCurrentPage + 1;
        } else {
            return mCurrentPage;
        }
    }
}
