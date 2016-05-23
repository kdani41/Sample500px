package com.d500px.fivehundredpx;

import com.d500px.fivehundredpx.model.Photo;
import com.d500px.fivehundredpx.model.PhotoResponse;
import com.d500px.fivehundredpx.repository.PhotoData;
import com.d500px.fivehundredpx.repository.ServiceRepository;
import com.d500px.fivehundredpx.repository.ServiceRepositoryImpl;
import com.d500px.fivehundredpx.util.MockModelPhotoResponse;
import com.d500px.fivehundredpx.utils.Constants;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import rx.Observable;
import rx.observers.TestSubscriber;

import static org.mockito.Mockito.when;

/**
 * Created by KD on 5/4/16.
 */
public class ServiceRepositoryTest {

    private static final int FAKE_PAGE = 1;
    private static final int PHOTOS_COUNT = 20;

    @Mock
    ServiceRepository.FiveHundredPxApi mFiveHundredPxApi;
    private ServiceRepositoryImpl mServiceRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        PhotoData photoData = new PhotoData();
        mServiceRepository = new ServiceRepositoryImpl(mFiveHundredPxApi, photoData);
    }

    @Test
    public void testFetchPhotos() throws Exception {

        PhotoResponse mockPhotos = MockModelPhotoResponse.generateMockPhotoResponse(PHOTOS_COUNT);
        when(mFiveHundredPxApi.fetchPhotos(Constants.IMAGE_SIZE_600_1600, FAKE_PAGE)).thenReturn(
                Observable.just(mockPhotos));

        TestSubscriber<List<Photo>> photoResponseTestSubscriber = new TestSubscriber<>();
        mServiceRepository.fetchPhotos(Constants.IMAGE_SIZE_600_1600, true)
                .subscribe(photoResponseTestSubscriber);

        photoResponseTestSubscriber.assertNoErrors();
        photoResponseTestSubscriber.assertValue(mockPhotos.getPhotos());
        photoResponseTestSubscriber.assertCompleted();
    }
}
