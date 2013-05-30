package com.stanfy.hotcode.part3.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import android.app.IntentService;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.util.Log;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

/**
 * Just writing some nonsense to give Uri.
 * @author Olexandr Tereshchuk
 */
public class HotService extends IntentService {

  /** Logging TAG. */
  private static final String TAG = "HotService";
  /** Debug flag. */
  private static final boolean DEBUG = true;

  /** Actions. */
  public static final String WRITE_ACTION = "edu.hotcode.write";

  /** API URL. */
  private static final String API_URL = "http://stanfy-engine.appspot.com/api/table";

  static {
    System.setProperty("http.keepAlive", "false");
  }

  public HotService() {
    super("HotService");
  }

  @Override
  protected void onHandleIntent(final Intent intent) {
    if (DEBUG) { Log.d(TAG, "Got intent: " + intent); }
    if (WRITE_ACTION.equalsIgnoreCase(intent.getAction()) && intent.getData() != null) {

      HttpURLConnection con = null;
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
        final Scores result = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create().fromJson(jsonStr, Scores.class);
        if (result != null) {
          final ContentResolver cr = getContentResolver();
          final ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>(result.size() + 1);
          batch.add(ContentProviderOperation.newDelete(intent.getData()).build());

          final long dateOffset = 3 * 60 * 60 * 1000;
          for (final Person person : result) {
            final ContentValues cv = new ContentValues();
            cv.put("name", person.name);
            cv.put("score", person.rate);
            cv.put("date", TimeUnit.MILLISECONDS.toSeconds((person.date == null ? new Date() : person.date).getTime()));
            cv.put("cheater", person.cheater);
            batch.add(ContentProviderOperation.newInsert(intent.getData()).withValues(cv).build());
          }
          cr.applyBatch(intent.getData().getAuthority(), batch);
        }
      } catch (final Throwable e) {
        Log.e(TAG, "Didn't expected that...", e);
      } finally {
        if (con != null) {
          con.disconnect();
        }
      }

    }
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
    /** Commit date. */
    @SerializedName("Date")
    private Date date;
    /** Is cheater. */
    @SerializedName("Cheater")
    private boolean cheater;
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
