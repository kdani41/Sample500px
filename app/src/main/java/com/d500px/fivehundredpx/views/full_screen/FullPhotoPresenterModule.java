package com.d500px.fivehundredpx.views.full_screen;

import dagger.Module;
import dagger.Provides;

/**
 * Created by KD on 5/2/16.
 */
@Module
public class FullPhotoPresenterModule {

    private FullPhotoContract.View mView;

    public FullPhotoPresenterModule(FullPhotoContract.View view) {
        mView = view;
    }

    @Provides
    FullPhotoContract.View provideFullPhotoView() {
        return mView;
    }
}
