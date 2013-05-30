package com.stanfy.hotcode.part3;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.CursorAdapter;

/**
 * @author Olexandr Tereshchuk
 */
public class MainActivity extends AbsHotActivity {

  @Override
  protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  @Override
  public CursorAdapter getListAdapter() {
    final ListFragment f = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.list_fragment);
    return f == null ? null : f.getListAdapter();
  }

  @Override
  public String getPersonNameColumnName() {
    return Person.Contract.COLUMN_NAME;
  }
  @Override
  public String getPersonScoreColumnName() {
    return Person.Contract.COLUMN_SCORE;
  }

  public void showDetails(final Person person) {
    final FragmentManager fm = getSupportFragmentManager();
    final DetailsFragment f = new DetailsFragment();
    final Bundle args = new Bundle();
    args.putSerializable(DetailsFragment.ARG_PERSON, person);
    f.setArguments(args);
    fm.popBackStack();
    fm.beginTransaction().replace(R.id.fragment_container, f).addToBackStack(null).commit();
  }

}
