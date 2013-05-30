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
 * @author Olexandr Kusakov (Stanfy - http://stanfy.com)
 */
public class TitlesFragment extends ListFragment {

  /** Array of color titles. */
  public static final String[] COLOR_TITLES = {"Red", "Green", "Blue", "Yellow", "Black"};

  /** Key to save current position. */
  private static final String KEY_CURRENT_POS = "current_pos";
  /** State with two fragments. */
  private boolean dualPane;

  @Override
  public void onActivityCreated(final Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);



    View detailsFrame = null; //= getActivity().findViewById(R.id.details);
    dualPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;

    if (dualPane) {
      showDetails(0);
    }
  }

  @Override
  public void onListItemClick(final ListView list, final View v, final int position, final long id) {
    showDetails(position);
  }

  private void showDetails(final int position) {

    if (dualPane) {
//      DetailsFragment details = (DetailsFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.details);
//      if (details == null || details.getCurrentColor() != MainActivity.COLORS[position]) {
//
//
//      }
    } else {
      Intent intent = new Intent(getActivity(), DetailsActivity.class);
      intent.putExtra(DetailsFragment.ARG_COLOR, MainActivity.COLORS[position]);
      getActivity().startActivity(intent);
    }
  }





}
