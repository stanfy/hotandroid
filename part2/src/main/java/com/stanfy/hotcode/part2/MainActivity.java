package com.stanfy.hotcode.part2;

import android.os.Bundle;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;

/**
 * Main activity.
 * @author Olexandr Kusakov
 */
public class MainActivity extends FragmentActivity {
  
  /** Intent for testing. */
  private Intent lastIntent;

  /** Array of colors. */
  public static final int[] COLORS = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.BLACK};

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
