package com.d500px.fivehundredpx.views.full_screen;

import com.d500px.fivehundredpx.R;
import com.d500px.fivehundredpx.utils.LogUtils;
import com.d500px.fivehundredpx.views.base.BaseFragment;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.ImageView;

import butterknife.Bind;

/**
 * Created by KD on 2/7/16.
 * <p/>
 * Class representing each view of the view pager {@link FullPhotoFragment}
 */
public class PhotoFragment extends BaseFragment {

    private static final String ARGUMENT_FULL_PHOTO_URL = "FULL_PHOTO_URL";
    private static final String TAG = PhotoFragment.class.getSimpleName();
    @Bind(R.id.photo_full)
    ImageView mPhotoFull;

    private String mFullPhotoUrl;

    public static PhotoFragment newInstance(String url) {
        Bundle args = new Bundle();
        args.putString(ARGUMENT_FULL_PHOTO_URL, url);
        PhotoFragment fragment = new PhotoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(ARGUMENT_FULL_PHOTO_URL)) {
            mFullPhotoUrl = bundle.getString(ARGUMENT_FULL_PHOTO_URL);
        }
        initLayout();
    }

    private void initLayout() {
        Picasso.with(getContext())
                .load(mFullPhotoUrl)
                .noPlaceholder()
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(mPhotoFull, new Callback() {
                    @Override
                    public void onSuccess() {
                        setLoadingIndicator(false);
                    }

                    @Override
                    public void onError() {
                        setLoadingIndicator(true);
                        if (mFullPhotoUrl != null) {
                            Picasso.with(getContext())
                                    .load(mFullPhotoUrl)
                                    .into(mPhotoFull, new Callback() {
                                        @Override
                                        public void onSuccess() {
                                            setLoadingIndicator(false);
                                        }

                                        @Override
                                        public void onError() {
                                            LogUtils.debug(TAG, "onError");
                                        }
                                    });
                        }
                    }
                });
    }

    private void setLoadingIndicator(boolean active) {
        ((FullPhotoFragment) getParentFragment()).setLoadingIndicator(active);
    }

    @Override
    public void onPause() {
        Picasso.with(getContext()).cancelRequest(mPhotoFull);
        super.onPause();
    }

    @Override
    protected int getTitle() {
        return 0;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_photo;
    }
}
