package com.d500px.fivehundredpx.views.full_screen;

import com.d500px.fivehundredpx.model.Photo;
import com.d500px.fivehundredpx.views.base.BasePresenter;
import com.d500px.fivehundredpx.views.base.BaseView;

import java.util.List;

/**
 * Created by KD on 2/5/16.
 *
 * Specifies the contract between View {@link FullPhotoFragment} and Presenter {@link
 * FullPhotoPresenter}
 */
public interface FullPhotoContract {

    interface View extends BaseView<Presenter> {
        void updateGridAdapter(List<Photo> photos);

        void updatePagerPosition();

        void setLoadingIndicator(final boolean active);

        void showError();
    }

    interface Presenter extends BasePresenter {
        void fetchPhotos(boolean nextPage);
    }
}
