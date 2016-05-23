package com.d500px.fivehundredpx;

import com.d500px.fivehundredpx.custom.action.RecyclerViewCustomActions;
import com.d500px.fivehundredpx.custom.action.ViewPagerActions;
import com.d500px.fivehundredpx.custom.assertions.RecyclerViewAssertions;
import com.d500px.fivehundredpx.custom.assertions.ViewPagerAssertions;
import com.d500px.fivehundredpx.util.MockModelPhotoResponse;
import com.d500px.fivehundredpx.util.TestUtils;
import com.d500px.fivehundredpx.views.grid_screen.PhotoGridActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.annotation.IdRes;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.test.suitebuilder.annotation.LargeTest;

import java.util.Random;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.allOf;

/**
 * Created by KD on 5/11/16.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class PhotoGridScreenTest {

    @Rule
    public ActivityTestRule<PhotoGridActivity> mPhotoGridActivityActivityTestRule =
            new ActivityTestRule<PhotoGridActivity>(PhotoGridActivity.class);

    @Before
    public void setUp() throws Exception {
        Espresso.registerIdlingResources(
                mPhotoGridActivityActivityTestRule.getActivity().getCountingIdlingResource());
    }

    @Test
    public void testItemsVisibleAfterRemoteCall() throws Exception {
        onView(withId(R.id.main_grid)).check(matches(isDisplayed()));
        onView(withId(R.id.main_grid)).check(
                RecyclerViewAssertions.hasItemsCount(getItemCount(R.id.main_grid)));
    }

    @Test
    public void orientationChange_listPersists() throws Exception {
        // Check orientation change doesn't affect item count
        int initialCount = getItemCount(R.id.main_grid);
        onView(withId(R.id.main_grid)).check(RecyclerViewAssertions.hasItemsCount(initialCount));

        // When rotating a screen
        TestUtils.rotateOrientation(mPhotoGridActivityActivityTestRule);

        // Item count doesn't changes
        onView(withId(R.id.main_grid)).check(RecyclerViewAssertions.hasItemsCount(initialCount));
    }

    @Test
    public void testFullPhotoInteraction() throws Exception {
        // Check switching activities doesn't affect item count
        int initialCount = getItemCount(R.id.main_grid);

        onView(withId(R.id.main_grid)).check(RecyclerViewAssertions.hasItemsCount(initialCount));

        //int clickOnPosition = getRandomRecyclerPosition(R.id.main_grid);
        // TODO: change to random position
        onView(withId(R.id.main_grid)).perform(
                RecyclerViewActions.actionOnItemAtPosition(4, click()));

        onView(withId(R.id.pager)).check(ViewPagerAssertions.hasItemsCount(initialCount));
        onView(allOf(withId(R.id.photo_full), isDisplayed())).check(matches(isDisplayed()));

        onView(withId(R.id.pager)).check(ViewPagerAssertions.isOnItem(4));
        Espresso.pressBack();
        onView(withId(R.id.main_grid)).check(RecyclerViewAssertions.hasItemsCount(initialCount));
    }

    @Test
    public void testItemsUpdatedOnScroll() throws Exception {
        // Check item count is updated when last item of main grid is reached
        int initialCount = getItemCount(R.id.main_grid);

        onView(withId(R.id.main_grid)).check(matches(isDisplayed()));

        // Scroll to last item on recycler view
        onView(withId(R.id.main_grid)).perform(RecyclerViewCustomActions.scrollToLastPosition());

        // check if item count is updated
        onView(withId(R.id.main_grid)).check(RecyclerViewAssertions.hasItemsCount(
                initialCount + MockModelPhotoResponse.DEFAULT_PHOTOS_COUNT));
    }

    @Test
    public void testItemsRefreshed() throws Exception {
        // Check item count is updated when new items are updated by some other activity

        int initialCount = getItemCount(R.id.main_grid);
        onView(withId(R.id.main_grid)).check(matches(isDisplayed()));

        onView(withId(R.id.main_grid)).check(RecyclerViewAssertions.hasItemsCount(initialCount));

        onView(withId(R.id.main_grid)).perform(
                RecyclerViewActions.actionOnItemAtPosition(6, click()));

        onView(withId(R.id.pager)).check(matches(isDisplayed()));
        onView(withId(R.id.pager)).check(ViewPagerAssertions.hasItemsCount(initialCount));
        // Get the new page
        onView(withId(R.id.pager)).perform(ViewPagerActions.scrollToLastPosition());

        // Check item count is updated on the view pager
        onView(withId(R.id.pager)).check(ViewPagerAssertions.hasItemsCount(
                initialCount + MockModelPhotoResponse.DEFAULT_PHOTOS_COUNT));

        // Land to previous activity
        Espresso.pressBack();

        // Check if item count is updated
        onView(withId(R.id.main_grid)).check(RecyclerViewAssertions.hasItemsCount(
                initialCount + MockModelPhotoResponse.DEFAULT_PHOTOS_COUNT));
    }

    @After
    public void tearDown() throws Exception {
        Espresso.unregisterIdlingResources(
                mPhotoGridActivityActivityTestRule.getActivity().getCountingIdlingResource());
    }

    /*Helpers*/

    /**
     * Returns random position inside recycler view
     *
     * @param recyclerId resource id
     * @return item position
     */
    private int getRandomRecyclerPosition(@IdRes int recyclerId) {
        Random ran = new Random();
        RecyclerView recyclerView = (RecyclerView) mPhotoGridActivityActivityTestRule.getActivity()
                .findViewById(recyclerId);

        int n = (recyclerView == null) ? 1 : recyclerView.getAdapter().getItemCount();

        return ran.nextInt(n);
    }

    /**
     * Returns the adapter count of the recycler view
     *
     * @param recyclerId resource id of recycler view
     * @return item count
     */
    private int getItemCount(@IdRes int recyclerId) {
        RecyclerView recyclerView = (RecyclerView) mPhotoGridActivityActivityTestRule.getActivity()
                .findViewById(recyclerId);
        return recyclerView.getAdapter().getItemCount();
    }
}
