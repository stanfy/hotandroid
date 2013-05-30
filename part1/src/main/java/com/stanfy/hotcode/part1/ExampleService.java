package com.stanfy.hotcode.part1;

import java.util.concurrent.TimeUnit;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Extremely simple demo service just to show how we can start service and use LogCat for debug messages monitoring.
 */
public class ExampleService extends IntentService {

  private static final String LOG_TAG = "ExampleService";
  public static final String EXTRA_TIMEOUT = "com.stanfy.hotcode.part1.ToastService.timeout";
  private static final int DEFAULT_TIMEOUT = 5;

  private int timeout;

  public ExampleService() {
    super("ToastService");
  }

  @Override
  protected void onHandleIntent(final Intent args) {
    Log.i(LOG_TAG, "ExampleService started");

    timeout = args.getIntExtra(EXTRA_TIMEOUT, DEFAULT_TIMEOUT);

    try {
      Thread.sleep(TimeUnit.SECONDS.toMillis(timeout));
    } catch (final InterruptedException e) {
      e.printStackTrace();
    }

    Log.i(LOG_TAG, "ExampleService finished");
  }

}
