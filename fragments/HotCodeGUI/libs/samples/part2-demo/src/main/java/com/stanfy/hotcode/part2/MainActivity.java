package com.stanfy.hotcode.part2;

import android.os.Bundle;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Main activity.
 * @author Olexandr Kusakov (Stanfy - http://stanfy.com)
 */
public class MainActivity extends FragmentActivity {

  /** Array of colors. */
  public static final int[] COLORS = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.BLACK};

  @Override
  protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);


  }
  
}
