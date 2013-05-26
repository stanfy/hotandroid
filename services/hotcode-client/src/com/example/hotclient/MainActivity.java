package com.example.hotclient;

import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.ListView;

/**
 * @author Olexandr Tereshchuk
 */
public class MainActivity extends FragmentActivity implements LoaderCallbacks<Cursor> {

  /** Service action. */
  private static final String SERVICE_ACTION = "edu.hotcode.write";

  /** Loader ID. */
  private static final int LOADER_ID = 1;

  /** Update delay. */
  private static final long DELAY = 1000;

  /** Helper handler. */
  private final Handler handler = new Handler();

  /** Update action. */
  private final Runnable updateAction = new Runnable() {
    @Override
    public void run() {
      getSupportLoaderManager().restartLoader(LOADER_ID, null, MainActivity.this);
    }
  };

  /** Observers data updates. */
  private final ContentObserver observer = new ContentObserver(handler) {
    @Override
    public void onChange(final boolean selfChange) {
      handler.removeCallbacks(updateAction);
      handler.postDelayed(updateAction, DELAY);
    }
  };

  @Override
  protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    getContentResolver().registerContentObserver(Person.Contract.URI, true, observer);

    startService(
        new Intent()
        .addFlags(Intent.FLAG_DEBUG_LOG_RESOLUTION)
        .setData(Person.Contract.URI)
        .setAction(SERVICE_ACTION)
        );
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    getContentResolver().unregisterContentObserver(observer);
    handler.removeCallbacks(updateAction);
  }

  @Override
  public Loader<Cursor> onCreateLoader(final int id, final Bundle args) {
    return new CursorLoader(this, Person.Contract.URI, Person.Contract.COLUMNS, null, null, null);
  }

  @Override
  public void onLoadFinished(final Loader<Cursor> loader, final Cursor cursor) {
    ((ListView) findViewById(android.R.id.list)).setAdapter(
        new SimpleCursorAdapter(this, R.layout.list_item, cursor,
            Person.Contract.COLUMNS,
            new int[]{View.NO_ID, R.id.person_name, R.id.person_score},
            0)
        );
  }

  @Override
  public void onLoaderReset(final Loader<Cursor> loader) { /* empty */ }


}
