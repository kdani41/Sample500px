package com.d500px.fivehundredpx.custom.action;

import org.hamcrest.Matcher;

import android.support.annotation.Nullable;
import android.support.test.espresso.PerformException;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.util.HumanReadables;
import android.support.v4.view.ViewPager;
import android.view.View;

import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by KD on 5/14/16.
 */
public final class ViewPagerActions {

    public static ViewAction scrollToPosition(final int position) {
        return new ScrollToPositionViewAction(position);
    }

    public static ViewAction scrollToLastPosition() {
        return new ScrollToPositionViewAction(null);
    }

    /**
     * {@link ViewAction} which scrolls {@link ViewPager} to a given position. See
     * {@link ViewPagerActions#scrollToPosition(int)} for more details.
     */
    private static final class ScrollToPositionViewAction implements ViewAction {
        private final Integer position;

        private ScrollToPositionViewAction(@Nullable Integer position) {
            this.position = position;
        }

        @SuppressWarnings("unchecked")
        @Override
        public Matcher<View> getConstraints() {
            return allOf(isAssignableFrom(ViewPager.class), isDisplayed());
        }

        @Override
        public String getDescription() {
            return "scroll ViewPager to position: " + position;
        }

        @Override
        public void perform(UiController uiController, View view) {
            uiController.loopMainThreadUntilIdle();
            ViewPager viewPager = (ViewPager) view;
            if (position == null) {
                viewPager.setCurrentItem((viewPager.getAdapter().getCount() - 1));
            } else {
                View viewAtPosition = viewPager.getChildAt(position);
                if (null == viewAtPosition) {
                    throw new PerformException.Builder().withActionDescription(this.toString())
                            .withViewDescription(HumanReadables.describe(viewAtPosition))
                            .withCause(new IllegalStateException("No view at position: " + position))
                            .build();
                }

                viewPager.setCurrentItem(position);
            }
        }
    }
}
