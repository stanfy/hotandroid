package com.stanfy.hotcode.part3;

import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.CursorAdapter;

/**
 * @author Olexandr Tereshchuk
 */
public abstract class AbsHotActivity extends FragmentActivity {

  /** Service action. */
  public static final String SERVICE_ACTION = "edu.hotcode.write";

  /** Target class name. */
  public static final String TEST_CLASS_NAME = "com.stanfy.hotcode.part3.MainActivity";

  public AbsHotActivity() {
    if (!getClass().getName().equals(TEST_CLASS_NAME)) {
      throw new RuntimeException("Class name must be: " + TEST_CLASS_NAME);
    }
  }


  /** @return adapter instance used in current list view */
  public abstract CursorAdapter getListAdapter();

  /** @return column name of the person's name used in cursor */
  public abstract String getPersonNameColumnName();
  /** @return column name of the person's score used in cursor */
  public abstract String getPersonScoreColumnName();

}
