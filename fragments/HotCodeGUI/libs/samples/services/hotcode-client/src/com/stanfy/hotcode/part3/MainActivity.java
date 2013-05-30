package com.stanfy.hotcode.part3;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.ListView;

/**
 * @author Olexandr Tereshchuk
 */
public class MainActivity extends AbsHotActivity implements LoaderCallbacks<Cursor> {

  /** Loader ID. */
  private static final int LOADER_ID = 1;

  /** List view. */
  private ListView list;

  @Override
  protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    list = (ListView) findViewById(android.R.id.list);

    getSupportLoaderManager().initLoader(LOADER_ID, null, this);

    startService(
        new Intent()
        .addFlags(Intent.FLAG_DEBUG_LOG_RESOLUTION)
        .setData(Person.Contract.URI)
        .setAction(SERVICE_ACTION)
        );
  }

  @Override
  public Loader<Cursor> onCreateLoader(final int id, final Bundle args) {
    return new CursorLoader(this, Person.Contract.URI, Person.Contract.COLUMNS, null, null, null);
  }

  @Override
  public void onLoadFinished(final Loader<Cursor> loader, final Cursor cursor) {
    cursor.setNotificationUri(getContentResolver(), Person.Contract.URI);
    list.setAdapter(
        new SimpleCursorAdapter(this, R.layout.list_item, cursor,
            Person.Contract.COLUMNS,
            new int[]{View.NO_ID, R.id.person_name, R.id.person_score},
            0)
        );
  }

  @Override
  public void onLoaderReset(final Loader<Cursor> loader) { /* empty */ }

  @Override
  public CursorAdapter getListAdapter() { return (CursorAdapter) list.getAdapter(); }

  @Override
  public String getPersonNameColumnName() {
    return Person.Contract.COLUMN_NAME;
  }
  @Override
  public String getPersonScoreColumnName() {
    return Person.Contract.COLUMN_SCORE;
  }

}
