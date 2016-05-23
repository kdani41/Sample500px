package com.d500px.fivehundredpx.views.grid_screen;

import com.d500px.fivehundredpx.R;
import com.d500px.fivehundredpx.model.Photo;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by KD on 2/5/16.
 * <p/>
 * Adapter used to represent the items on the main grid.
 * Launched by {@link PhotosGridFragment}
 */
public class PhotoGridAdapter extends RecyclerView.Adapter<PhotoGridAdapter.ViewHolder> {
    private static final long CLICK_TIME_INTERVAL = 300;
    private PhotosGridFragment.PhotoItemListener mPhotoItemListener;
    private List<Photo> mPhotoList;
    private long mLastClickTime = System.currentTimeMillis();
    private Context mContext;

    public PhotoGridAdapter(PhotosGridFragment.PhotoItemListener photoItemListener,
                            List<Photo> photoList) {
        setList(photoList);
        mPhotoItemListener = photoItemListener;
        setHasStableIds(true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mPhotoThumb.setImageBitmap(null);
        ViewCompat.setTransitionName(holder.mPhotoThumb, "view_" + position);
        holder.mPhotoThumb.setDrawingCacheEnabled(true);
        Picasso.with(mContext).cancelRequest(holder.mPhotoThumb);
        final String thumbUrl = mPhotoList.get(position).getImages().get(0).getUrl();
        Picasso.with(mContext)
                .load(thumbUrl)
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(holder.mPhotoThumb, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Picasso.with(mContext).load(thumbUrl).into(holder.mPhotoThumb);
                    }
                });
    }

    @Override
    public long getItemId(int position) {
        return mPhotoList.get(position).getPhotoId();
    }

    @Override
    public int getItemCount() {
        return mPhotoList.size();
    }

    public void replaceData(List<Photo> photos) {
        setList(photos);
        notifyDataSetChanged();
    }

    private void setList(List<Photo> photos) {
        mPhotoList = checkNotNull(photos);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.photo_thumb)
        ImageView mPhotoThumb;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            long now = System.currentTimeMillis();
            if (now - mLastClickTime < CLICK_TIME_INTERVAL) {
                return;
            }
            mLastClickTime = now;
            mPhotoItemListener.onPhotoClick(getAdapterPosition(), view);
        }
    }
}
