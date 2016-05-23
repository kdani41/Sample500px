package com.d500px.fivehundredpx.custom.assertions;

import com.google.common.truth.Truth;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by KD on 5/14/16.
 */
public class ViewPagerAssertions {

    public static ViewAssertion hasItemsCount(final int count) {
        return new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException e) {
                if (!(view instanceof ViewPager)) {
                    throw e;
                }
                ViewPager pager = (ViewPager) view;
                Truth.assertThat(pager.getAdapter().getCount()).isEqualTo(count);
            }
        };
    }

    public static ViewAssertion hasItemAtPosition(final int index) {
        return new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException e) {
                if (!(view instanceof ViewPager)) {
                    throw e;
                }
                ViewPager pager = (ViewPager) view;
                Truth.assertThat(index).isAtMost(pager.getAdapter().getCount());
            }
        };
    }

    public static ViewAssertion isOnItem(final int index) {
        return new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException e) {
                if (!(view instanceof ViewPager)) {
                    throw e;
                }
                ViewPager pager = (ViewPager) view;
                Truth.assertThat(pager.getCurrentItem()).isEqualTo(index);
            }
        };
    }
}
