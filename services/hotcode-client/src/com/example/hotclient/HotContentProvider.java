package com.example.hotclient;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

/**
 * Content provider.
 * @author Olexandr Tereshchuk
 */
public class HotContentProvider extends ContentProvider {

  /** Authority. */
  public static final String AUTHORITY = "edu.hotcode";

  /** Logging tag. */
  private static final String TAG = "HotContentProvider";
  /** Debug flag. */
  private static final boolean DEBUG = false;

  /** Database manager. */
  private HotDbManager db;

  @Override
  public boolean onCreate() {
    db = new HotDbManager(getContext());
    return true;
  }

  @Override
  public String getType(final Uri uri) {
    if (Person.Contract.TABLE_NAME.equals(uri.getLastPathSegment())) {
      return ContentResolver.CURSOR_DIR_BASE_TYPE;
    }
    return ContentResolver.CURSOR_ITEM_BASE_TYPE;
  }

  @Override
  public Cursor query(final Uri uri, final String[] projection, final String selection, final String[] selectionArgs, final String sortOrder) {
    return db.getReadableDatabase().query(Person.Contract.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
  }

  @Override
  public Uri insert(final Uri uri, final ContentValues values) {
    final long id = db.getWritableDatabase().insert(Person.Contract.TABLE_NAME, null, values);
    getContext().getContentResolver().notifyChange(uri, null);
    return Uri.withAppendedPath(uri, String.valueOf(id));
  }

  @Override
  public int delete(final Uri uri, final String selection, final String[] selectionArgs) {
    return db.getWritableDatabase().delete(Person.Contract.TABLE_NAME, selection, selectionArgs);
  }

  @Override
  public int update(final Uri uri, final ContentValues values, final String selection, final String[] selectionArgs) {
    return db.getWritableDatabase().update(Person.Contract.TABLE_NAME, values, selection, selectionArgs);
  }

}
