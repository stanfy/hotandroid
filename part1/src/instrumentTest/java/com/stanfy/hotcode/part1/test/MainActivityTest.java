package com.stanfy.hotcode.part1.test;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.stanfy.hotcode.part1.MainActivity;
import com.stanfy.hotcode.part1.R;


public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

  private Activity activity;

  private Button redBtn;
  private Button greenBtn;
  private Button blueBtn;

  public MainActivityTest() {
    super(MainActivity.class);
  }

  @Override
  protected void setUp() throws Exception {
    super.setUp();

    super.setUp();

    setActivityInitialTouchMode(false);
    activity = getActivity();

    redBtn = (Button) activity.findViewById(R.id.red_button);
    greenBtn = (Button) activity.findViewById(R.id.green_button);
    blueBtn = (Button) activity.findViewById(R.id.blue_button);
  }

  public void testPreconditions() {
    assertTrue(redBtn != null);
    assertTrue(greenBtn != null);
    assertTrue(blueBtn != null);
  }

  private void pressButton(final Button button) {
    activity.runOnUiThread(new Runnable() {
      @Override
      public void run() {
        button.requestFocus();
      }
    });

    sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);
  }

  private int getMainPanelColor(final Activity activity) {
    final View mainPanel = activity.findViewById(R.id.main_panel);
    return ((ColorDrawable) mainPanel.getBackground()).getColor();
  }

  public void testBgColorChange() throws Exception {
    pressButton(redBtn);
    assertEquals(Color.RED, getMainPanelColor(activity));

    pressButton(greenBtn);
    assertEquals(Color.GREEN, getMainPanelColor(activity));

    pressButton(blueBtn);
    assertEquals(Color.BLUE, getMainPanelColor(activity));
  }

  public void testBgColorRestore() throws Throwable {
    final Instrumentation instrumentation = getInstrumentation();

    pressButton(greenBtn);

    final Bundle outState = new Bundle();
    runTestOnUiThread(new Thread() {
      @Override
      public void run() {
        instrumentation.callActivityOnSaveInstanceState(activity, outState);
      }
    });

    activity.finish();
    setActivity(null);
    activity = getActivity();

    runTestOnUiThread(new Thread() {
      @Override
      public void run() {
        instrumentation.callActivityOnCreate(activity, outState);
        instrumentation.callActivityOnRestoreInstanceState(activity, outState);
        assertEquals(Color.GREEN, getMainPanelColor(activity));
      }
    });
  }

  public void testIntentFilter() {
    final Intent intent = new Intent(MainActivity.ACTION_START_EXAMPLE);
    final ResolveInfo resolveInfo = activity.getPackageManager().resolveActivity(intent, 0);

    final ActivityInfo activity = resolveInfo.activityInfo;
    assertEquals(MainActivity.class.getCanonicalName(), activity.name);
  }
}
