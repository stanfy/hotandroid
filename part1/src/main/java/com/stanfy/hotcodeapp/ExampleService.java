package com.stanfy.hotcodeapp;

import java.util.concurrent.TimeUnit;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;


public class ExampleService extends IntentService {

  public static final String EXTRA_TIMEOUT = "com.stanfy.hotcodeapp.ToastService.timeout";

  private static final int DEFAULT_TIMEOUT = 5;
  private int timeout;

  public ExampleService() {
    super("ToastService");
  }

  @Override
  protected void onHandleIntent(final Intent args) {
    Log.i("ExampleService", "ExampleService started");

    timeout = args.getIntExtra(EXTRA_TIMEOUT, DEFAULT_TIMEOUT);

    try {
      Thread.sleep(TimeUnit.SECONDS.toMillis(timeout));
    } catch (final InterruptedException e) {
      e.printStackTrace();
    }

    Log.i("ExampleService", "ExampleService finished");
  }

}
