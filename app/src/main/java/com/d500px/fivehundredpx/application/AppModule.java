package com.d500px.fivehundredpx.application;

import com.d500px.fivehundredpx.repository.PhotoData;
import com.d500px.fivehundredpx.repository.ServiceRepository;
import com.d500px.fivehundredpx.utils.Constants;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by KD on 2/5/16.
 *
 * Dagger Module at Application Level
 */
@Module
public class AppModule {

    private App mApp;

    public AppModule(App app) {
        mApp = app;
    }

    @Provides
    Context provideAppContext() {
        return mApp.getApplicationContext();
    }

    @Provides
    @Singleton
    PhotoData providePhotoData() {
        return new PhotoData();
    }

    @Provides
    ServiceRepository.FiveHundredPxApi provideWeatherService() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(ServiceRepository.FiveHundredPxApi.class);
    }
}
