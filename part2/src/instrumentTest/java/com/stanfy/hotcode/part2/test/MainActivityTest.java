package com.stanfy.hotcode.part2.test;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.test.ActivityInstrumentationTestCase2;

import android.view.KeyEvent;
import android.widget.ListView;
import com.stanfy.hotcode.part2.DetailsFragment;
import com.stanfy.hotcode.part2.MainActivity;
import com.stanfy.hotcode.part2.TitlesFragment;

import com.jayway.android.robotium.solo.Solo;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private static final int PORTRAIT = Solo.PORTRAIT;
    private static final int LANDSCAPE = Solo.LANDSCAPE;

    private static final int[] TEST_COLORS = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.BLACK};
    private static final String[] TEST_COLOR_TITLES = {"Red", "Green", "Blue", "Yellow", "Black"};

    private Solo solo;
    private MainActivity activity;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(false);

        activity = getActivity();
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testPreConditions() {
        assertNotNull(activity);
    }

    public void testColorArrayNotModified() {
        assertEquals(TEST_COLORS.length, MainActivity.COLORS.length);
        for (int i = 0; i < TEST_COLORS.length; i++) {
            assertEquals(TEST_COLORS[i], MainActivity.COLORS[i]);
        }
    }

    public void testColorTiltesArrayNotModified() {
        assertEquals(TEST_COLOR_TITLES.length, TitlesFragment.COLOR_TITLES.length);
        for (int i = 0; i < TEST_COLOR_TITLES.length; i++) {
            assertEquals(TEST_COLOR_TITLES[i], TitlesFragment.COLOR_TITLES[i]);
        }
    }

    public void testFragmentsCreationInPortraitMode() {
        setOrientation(PORTRAIT);

        assertNotNull(getTitlesFragment());
        if (getScreenSize() == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            assertNotNull(getDerailsFragment());
        } else {
            assertNull(getDerailsFragment());
        }
    }

    public void testFragmentsCreationInLandscapeMode() {
        setOrientation(LANDSCAPE);

        assertNotNull(getTitlesFragment());
        assertNotNull(getDerailsFragment());
    }

    public void testColorChangedCorrectlyInLandscapeMode() {
        setOrientation(LANDSCAPE);

        for (int pos = 0; pos < TEST_COLORS.length; pos++) {
            clickOnList(pos == 0 ? true : false);
            assertEquals(getExpectedColor(pos), getDetailsFragmentColor());
        }
    }

    public void testColorChangedCorrectlyInPortraitMode() {
        setOrientation(PORTRAIT);

        for (int pos = 0; pos < TEST_COLORS.length; pos++) {
            clickOnList(pos == 0 ? true : false);

            if (getScreenSize() != Configuration.SCREENLAYOUT_SIZE_LARGE) {
                Intent intent = activity.getLastIntent();
                String className = intent.getComponent().getClassName();
                int color = intent.getExtras().getInt(DetailsFragment.ARG_COLOR);

                assertEquals("com.stanfy.hotcode.part2.DetailsActivity", className);
                assertEquals(getExpectedColor(pos), color);

                solo.goBack();
            } else {
                assertEquals(getExpectedColor(pos), getDetailsFragmentColor());
            }
        }
    }

    public void testColorRestore() {
        setOrientation(LANDSCAPE);
        int pos = 3;
        for (int i = 0; i < pos; i++) {
          clickOnList(false);
        }
        setOrientation(PORTRAIT);
        setOrientation(LANDSCAPE);

        assertEquals(getExpectedColor(pos), getDetailsFragmentColor());
    }

    private int getExpectedColor(final int pos) {
      int indx = Math.abs(pos - (TEST_COLORS.length - 1));
      return TEST_COLORS[indx];
    }

    private int getDetailsFragmentColor() {
        DetailsFragment detailsFragment = getDerailsFragment();
        if (detailsFragment == null) { return 0; }
        return detailsFragment.getCurrentColor();
    }

    private TitlesFragment getTitlesFragment() {
        return (TitlesFragment) activity.getSupportFragmentManager().findFragmentById(android.R.id.list);
    }

    private DetailsFragment getDerailsFragment() {
        return (DetailsFragment) activity.getSupportFragmentManager().findFragmentById(com.stanfy.hotcode.part2.R.id.details);
    }

    private void clickOnList(final boolean firstClick) {
        final ListView listView = getTitlesFragment().getListView();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listView.requestFocus();
            }
        });
        if (!firstClick) {
            sendKeys(KeyEvent.KEYCODE_DPAD_DOWN);
        }
        sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);
    }

    private void setOrientation(final int newOrientation) {
        solo.setActivityOrientation(newOrientation);
        getInstrumentation().waitForIdleSync();
        activity = (MainActivity) solo.getCurrentActivity();
    }

    private int getScreenSize() {
        return activity.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
    }

    protected void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

}
