package com.d500px.fivehundredpx.views.full_screen;

import com.d500px.fivehundredpx.R;
import com.d500px.fivehundredpx.model.Photo;
import com.d500px.fivehundredpx.utils.CommonUtils;
import com.d500px.fivehundredpx.utils.Constants;
import com.d500px.fivehundredpx.views.base.BaseFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by KD on 5/2/16.
 * </p>
 * Main UI for single photo view
 */
public class FullPhotoFragment extends BaseFragment implements FullPhotoContract.View {
    private static final String ARGUMENT_PAGER_POSITION = "PAGER_POSITION";
    private static final String TAG = FullPhotoFragment.class.getSimpleName();

    @Bind(R.id.pager)
    ViewPager mPager;
    @Bind(android.R.id.progress)
    ProgressBar mProgressBar;
    @Bind(R.id.insideFrame)
    FrameLayout mInsideFrameLayout;

    private FullPhotoContract.Presenter mPresenter;
    private PhotoScreenAdapter mAdapter;
    private int mPagerPosition;
    private Snackbar mSnackbar;

    public static FullPhotoFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt(ARGUMENT_PAGER_POSITION, position);
        FullPhotoFragment fragment = new FullPhotoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new PhotoScreenAdapter(getChildFragmentManager(), new ArrayList<Photo>(0));
        Bundle bundle = getArguments();
        if (savedInstanceState == null && CommonUtils.bundleContainsKey(bundle,
                ARGUMENT_PAGER_POSITION)) {
            mPagerPosition = bundle.getInt(ARGUMENT_PAGER_POSITION);
        } else if (CommonUtils.bundleContainsKey(savedInstanceState, ARGUMENT_PAGER_POSITION)) {
            mPagerPosition = savedInstanceState.getInt(ARGUMENT_PAGER_POSITION);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPager.setAdapter(mAdapter);
        mPager.setOffscreenPageLimit(Constants.OFF_SCREEN_PAGE_LIMIT);
    }

    @Override
    protected int getTitle() {
        return 0;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_full_photo;
    }

    @Override
    public void updateGridAdapter(List<Photo> photos) {
        if (mAdapter.getCount() != photos.size()) mAdapter.replaceData(photos);
    }

    @Override
    public void updatePagerPosition() {
        mPager.setCurrentItem(mPagerPosition);
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
        mSnackbar = Snackbar.make(mInsideFrameLayout, R.string.no_internet_connection,
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
    public void onSaveInstanceState(Bundle outState) {
        // Saving position of the view pager
        outState.putInt(ARGUMENT_PAGER_POSITION, mPager.getCurrentItem());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void setPresenter(FullPhotoContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    private class PhotoScreenAdapter extends FragmentStatePagerAdapter {
        private List<Photo> mPhotoList;

        public PhotoScreenAdapter(FragmentManager fm, List<Photo> photoList) {
            super(fm);
            replaceData(photoList);
        }

        @Override
        public Fragment getItem(int position) {
            if (isLastItem(position)) {
                mPresenter.fetchPhotos(true);
            }
            return PhotoFragment.newInstance(mPhotoList.get(position).getImages().get(1).getUrl());
        }

        @Override
        public int getCount() {
            return mPhotoList.size();
        }

        public void replaceData(List<Photo> photos) {
            setList(photos);
            notifyDataSetChanged();
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        private void setList(List<Photo> photos) {
            //TODO: find a better implementation for this
            mPhotoList = new ArrayList<>(checkNotNull(photos));
        }

        private boolean isLastItem(int pos) {
            return pos == mAdapter.getCount() - 1;
        }
    }
}
