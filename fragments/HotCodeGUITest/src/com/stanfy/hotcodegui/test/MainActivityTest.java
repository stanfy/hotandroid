package com.stanfy.hotcodegui.test;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;

import com.stanfy.hotcodegui.DetailsFragment;
import com.stanfy.hotcodegui.MainActivity;


public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
  
  private static final int PORTRAIT = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
  private static final int LANDSCAPE = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
  
  private MainActivity activity;
  private ListFragment titlesFragment;
  private Fragment detailsFragment;

  public MainActivityTest() {
    super(MainActivity.class);
  }
  
  @Override
  protected void setUp() throws Exception {
    super.setUp();
    
    setActivityInitialTouchMode(false);
    activity  = getActivity();
  }
  
  public void testPreConditions() {
    assertNotNull(activity);
  }
  
  public void testFragmentsCreationInPortraitMode() {
    setOrientation(PORTRAIT);
    initFragments();
    
    assertNotNull(titlesFragment);
    if (getScreenSize() == Configuration.SCREENLAYOUT_SIZE_LARGE) {
      assertNotNull(detailsFragment);
    } else {
      assertNull(detailsFragment);
    }
  }
  
  public void testFragmentsCreationInLandscapeMode() {
    setOrientation(LANDSCAPE);
    initFragments();
    
    assertNotNull(titlesFragment);
    assertNotNull(detailsFragment);
  }
  
  public void testColorChangeOnClickInLandscapeMode() {
    setOrientation(LANDSCAPE);
    initFragments();
    
    clickListItemByPosition(1);
    assertEquals(Color.GREEN, getDetailsFragmentColor());
  }

  public void testColorChangeOnClickInPortraitMode() {
    setOrientation(PORTRAIT);
    initFragments();
    
    clickListItemByPosition(2);
    
    if (getScreenSize() != Configuration.SCREENLAYOUT_SIZE_LARGE) {
      Intent intent = activity.getLastIntent();
      String className = intent.getComponent().getClassName();
      int position = intent.getExtras().getInt(DetailsFragment.ARG_COLOR);
      
      assertEquals("com.stanfy.hotcodegui.DetailsActivity", className);
      assertEquals(2, position);
    } else {
      assertEquals(Color.BLUE, getDetailsFragmentColor());
    }
  }
  
  public void testColorRestore() {
    setOrientation(LANDSCAPE);
    initFragments();
    
    clickListItemByPosition(3);
    
    setOrientation(PORTRAIT);
    initFragments();
    if (getScreenSize() != Configuration.SCREENLAYOUT_SIZE_LARGE) {
      Intent intent = activity.getLastIntent();
      int position = intent.getExtras().getInt(DetailsFragment.ARG_COLOR);
      assertEquals(3, position);
    } else {
      assertEquals(Color.YELLOW, getDetailsFragmentColor());
    }
  }
  
  @SuppressLint("NewApi")
  private int getDetailsFragmentColor() {
    final View detailsView = detailsFragment.getView();
    int currentColor = ((ColorDrawable) detailsView.getBackground()).getColor();
    return currentColor;
  }

  private void clickListItemByPosition(int position) {
    final ListView listView = titlesFragment.getListView();
    activity.runOnUiThread(new Runnable() {
      @Override
      public void run() {
        listView.requestFocus();
      }
    });
    
    for (int i = position; i > 0 ; i--) {
      sendKeys(KeyEvent.KEYCODE_DPAD_DOWN);
    }
    sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);
  }
  
  private void initFragments() {
    titlesFragment = (ListFragment) activity.getSupportFragmentManager().findFragmentById(android.R.id.list);
    detailsFragment = activity.getSupportFragmentManager().findFragmentById(com.stanfy.hotcodegui.R.id.details);
  }
  
  private void setOrientation(final int newOrientation) {
    Resources res = getInstrumentation().getTargetContext().getResources();
    int currentOrientation = res.getConfiguration().orientation;
    if (isEqual(newOrientation, currentOrientation)) { 
      return;
    }
    activity.setRequestedOrientation(newOrientation);
    setActivity(null);
    activity = getActivity();
    getInstrumentation().waitForIdleSync();
  }
  
  private int getScreenSize() {
    return activity.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
  }
  
  private boolean isEqual(final int newOrientation, final int currentOrientation) {
    if ((currentOrientation == Configuration.ORIENTATION_PORTRAIT 
       && newOrientation == PORTRAIT) 
       || 
       (currentOrientation == Configuration.ORIENTATION_LANDSCAPE 
       && newOrientation == LANDSCAPE)) {
      return true;
    } else {
      return false;
    }
  }
  
  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
  }
  
}
