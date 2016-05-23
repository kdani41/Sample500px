package com.d500px.fivehundredpx.custom.assertions;

import com.google.common.truth.Truth;

import org.hamcrest.Matcher;
import org.junit.Assert;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import static android.view.View.FIND_VIEWS_WITH_TEXT;

/**
 * Created by KD on 5/12/16.
 *
 * @see <a href="https://gist.github.com/chemouna/00b10369eb1d5b00401b">Reference</a>
 */
public final class RecyclerViewAssertions {

    public static ViewAssertion hasItemsCount(final int count) {
        return new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException e) {
                if (!(view instanceof RecyclerView)) {
                    throw e;
                }
                RecyclerView rv = (RecyclerView) view;
                Truth.assertThat(rv.getAdapter().getItemCount()).isEqualTo(count);
            }
        };
    }

    public static ViewAssertion hasHolderItemAtPosition(final int index,
                                                        final Matcher<RecyclerView.ViewHolder> viewHolderMatcher) {
        return new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException e) {
                if (!(view instanceof RecyclerView)) {
                    throw e;
                }
                RecyclerView rv = (RecyclerView) view;
                Assert.assertThat(rv.findViewHolderForAdapterPosition(index), viewHolderMatcher);
            }
        };
    }

    public static ViewAssertion hasViewWithTextAtPosition(final int index,
                                                          final CharSequence text) {
        return new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException e) {
                if (!(view instanceof RecyclerView)) {
                    throw e;
                }
                RecyclerView rv = (RecyclerView) view;
                ArrayList<View> outviews = new ArrayList<>();
                rv.findViewHolderForAdapterPosition(index).itemView.findViewsWithText(outviews,
                        text, FIND_VIEWS_WITH_TEXT);
                Truth.assert_()
                        .withFailureMessage("There's no view at index "
                                + index
                                + " of recyclerview that has text : "
                                + text)
                        .that(outviews)
                        .isNotEmpty();
            }
        };
    }

    public static ViewAssertion doesntHaveViewWithText(final String text) {
        return new ViewAssertion() {
            @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            @Override
            public void check(View view, NoMatchingViewException e) {
                if (!(view instanceof RecyclerView)) {
                    throw e;
                }
                RecyclerView rv = (RecyclerView) view;
                ArrayList<View> outviews = new ArrayList<>();
                for (int index = 0; index < rv.getAdapter().getItemCount(); index++) {
                    rv.findViewHolderForAdapterPosition(index).itemView.findViewsWithText(outviews,
                            text, FIND_VIEWS_WITH_TEXT);
                    if (outviews.size() > 0) break;
                }
                Truth.assertThat(outviews).isEmpty();
            }
        };
    }
}
