package com.stanfy.hotcode.part1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * Main screen with three "color" button and "start service" button.
 */
public class MainActivity extends Activity {
  public static final String ACTION_START_EXAMPLE = "com.example.hotcode.START_EXAMPLE";

  private static final String STATE_BG_COLOR = "bg_color";
  private static final int STATE_BG_COLOR_BLACK = 0;
  private static final int STATE_BG_COLOR_GREEN = 1;
  private static final int STATE_BG_COLOR_BLUE  = 2;
  private static final int STATE_BG_COLOR_RED   = 3;

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

      // FIXME: there you should restore background color
      switch (currentColor) {
          case STATE_BG_COLOR_BLACK :
              currentColor = Color.BLACK;
              break;
          case STATE_BG_COLOR_GREEN:
              currentColor = Color.GREEN;
              break;
          case STATE_BG_COLOR_BLUE:
              currentColor = Color.BLUE;
              break;
          case STATE_BG_COLOR_RED:
              currentColor = Color.RED;
              break;
          default:
              break;
      }
      mainPanel.setBackgroundColor(currentColor);
    }

    findViewById(R.id.red_button).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(final View v) {
        mainPanel.setBackgroundColor(Color.RED);
        currentColor = STATE_BG_COLOR_RED;
      }
    });

    findViewById(R.id.green_button).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(final View v) {
        // FIXME: oops, something is missed here
        mainPanel.setBackgroundColor(Color.GREEN);
        currentColor = STATE_BG_COLOR_GREEN;
      }
    });

    // FIXME: don't forget about blue button...

    findViewById(R.id.blue_button).setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View view) {
            mainPanel.setBackgroundColor(Color.BLUE);
            currentColor = STATE_BG_COLOR_BLUE;
        }
    });

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

}
