package com.d500px.fivehundredpx.application;

import com.d500px.fivehundredpx.di.component.DaggerAppComponent;
import com.d500px.fivehundredpx.repository.PhotoData;
import com.d500px.fivehundredpx.repository.ServiceRepository;
import com.d500px.fivehundredpx.repository.ServiceRepositoryImpl;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by KD on 2/5/16.
 *
 * Dagger component for {@link AppModule}
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(Application application);

    ServiceRepositoryImpl getRepository();

    PhotoData getPhotoData();

    ServiceRepository.FiveHundredPxApi getFiveHunderedPxApi();

    final class Initializer {
        private Initializer() {
        }

        public static AppComponent init(App app) {
            return DaggerAppComponent.builder().appModule(new AppModule(app)).build();
        }
    }
}
