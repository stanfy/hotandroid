package com.stanfy.hotcode.part3;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.CursorAdapter;

/**
 * @author Olexandr Tereshchuk
 */
public class MainActivity extends FragmentActivity {

  /** Service action. */
  public static final String SERVICE_ACTION = "edu.hotcode.write";

  @Override
  protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  public CursorAdapter getListAdapter() {
    final ListFragment f = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.list_fragment);
    return f == null ? null : f.getListAdapter();
  }

  public String getPersonNameColumnName() {
    //FIXME
    return null;
  }
  public String getPersonScoreColumnName() {
    //FIXME
    return null;
  }

  public void showDetails(final Person person) {
    final FragmentManager fm = getSupportFragmentManager();
    final DetailsFragment f = new DetailsFragment();
    final Bundle args = new Bundle();
    args.putSerializable(DetailsFragment.ARG_PERSON, person);
    f.setArguments(args);
    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
      fm.popBackStack();
      fm.beginTransaction().replace(R.id.fragment_container, f).addToBackStack(null).commit();
    } else {
      fm.beginTransaction().hide(fm.findFragmentById(R.id.list_fragment)).replace(R.id.fragment_container, f).addToBackStack(null).commit();
    }
  }

}
