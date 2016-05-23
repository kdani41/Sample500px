package com.d500px.fivehundredpx.views.grid_screen;

import com.d500px.fivehundredpx.model.Photo;
import com.d500px.fivehundredpx.views.base.BasePresenter;
import com.d500px.fivehundredpx.views.base.BaseView;

import java.util.List;

/**
 * Created by KD on 2/5/16.
 *
 * Specifies the contract between View {@link PhotosGridFragment} and Presenter {@link
 * PhotoGridPresenter}
 */
public interface PhotoGridContract {

    interface View extends BaseView<Presenter> {
        void updateGridAdapter(List<Photo> photos);

        void setLoadingIndicator(final boolean active);

        void showError();

        void dismissSnackbar();
    }

    interface Presenter extends BasePresenter {
        void fetchPhotos(boolean nextPage);
    }
}
