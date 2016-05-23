package com.d500px.fivehundredpx.views.grid_screen;

import com.d500px.fivehundredpx.App;
import com.d500px.fivehundredpx.BuildConfig;
import com.d500px.fivehundredpx.R;
import com.d500px.fivehundredpx.utils.ActivityUtils;
import com.d500px.fivehundredpx.views.base.BaseActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.Bind;

/**
 * Activity holding {@link PhotosGridFragment} as it's main container.
 */
public class PhotoGridActivity extends BaseActivity {

    private static final String TAG = PhotoGridActivity.class.getSimpleName();

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(mToolbar);
        PhotosGridFragment photosGridFragment =
                (PhotosGridFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (photosGridFragment == null) {
            photosGridFragment = PhotosGridFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), photosGridFragment,
                    R.id.contentFrame);
        }

        DaggerPhotoGridPresenterComponent.builder()
                .appComponent(App.getComponent())
                .photoGridPresenterModule(new PhotoGridPresenterModule(photosGridFragment))
                .build()
                .getPhotoGridPresenter();
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about_me:
                int versionCode = BuildConfig.VERSION_CODE;
                String versionName = BuildConfig.VERSION_NAME + "-" + versionCode;
                LayoutInflater inflater = getLayoutInflater();
                TextView dialogText = (TextView) inflater.inflate(R.layout.about_me, null);
                String aboutMe = String.format(getString(R.string.about_me_text), versionName);
                dialogText.setText(Html.fromHtml(aboutMe));
                dialogText.setMovementMethod(LinkMovementMethod.getInstance());
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setView(dialogText)
                        .setTitle(getString(R.string.action_about_me))
                        .setPositiveButton(R.string.action_dismiss,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                })
                        .show();
        }
        return super.onOptionsItemSelected(item);
    }
}
