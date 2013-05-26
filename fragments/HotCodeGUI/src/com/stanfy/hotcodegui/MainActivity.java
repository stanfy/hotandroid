package com.stanfy.hotcodegui;

import android.os.Bundle;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;

/**
 * Main activity.
 * @author Olexandr Kusakov (Stanfy - http://stanfy.com)
 */
public class MainActivity extends FragmentActivity {
  
  /** Intent for testing. */
  private Intent lastIntent;

  /** Array of color titles. */
  static String[] colorTitles = {"Red", "Green", "Blue", "Yellow", "Black"};
  /** Array of colors. */
  static int[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.BLACK};

  @Override
  protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }
  
  @Override
  public void startActivity(final Intent intent) {
    lastIntent = intent;
    super.startActivity(intent);
  }
  
  public Intent getLastIntent() {
    return lastIntent;
  }

}
