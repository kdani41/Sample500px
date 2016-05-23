package com.d500px.fivehundredpx;

import com.d500px.fivehundredpx.custom.assertions.ViewPagerAssertions;
import com.d500px.fivehundredpx.util.MockModelPhotoResponse;
import com.d500px.fivehundredpx.util.TestUtils;
import com.d500px.fivehundredpx.views.full_screen.FullPhotoScreenActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.allOf;

/**
 * Created by KD on 5/14/16.
 */
public class FullPhotoScreenTest {

    @Rule
    public ActivityTestRule<FullPhotoScreenActivity>
            mFullPhotoScreenActivityActivityTestRule =
            new ActivityTestRule<FullPhotoScreenActivity>(FullPhotoScreenActivity.class);

    @Before
    public void setUp() throws Exception {
        Espresso.registerIdlingResources(
                mFullPhotoScreenActivityActivityTestRule.getActivity().getCountingIdlingResource());
    }

    @Test
    public void testFullPhotoInteraction() throws Exception {
        onView(withId(R.id.pager)).check(matches(isDisplayed()));
        onView(withId(R.id.pager)).check(
                ViewPagerAssertions.hasItemsCount(MockModelPhotoResponse.DEFAULT_PHOTOS_COUNT));
        onView(allOf(withId(R.id.photo_full), isDisplayed())).check(matches(isDisplayed()));
    }

    @Test
    public void testViewPagerItemPersists() throws Exception {
        onView(withId(R.id.pager)).check(
                ViewPagerAssertions.hasItemsCount(MockModelPhotoResponse.DEFAULT_PHOTOS_COUNT));

        TestUtils.rotateOrientation(mFullPhotoScreenActivityActivityTestRule);
        onView(allOf(withId(R.id.photo_full), isDisplayed())).check(matches(isDisplayed()));
        onView(withId(R.id.pager)).check(
                ViewPagerAssertions.hasItemsCount(MockModelPhotoResponse.DEFAULT_PHOTOS_COUNT));
    }

    @After
    public void tearDown() throws Exception {
        Espresso.unregisterIdlingResources(
                mFullPhotoScreenActivityActivityTestRule.getActivity().getCountingIdlingResource());
    }
}
