package com.d500px.fivehundredpx.views.grid_screen;

import com.d500px.fivehundredpx.R;
import com.d500px.fivehundredpx.model.Photo;
import com.d500px.fivehundredpx.views.base.BaseFragment;
import com.d500px.fivehundredpx.views.custom_view.CustomGridLayoutManager;
import com.d500px.fivehundredpx.views.custom_view.InfiniteScrollListener;
import com.d500px.fivehundredpx.views.custom_view.SpaceItemDecorator;
import com.d500px.fivehundredpx.views.full_screen.FullPhotoScreenActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by KD on 2/5/16.
 * Fragment representing the main grid of the app.
 */
public class PhotosGridFragment extends BaseFragment implements PhotoGridContract.View {

    private static final String TAG = PhotosGridFragment.class.getSimpleName();
    @Bind(R.id.main_grid)
    RecyclerView mMainGrid;
    @Bind(android.R.id.progress)
    ProgressBar mProgressBar;
    @Bind(R.id.main_container)
    FrameLayout mMainContainer;

    private PhotoGridContract.Presenter mPresenter;
    private Snackbar mSnackbar;
    private PhotoGridAdapter mAdapter;

    private PhotoItemListener mPhotoItemListener = new PhotoItemListener() {
        @Override
        public void onPhotoClick(int position, View clickedView) {
            ImageView imageView = ButterKnife.findById(clickedView, R.id.photo_thumb);
            final ActivityOptionsCompat options =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), imageView,
                            ViewCompat.getTransitionName(clickedView));
            ActivityCompat.startActivity(getActivity(),
                    FullPhotoScreenActivity.getStartIntent(getContext(), position), options.toBundle());
        }
    };

    public PhotosGridFragment() {
        //Required Empty Constructor
    }

    public static PhotosGridFragment newInstance() {
        return new PhotosGridFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mAdapter = new PhotoGridAdapter(mPhotoItemListener, new ArrayList<Photo>(0));
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CustomGridLayoutManager layoutManager = new CustomGridLayoutManager(getContext(),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 123,
                        getResources().getDisplayMetrics()));
        mMainGrid.setLayoutManager(layoutManager);
        mMainGrid.addOnScrollListener(new InfiniteScrollListener(layoutManager) {
            @Override
            public void onLastItemReached() {
                mPresenter.fetchPhotos(true);
            }
        });
        mMainGrid.addItemDecoration(new SpaceItemDecorator(getContext(), R.dimen.item_spacing));
        mMainGrid.setAdapter(mAdapter);
    }

    @Override
    protected int getTitle() {
        return 0;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.main_grid;
    }

    @Override
    public void updateGridAdapter(List<Photo> photos) {
        mAdapter.replaceData(photos);
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (active) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void showError() {
        mSnackbar = Snackbar.make(mMainContainer, R.string.no_internet_connection,
                Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.action_try_again, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mSnackbar.dismiss();
                        setLoadingIndicator(false);
                        mPresenter.fetchPhotos(true);
                    }
                });
        mSnackbar.show();
    }

    @Override
    public void dismissSnackbar() {
        if (mSnackbar != null) mSnackbar.dismiss();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    @Override
    public void setPresenter(PhotoGridContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    interface PhotoItemListener {
        void onPhotoClick(int position, View clickedView);
    }
}
