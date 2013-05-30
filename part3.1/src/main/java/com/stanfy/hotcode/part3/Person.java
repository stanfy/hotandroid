package com.stanfy.hotcode.part3;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

/**
 * Person with score.
 * @author Olexandr Tereshchuk
 */
public class Person implements Serializable {

  /** serialVersionUID. */
  private static final long serialVersionUID = 4959866553612127740L;


  /** Id. */
  private long id;
  /** Name. */
  private String name;
  /** Score. */
  private int score;
  /** Last commit date. */
  private Date date;
  /** Is cheater. */
  private boolean cheater;

  /** @return the id */
  public long getId() { return id; }
  /** @return the name */
  public String getName() { return name; }
  /** @return the score */
  public int getScore() { return score; }
  /** @return the date */
  public Date getDate() { return date; }
  /** @return is cheater */
  public boolean isCheater() { return cheater; }

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
    public static final String COLUMN_ID = "_id", COLUMN_NAME = "name", COLUMN_SCORE = "score", COLUMN_DATE = "date", COLUMN_CHEATER = "cheater";
    /** Columns. */
    public static final String[] COLUMNS = new String[] { COLUMN_ID, COLUMN_NAME, COLUMN_SCORE, COLUMN_DATE, COLUMN_CHEATER };
    /** Column indices. */
    public static final int COL_IND_ID = 0, COL_IND_NAME = 1, COL_IND_SCORE = 2, COL_IND_DATE = 3, COL_IND_CHEATER = 4;
    /** Table create script. */
    public static final String CREATE_SQL;

    static {
      //FIXME
      CREATE_SQL = "";
    }

    public static ContentValues toContentValues(final Person p) {
      final ContentValues cv = new ContentValues(3);
      cv.put(COLUMN_NAME, p.name);
      cv.put(COLUMN_SCORE, p.score);
      cv.put(COLUMN_DATE, TimeUnit.MILLISECONDS.toSeconds((p.date == null ? new Date() : p.date).getTime()));
      cv.put(COLUMN_CHEATER, p.cheater ? 1 : 0);
      return cv;
    }

    public static Person fromCursor(final Cursor c) {
      final Person p = new Person();
      p.id = c.getLong(COL_IND_ID);
      p.name = c.getString(COL_IND_NAME);
      p.score = c.getInt(COL_IND_SCORE);
      p.date = new Date(TimeUnit.SECONDS.toMillis(c.getLong(COL_IND_DATE)));
      p.cheater = c.getInt(COL_IND_CHEATER) == 1;
      return p;
    }

    private Contract() { /*hidden*/ }
  }

}
