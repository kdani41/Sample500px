package com.d500px.fivehundredpx.repository;

import com.d500px.fivehundredpx.BuildConfig;
import com.d500px.fivehundredpx.model.Photo;
import com.d500px.fivehundredpx.model.PhotoResponse;
import com.d500px.fivehundredpx.utils.Constants;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by KD on 2/6/16.
 * <p>
 * Main point of entry for accessing data.
 */
public interface ServiceRepository {

    Observable<List<Photo>> fetchPhotos(String imageSize, boolean nextPage);

    /**
     * Interface of the api responsible for performing the get request
     */
    interface FiveHundredPxApi {
        @GET(Constants.PHOTOS_ENDPOINT_PATH + Constants.PHOTOS_EXCLUDE +
                Constants.PHOTOS_CONSUMER_KEY + BuildConfig.API_KEY)
        Observable<PhotoResponse> fetchPhotos(@Query(Constants.IMAGE_SIZE) String imageSize,
                                              @Query(Constants.PAGE) int page);
    }
}
