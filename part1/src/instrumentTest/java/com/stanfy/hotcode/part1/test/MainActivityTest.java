package com.stanfy.hotcode.part1.test;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
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

  public int getBackgroundColor(View view) {
    // The actual color, not the id.
    int color = Color.BLACK;

    if(view.getBackground() instanceof ColorDrawable) {
      if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
        final Bitmap bitmap = Bitmap.createBitmap(1,1, Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        final Rect bounds = new Rect();
        // If the ColorDrawable makes use of its bounds in the draw method,
        // we may not be able to get the color we want. This is not the usual
        // case before Ice Cream Sandwich (4.0.1 r1).
        // Yet, we change the bounds temporarily, just to be sure that we are
        // successful.
        ColorDrawable colorDrawable = (ColorDrawable)view.getBackground();

        bounds.set(colorDrawable.getBounds()); // Save the original bounds.
        colorDrawable.setBounds(0, 0, 1, 1); // Change the bounds.

        colorDrawable.draw(canvas);
        color = bitmap.getPixel(0, 0);

        colorDrawable.setBounds(bounds); // Restore the original bounds.
        bitmap.recycle();
      }
      else {
        color = ((ColorDrawable)view.getBackground()).getColor();
      }
    }

    return color;
  }

  private int getMainPanelColor(final Activity activity) {
    final View mainPanel = activity.findViewById(R.id.main_panel);
    return getBackgroundColor(mainPanel);
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
    runTestOnUiThread(new Runnable() {
      @Override
      public void run() {
        instrumentation.callActivityOnSaveInstanceState(activity, outState);
      }
    });

    activity.finish();
    setActivity(null);
    activity = getActivity();

    runTestOnUiThread(new Runnable() {
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
