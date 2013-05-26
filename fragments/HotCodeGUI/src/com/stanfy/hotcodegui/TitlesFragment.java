package com.stanfy.hotcodegui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Fragment with colors list.
 * @author Olexandr Kusakov (Stanfy - http://stanfy.com)
 */
public class TitlesFragment extends ListFragment {

  /** Key to save current position. */
  private static final String KEY_CURRENT_POS = "current_pos";
  /** Current position. */
  private int currentPosition;

  @Override
  public void onActivityCreated(final Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
        android.R.layout.simple_list_item_activated_1, MainActivity.colorTitles);
    setListAdapter(adapter);
    setListShown(true);

    if (savedInstanceState != null) {
      showDetails(savedInstanceState.getInt(KEY_CURRENT_POS, 0));
    }

  }

  @Override
  public void onListItemClick(final ListView list, final View v, final int position, final long id) {
    showDetails(position);
  }

  private void showDetails(final int position) {
    currentPosition = position;
    getListView().setItemChecked(position, true);

    DetailsFragment details = (DetailsFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.details);
    if (details != null && details.getView() != null) {
      details.setColor(position);
    } else {
      Intent intent = new Intent(getActivity(), DetailsActivity.class);
      intent.putExtra(DetailsFragment.ARG_SELECTED_POSITION, position);
      getActivity().startActivity(intent);
    }
  }

  @Override
  public void onSaveInstanceState(final Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putInt(KEY_CURRENT_POS, currentPosition);
  }

}
