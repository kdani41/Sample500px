package com.d500px.fivehundredpx;

import com.d500px.fivehundredpx.di.component.AppComponent;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import android.app.Application;

/**
 * Created by KD on 2/5/16.
 * <p/>
 * Using disk cache in Picasso - <a href="http://stackoverflow.com/a/30686992/3354265">Reference</a>
 */
public class App extends Application {
    private static AppComponent mAppComponent;

    public static AppComponent getComponent() {
        return mAppComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = AppComponent.Initializer.init(this);
        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttpDownloader(this, Integer.MAX_VALUE));
        //LeakCanary.install(this);
        Picasso built = builder.build();
        if (BuildConfig.DEBUG) built.setIndicatorsEnabled(true);
        Picasso.setSingletonInstance(built);
    }
}
