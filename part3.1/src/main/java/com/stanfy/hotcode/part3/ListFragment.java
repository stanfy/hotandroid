package com.stanfy.hotcode.part3;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * @author Olexandr Tereshchuk
 */
public class ListFragment extends Fragment implements LoaderCallbacks<Cursor>, OnItemClickListener {

  /** Loader ID. */
  private static final int LOADER_ID = 1;

  /** List view. */
  private ListView list;


  public MainActivity getOwnerActivity() { return (MainActivity) getActivity(); }

  @Override
  public void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
  }

  @Override
  public void onAttach(final Activity activity) {
    super.onAttach(activity);
    //FIXME
    // activity.startService(...);
  }

  @Override
  public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
    return inflater.inflate(R.layout.list_fragment, container, false);
  }

  @Override
  public void onViewCreated(final View view, final Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    list = (ListView) view.findViewById(android.R.id.list);
    list.setOnItemClickListener(this);

    getLoaderManager().initLoader(LOADER_ID, null, this);
  }

  @Override
  public Loader<Cursor> onCreateLoader(final int id, final Bundle args) {
    //FIXME
    return null;
  }

  @Override
  public void onLoadFinished(final Loader<Cursor> loader, final Cursor cursor) {
    cursor.setNotificationUri(getActivity().getContentResolver(), Person.Contract.URI);

    //FIXME
    // list.setAdapter(...);

    getView().findViewById(R.id.progress).setVisibility(View.GONE);
    list.setVisibility(View.VISIBLE);
  }

  @Override
  public void onLoaderReset(final Loader<Cursor> loader) { /* empty */ }

  @Override
  public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
    final Person person = Person.Contract.fromCursor((Cursor) list.getItemAtPosition(position));
    getOwnerActivity().showDetails(person);
  }

  public SimpleCursorAdapter getListAdapter() { return (SimpleCursorAdapter) list.getAdapter(); }
}
