package com.d500px.fivehundredpx.custom.action;

import org.hamcrest.Matcher;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.Random;

import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by KD on 5/15/16.
 */
public class RecyclerViewCustomActions {

    public static ViewAction scrollToRandomPosition() {
        return new ScrollToRandomPosition();
    }

    public static ViewAction scrollToLastPosition() {
        return new ScrollToLastPositionViewAction();
    }

    /**
     * {@link ViewAction} which scrolls {@link RecyclerView} to a given position. See
     * {@link RecyclerViewCustomActions#scrollToLastPosition()} for more details.
     */
    private static final class ScrollToLastPositionViewAction implements ViewAction {

        @SuppressWarnings("unchecked")
        @Override
        public Matcher<View> getConstraints() {
            return allOf(isAssignableFrom(RecyclerView.class), isDisplayed());
        }

        @Override
        public String getDescription() {
            return "scroll RecyclerView to last position ";
        }

        @Override
        public void perform(UiController uiController, View view) {
            uiController.loopMainThreadUntilIdle();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
        }
    }

    private static final class ScrollToRandomPosition implements ViewAction {

        @SuppressWarnings("unchecked")
        @Override
        public Matcher<View> getConstraints() {
            return allOf(isAssignableFrom(RecyclerView.class), isDisplayed());
        }

        @Override
        public String getDescription() {
            return "scroll RecyclerView to last position ";
        }

        @Override
        public void perform(UiController uiController, View view) {
            Random ran = new Random();
            uiController.loopMainThreadUntilIdle();

            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.scrollToPosition(ran.nextInt(recyclerView.getAdapter().getItemCount()));
        }
    }
}
