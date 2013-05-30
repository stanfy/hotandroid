package com.stanfy.hotcode.part3.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.stanfy.hotcode.part3.AbsHotActivity;

public class HotTest extends ActivityInstrumentationTestCase2<AbsHotActivity> {

  /** Logging TAG. */
  private static final String TAG = "HotTest";
  /** Debug flag. */
  private static final boolean DEBUG = true;

  /** API URL. */
  private static final String API_URL = "http://stanfy-engine.appspot.com/api/table";

  /** Wait time seconds. */
  private static final int WAITING_TIME = 15;

  static {
    System.setProperty("http.keepAlive", "false");
  }

  @SuppressWarnings("unchecked")
  public HotTest() throws ClassNotFoundException {
    super((Class<AbsHotActivity>) Class.forName(AbsHotActivity.TEST_CLASS_NAME));
  }

  public void testNaive() throws Exception {
    extractAdapter();
  }

  public void testServer() throws Exception {
    final AbsHotActivity a = getActivity();
    final CursorAdapter adapter = extractAdapter();
    HttpURLConnection con = null;
    Scores result = null;
    try {
      final URL api = new URL(API_URL);
      con = (HttpURLConnection) api.openConnection();
      final BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
      String line = br.readLine();
      final StringBuilder json = new StringBuilder();
      while (line != null) {
        json.append(line);
        line = br.readLine();
      }
      final String jsonStr = json.toString();
      if (DEBUG) { Log.d(TAG, "JSON: " + jsonStr); }
      result = new Gson().fromJson(jsonStr, Scores.class);
    } catch (final Throwable e) {
      Log.e(TAG, "Didn't expected that...", e);
    } finally {
      if (con != null) {
        con.disconnect();
      }
    }

    assertNotNull(result);

    final Scores scores = result;
    final CountDownLatch lock = new CountDownLatch(1);
    a.runOnUiThread(new Runnable() {

      @Override
      public void run() {
        for (int i = 0; i < adapter.getCount(); i++) {
          final Cursor cursor = (Cursor) adapter.getItem(i);
          final int col = cursor.getColumnIndexOrThrow(a.getPersonNameColumnName());
          boolean found = false;
          for (final Person p : scores) {
            if (p.name.equals(cursor.getString(col))) {
              found = true;
              break;
            }
          }
          assertTrue(found);
        }
        lock.countDown();
      }
    });
    lock.await(WAITING_TIME, TimeUnit.SECONDS);
  }

  private CursorAdapter extractAdapter() throws Exception {
    final AbsHotActivity a = getActivity();
    // wait 15 seconds to retrieve data
    for (int i = 0; i < WAITING_TIME; i++) {
      Thread.sleep(TimeUnit.SECONDS.toMillis(1));
      final CursorAdapter adapter = a.getListAdapter();
      if (adapter != null && adapter.getCount() > 0) {
        return adapter;
      }
    }
    throw new AssertionError("Timeout");
  }


  /**
   * Person model.
   * @author Olexandr Tereshchuk
   */
  private static class Person {
    /** Name. */
    @SerializedName("Name")
    private String name;
    /** Rate. */
    @SerializedName("Rate")
    private int rate;
  }

  /**
   * Scores.
   * @author Olexandr Tereshchuk
   */
  private static class Scores extends ArrayList<Person> {

    /** UID. */
    private static final long serialVersionUID = 7024372932005101290L;

  }

}
