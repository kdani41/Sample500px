package com.d500px.fivehundredpx.views.grid_screen;

import dagger.Module;
import dagger.Provides;

/**
 * Created by KD on 5/2/16.
 */
@Module
public class PhotoGridPresenterModule {
    private final PhotoGridContract.View mView;

    public PhotoGridPresenterModule(PhotoGridContract.View view) {
        mView = view;
    }

    @Provides
    PhotoGridContract.View providePhotoGridContractView() {
        return mView;
    }
}
