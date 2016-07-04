package com.d500px.fivehundredpx.views.full_screen;

import com.d500px.fivehundredpx.R;
import com.d500px.fivehundredpx.application.App;
import com.d500px.fivehundredpx.utils.ActivityUtils;
import com.d500px.fivehundredpx.views.base.BaseActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.Bind;

/**
 * Created by KD on 2/7/16.
 * <p>
 * Activity response for presenting main un-cropped size image in full screen mode.
 */
public class FullPhotoScreenActivity extends BaseActivity {
    private static final String TAG = FullPhotoScreenActivity.class.getSimpleName();
    private static final String PAGER_POSITION = TAG + ".PAGER_POSITION";

    @Bind(R.id.toolbar) Toolbar mToolbar;

    public static Intent getStartIntent(Context context, int position) {
        Intent starter = new Intent(context, FullPhotoScreenActivity.class);
        starter.putExtra(PAGER_POSITION, position);
        return starter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int pagerPosition = getIntent().getIntExtra(PAGER_POSITION, 0);
        FullPhotoFragment fullPhotoFragment =
                (FullPhotoFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (fullPhotoFragment == null) {
            fullPhotoFragment = FullPhotoFragment.newInstance(pagerPosition);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fullPhotoFragment,
                    R.id.contentFrame);
        }
        DaggerFullPhotoPresenterComponent.builder()
                .appComponent(App.getComponent())
                .fullPhotoPresenterModule(new FullPhotoPresenterModule(fullPhotoFragment))
                .build()
                .getFullPhotoPresenter();

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_photo_screen;
    }
}
