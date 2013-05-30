package com.stanfy.hotcode.part3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Database manager
 * @author Olexandr Tereshchuk
 */
public class HotDbManager extends SQLiteOpenHelper {

  /** Version. */
  private static final int DB_VERSION = 3;
  /** Name. */
  private static final String DB_NAME = "hot.db";

  public HotDbManager(final Context context) {
    super(context, DB_NAME, null, DB_VERSION);
  }

  @Override
  public void onCreate(final SQLiteDatabase db) {
    createTables(db);
  }

  @Override
  public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
    dropTables(db);
    createTables(db);
  }

  private void createTables(final SQLiteDatabase db) {
    db.execSQL(Person.Contract.CREATE_SQL);
  }

  private void dropTables(final SQLiteDatabase db) {
    db.execSQL("DROP TABLE IF EXISTS " + Person.Contract.TABLE_NAME);
  }

}
