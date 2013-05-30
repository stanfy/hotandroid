package com.stanfy.hotcode.part3;

import java.util.ArrayList;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
  private HotDbManager dbManager;

  @Override
  public boolean onCreate() {
    dbManager = new HotDbManager(getContext());
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
  public ContentProviderResult[] applyBatch(final ArrayList<ContentProviderOperation> operations)
      throws OperationApplicationException {
    final SQLiteDatabase db = dbManager.getWritableDatabase();
    db.beginTransaction();
    try {
      final ContentProviderResult[] result = super.applyBatch(operations);
      db.setTransactionSuccessful();
      return result;
    } finally {
      db.endTransaction();
      getContext().getContentResolver().notifyChange(Person.Contract.URI, null);
    }
  }

  @Override
  public Cursor query(final Uri uri, final String[] projection, final String selection, final String[] selectionArgs, final String sortOrder) {
    return dbManager.getReadableDatabase().query(Person.Contract.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
  }

  @Override
  public Uri insert(final Uri uri, final ContentValues values) {
    final long id = dbManager.getWritableDatabase().insert(Person.Contract.TABLE_NAME, null, values);
    return Uri.withAppendedPath(uri, String.valueOf(id));
  }

  @Override
  public int delete(final Uri uri, final String selection, final String[] selectionArgs) {
    return dbManager.getWritableDatabase().delete(Person.Contract.TABLE_NAME, selection, selectionArgs);
  }

  @Override
  public int update(final Uri uri, final ContentValues values, final String selection, final String[] selectionArgs) {
    return dbManager.getWritableDatabase().update(Person.Contract.TABLE_NAME, values, selection, selectionArgs);
  }

}
