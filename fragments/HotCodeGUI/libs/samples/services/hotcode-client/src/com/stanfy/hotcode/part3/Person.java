package com.stanfy.hotcode.part3;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

/**
 * Person with score.
 * @author Olexandr Tereshchuk
 */
public class Person {

  /** Id. */
  private long id;
  /** Name. */
  private String name;
  /** Score. */
  private int score;

  /** @return the id */
  public long getId() { return id; }
  /** @return the name */
  public String getName() { return name; }
  /** @return the score */
  public int getScore() { return score; }


  /**
   * Database contract.
   * @author Olexandr Tereshchuk
   */
  public static class Contract {

    /** Table name. */
    public static final String TABLE_NAME = "scores";
    /** Content URI. */
    public static final Uri URI = new Uri.Builder().scheme(ContentResolver.SCHEME_CONTENT).authority(HotContentProvider.AUTHORITY).appendPath(TABLE_NAME).build();
    /** Column names. */
    public static final String COLUMN_ID = "_id", COLUMN_NAME = "name", COLUMN_SCORE = "score";
    /** Columns. */
    public static final String[] COLUMNS = new String[] { COLUMN_ID, COLUMN_NAME, COLUMN_SCORE };
    /** Column indices. */
    public static final int COL_IND_ID = 0, COL_IND_NAME = 1, COL_IND_SCORE = 2;
    /** Table create script. */
    public static final String CREATE_SQL;

    static {
      CREATE_SQL = new StringBuilder()
      .append("CREATE TABLE ")
      .append(TABLE_NAME)
      .append("(")
      .append(COLUMN_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT,")
      .append(COLUMN_NAME).append(" TEXT,")
      .append(COLUMN_SCORE).append(" INTEGER")
      .append(")")
      .toString();
    }

    public static ContentValues toContentValues(final Person p) {
      final ContentValues cv = new ContentValues(2);
      cv.put(COLUMN_NAME, p.name);
      cv.put(COLUMN_SCORE, p.score);
      return cv;
    }

    public static Person fromCursor(final Cursor c) {
      final Person p = new Person();
      p.id = c.getLong(COL_IND_ID);
      p.name = c.getString(COL_IND_NAME);
      p.score = c.getInt(COL_IND_SCORE);
      return p;
    }

    private Contract() { /*hidden*/ }
  }

}
