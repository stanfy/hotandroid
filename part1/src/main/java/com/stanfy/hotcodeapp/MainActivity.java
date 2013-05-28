package com.stanfy.hotcodeapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {
  public static final String ACTION_START_EXAMPLE = "com.example.hotcode.START_EXAMPLE";

  private static final String STATE_BG_COLOR = "bg_color";

  private int currentColor;

  @Override
  protected void onCreate(final Bundle savedInstanceState) {
     super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    final View mainPanel = findViewById(R.id.main_panel);

    if (savedInstanceState != null && savedInstanceState.containsKey(STATE_BG_COLOR)) {
      switch (savedInstanceState.getInt(STATE_BG_COLOR)) {
      case 0: mainPanel.setBackgroundColor(Color.RED); break;
      case 1: mainPanel.setBackgroundColor(Color.GREEN); break;
      case 2: mainPanel.setBackgroundColor(Color.BLUE); break;
      }

    }

    findViewById(R.id.red_button).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(final View v) {
        mainPanel.setBackgroundColor(Color.RED);
        currentColor = 0;
      }
    });

    findViewById(R.id.green_button).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(final View v) {
        mainPanel.setBackgroundColor(Color.GREEN);
        currentColor = 1;
      }
    });

    findViewById(R.id.blue_button).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(final View v) {
        mainPanel.setBackgroundColor(Color.BLUE);
        currentColor = 2;
      }
    });

    findViewById(R.id.start_service_button).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(final View v) {
        startService(new Intent(MainActivity.this, ExampleService.class));
      }
    });

    throw new RuntimeException("test");

  }

  @Override
  protected void onStart() {
    super.onStart();
    System.out.println("");
  }

  @Override
  protected void onSaveInstanceState(final Bundle outState) {
    super.onSaveInstanceState(outState);

    outState.putInt(STATE_BG_COLOR, currentColor);
  }

}
