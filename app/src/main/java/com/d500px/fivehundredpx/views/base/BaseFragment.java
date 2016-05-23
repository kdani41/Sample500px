package com.d500px.fivehundredpx.views.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by KD on 2/5/16.
 * <p/>
 * Base Fragment for extracting common stuff from all the child fragment.
 */
public abstract class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getFragmentLayout(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        injectViews(view);
        if (getTitle() != 0) getActivity().setTitle(getTitle());
    }

    protected void injectViews(View view) {
        ButterKnife.bind(this, view);
    }

    protected abstract int getTitle();

    protected abstract int getFragmentLayout();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
