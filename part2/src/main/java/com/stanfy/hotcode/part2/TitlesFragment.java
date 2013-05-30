package com.stanfy.hotcode.part2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Fragment with colors list.
 * @author Olexandr Kusakov
 */
public class TitlesFragment extends ListFragment {

  /** Array of color titles. */
  public static final String[] COLOR_TITLES = {"Red", "Green", "Blue", "Yellow", "Black"};

  /** Key to save current position. */
  private static final String KEY_CURRENT_POS = "current_pos";
  /** Current position. */
  private int currentPosition;
  /** State with two fragments. */
  private boolean dualPane;

  @Override
  public void onActivityCreated(final Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    ColorAdapter adapter = new ColorAdapter(getActivity(), R.layout.row_list, COLOR_TITLES);
    setListAdapter(adapter);
    setListShown(true);
    
    View detailsFrame = getActivity().findViewById(R.id.details);
    dualPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;

    if (savedInstanceState != null) {
      currentPosition = savedInstanceState.getInt(KEY_CURRENT_POS, 0);
    }
    
    if (dualPane) {
      getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
      showDetails(currentPosition);
    }
  }

  @Override
  public void onListItemClick(final ListView list, final View v, final int position, final long id) {
    showDetails(position);
  }

  private void showDetails(final int position) {
    currentPosition = position;
    
    if (dualPane) {
      getListView().setItemChecked(position, true);
      DetailsFragment details = (DetailsFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.details);
      if (details == null || details.getCurrentColor() != getColor(currentPosition)) {
        details = DetailsFragment.newInstance(getColor(position));
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.details, details);
        fragmentTransaction.commit();
      }
    } else {
      getListView().clearChoices();
      Intent intent = new Intent(getActivity(), DetailsActivity.class);
      intent.putExtra(DetailsFragment.ARG_COLOR, getColor(position));
      getActivity().startActivity(intent);
    }
  }
  
  public int getColor(final int position) {
    int indx = position;
    //FIXME
    return MainActivity.COLORS[indx];
  }

  @Override
  public void onSaveInstanceState(final Bundle outState) {
    super.onSaveInstanceState(outState);
    //FIXME
    outState.putInt(KEY_CURRENT_POS, 0);
  }
  
  /**
   * Color titles adapter.
   * @author Olexandr Kusakov
   */
  public static class ColorAdapter extends ArrayAdapter<String> {
    
    public ColorAdapter(final Context context, final int textViewResourceId, final String[] objects) {
      super(context, textViewResourceId, objects);
    }
    
    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
      return super.getView(position, convertView, parent);
    }
    
    @Override
    public String getItem(final int position) {
      //FIXME
      return super.getItem(position);
    }

  }

}
