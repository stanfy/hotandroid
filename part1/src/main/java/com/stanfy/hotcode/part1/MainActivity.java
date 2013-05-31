package com.stanfy.hotcode.part1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;


class ColorCode {
    public static final int RED = 0;
    public static final int BLUE = 1;
    public static final int GREEN = 2;
}

/**
 * Main screen with three "color" button and "start service" button.
 */
public class MainActivity extends Activity {
  public static final String ACTION_START_EXAMPLE = "com.example.hotcode.START_EXAMPLE";

  private static final String STATE_BG_COLOR = "bg_color";

  private int currentColor;

  /**
   * The only method that must be override. You have to define activity layout in this method.
   * @param savedInstanceState values from "previous" activity. This parameter can be null (e.g. activity is started for
   *                           the first time)
   */
  @Override
  protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    final View mainPanel = findViewById(R.id.main_panel);

    if (savedInstanceState != null && savedInstanceState.containsKey(STATE_BG_COLOR)) {
      currentColor = savedInstanceState.getInt(STATE_BG_COLOR);
      switch(currentColor) {
          case ColorCode.RED: mainPanel.setBackgroundColor(Color.RED); break;
          case ColorCode.BLUE: mainPanel.setBackgroundColor(Color.BLUE); break;
          case ColorCode.GREEN: mainPanel.setBackgroundColor(Color.GREEN); break;
      }
    }

    setColorButtonListener(R.id.red_button, Color.RED, ColorCode.RED);
    setColorButtonListener(R.id.green_button, Color.GREEN, ColorCode.GREEN);
    setColorButtonListener(R.id.blue_button, Color.BLUE, ColorCode.BLUE);

    findViewById(R.id.start_service_button).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(final View v) {
        startService(new Intent(MainActivity.this, ExampleService.class));
      }
    });
  }

  /**
   * In this method you can save your current state. It will be called before activity destruction.
   * @param outState states from parent classes
   */
  @Override
  protected void onSaveInstanceState(final Bundle outState) {
    super.onSaveInstanceState(outState);

    outState.putInt(STATE_BG_COLOR, currentColor);
  }


  private void setColorButtonListener(int id, final int color, final int color_code){
      final View mainPanel = findViewById(R.id.main_panel);
      findViewById(id).setOnClickListener(new OnClickListener() {
          @Override
          public void onClick(final View v) {
              mainPanel.setBackgroundColor(color);
              currentColor = color_code;
          }
      });
  }
}
