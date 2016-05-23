package com.d500px.fivehundredpx.views.full_screen;

import com.d500px.fivehundredpx.model.Photo;
import com.d500px.fivehundredpx.model.PhotoResponse;
import com.d500px.fivehundredpx.repository.ServiceRepositoryImpl;
import com.d500px.fivehundredpx.utils.Constants;
import com.d500px.fivehundredpx.utils.EspressoIdlingResource;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by KD on 2/9/16.
 *
 * Listens to user actions from the UI ({@link FullPhotoFragment}), retrieves the data and updates
 * the
 * UI as required.
 */
public class FullPhotoPresenter implements FullPhotoContract.Presenter {
    private FullPhotoContract.View mView;
    private ServiceRepositoryImpl mServiceRepository;
    private CompositeSubscription mSubscriptions;
    private boolean mIsFirstLoad = true;

    @Inject
    FullPhotoPresenter(@NonNull ServiceRepositoryImpl repository,
                       @NonNull FullPhotoContract.View view) {
        mView = checkNotNull(view);
        mServiceRepository = checkNotNull(repository);
        mSubscriptions = new CompositeSubscription();
    }

    @Inject
    void setupListener() {
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        if (mIsFirstLoad) {
            fetchPhotos(false);
        }
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }

    @Override
    public void fetchPhotos(boolean nextPage) {
        EspressoIdlingResource.increment();
        mView.setLoadingIndicator(true);
        Subscription subscription =
                mServiceRepository.fetchPhotos(Constants.IMAGE_SIZE_600_1600, nextPage)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<List<Photo>>() {
                            @Override
                            public void onCompleted() {
                                if (!EspressoIdlingResource.getIdlingResource().isIdleNow()) {
                                    EspressoIdlingResource.decrement(); // Set app as idle.
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                mView.showError();
                            }

                            @Override
                            public void onNext(List<Photo> photos) {
                                mView.updateGridAdapter(photos);
                                if (mIsFirstLoad) {
                                    mView.updatePagerPosition();
                                    mIsFirstLoad = false;
                                }
                            }
                        });
        mSubscriptions.add(subscription);
    }

    private void processPhotos(PhotoResponse photoResponse) {
        mView.updateGridAdapter(photoResponse.getPhotos());
    }
}
