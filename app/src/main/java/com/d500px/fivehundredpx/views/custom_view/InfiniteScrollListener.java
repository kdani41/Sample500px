package com.d500px.fivehundredpx.views.custom_view;

import com.d500px.fivehundredpx.utils.LogUtils;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by KD on 5/20/16.
 * Adapted from {@link <a href="https://gist.github.com/ssinss/e06f12ef66c51252563e" />}
 */
public abstract class InfiniteScrollListener extends RecyclerView.OnScrollListener {
    public static String TAG = InfiniteScrollListener.class.getSimpleName();

    private static final int THRESHOLD = 3;
    private boolean mLoading = true;
    private int mPreviousTotal = 0;
    private CustomGridLayoutManager mLayoutManager;

    public InfiniteScrollListener(@NonNull CustomGridLayoutManager layoutManager) {
        mLayoutManager = checkNotNull(layoutManager);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        // return if scrolling upward
        if (dy < 0) return;
        final int totalItemCount = mLayoutManager.getItemCount();
        final int lastVisibleItem = mLayoutManager.findLastCompletelyVisibleItemPosition();

        if (mLoading && totalItemCount > mPreviousTotal) {
            mPreviousTotal = totalItemCount;
            mLoading = false;
        }

        if (!mLoading && (totalItemCount - lastVisibleItem) <= (THRESHOLD)) {
            LogUtils.debug(TAG, "LastItemReached");
            onLastItemReached();
            mLoading = true;
        }
    }

    public abstract void onLastItemReached();
}
