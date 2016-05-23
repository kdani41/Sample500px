package com.d500px.fivehundredpx.repository;

import com.d500px.fivehundredpx.model.Photo;
import com.d500px.fivehundredpx.model.PhotoResponse;

import android.support.annotation.Nullable;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by KD on 2/6/16.
 * <p>
 * Implementation of {@link ServiceRepository}.
 */
@Singleton
public class ServiceRepositoryImpl implements ServiceRepository {

    /**
     * Retrofit api used to fire get request
     */
    private FiveHundredPxApi mFiveHundredPxApi;

    /**
     * Variable holding the main data for the app
     */
    private PhotoData mPhotoData;

    /**
     * Variable to check if photos are updated
     * TODO: try to remove it
     */
    private boolean mIsPhotoUpdated;

    @Inject
    public ServiceRepositoryImpl(FiveHundredPxApi fiveHundredPxApi, PhotoData photoData) {
        mFiveHundredPxApi = fiveHundredPxApi;
        mPhotoData = photoData;
    }

    /**
     * Returns the observable of latest photos fetched from the server.
     * If nextPage is not requested it returns the observable of existing data if data is present.
     *
     * @param imageSize image size required
     * @param nextPage  true if request to next page is required
     */
    @Override
    public Observable<List<Photo>> fetchPhotos(String imageSize, boolean nextPage) {
        if (mPhotoData.hasData() && !nextPage) {
            return Observable.just(getExistingData());
        } else {
            return mFiveHundredPxApi.fetchPhotos(imageSize, mPhotoData.getNextPage())
                    .doOnNext(new Action1<PhotoResponse>() {
                        @Override
                        public void call(PhotoResponse photoResponse) {
                            mPhotoData.setPhotoResponse(photoResponse);
                        }
                    })
                    .flatMap(new Func1<PhotoResponse, Observable<List<Photo>>>() {
                        @Override
                        public Observable<List<Photo>> call(PhotoResponse photoResponse) {
                            mIsPhotoUpdated = true;
                            return Observable.just(mPhotoData.getPhotos());
                        }
                    });
        }
    }

    /**
     * Returns the existing data present in the application
     */
    @Nullable
    public List<Photo> getExistingData() {
        mIsPhotoUpdated = false;
        return mPhotoData.getPhotos();
    }

    /**
     * Returns true if list is being updated.
     */
    public boolean isPhotoUpdated() {
        return mIsPhotoUpdated;
    }
}
