package com.d500px.fivehundredpx.views.grid_screen;

import com.d500px.fivehundredpx.model.Photo;
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
 * Created by KD on 2/5/16.
 *
 * Listens to user actions from the UI ({@link PhotosGridFragment}), retrieves the data and updates
 * the
 * UI as required.
 */
public class PhotoGridPresenter implements PhotoGridContract.Presenter {

    private final CompositeSubscription mSubscriptions;
    private ServiceRepositoryImpl mServiceRepository;
    private PhotoGridContract.View mView;
    private boolean mFirstLoad = true;

    @Inject
    PhotoGridPresenter(@NonNull ServiceRepositoryImpl serviceRepository,
                       PhotoGridContract.View view) {
        mServiceRepository = checkNotNull(serviceRepository);
        mView = checkNotNull(view);
        mSubscriptions = new CompositeSubscription();
    }

    @Inject
    void setupListener() {
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        if (mFirstLoad || mServiceRepository.isPhotoUpdated()) {
            fetchPhotos(false);
            mFirstLoad = false;
        }
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
        mView.setLoadingIndicator(false);
        mView.dismissSnackbar();
    }

    /**
     * Get data from repository
     */
    @Override
    public void fetchPhotos(boolean nextPage) {
        mView.setLoadingIndicator(true);
        mView.dismissSnackbar();

        EspressoIdlingResource.increment();

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
                                mView.setLoadingIndicator(false);
                                mView.showError();
                            }

                            @Override
                            public void onNext(List<Photo> photos) {
                                mView.setLoadingIndicator(false);
                                mView.updateGridAdapter(photos);
                            }
                        });
        mSubscriptions.add(subscription);
    }
}
