package com.stanfy.hotcodegui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Fragment with color.
 * @author Olexandr Kusakov (Stanfy - http://stanfy.com)
 */
public class DetailsFragment extends Fragment {

  /** Fragment argument key. */
  public static final String ARG_SELECTED_POSITION = "selected_position";

  public static DetailsFragment newInstance(final int index) {
    DetailsFragment fragment = new DetailsFragment();
    Bundle args = new Bundle();
    args.putInt(ARG_SELECTED_POSITION, index);
    fragment.setArguments(args);
    return fragment;
  }

  private int getShownPosition() {
    Bundle args = getArguments();
    return args == null ? 0 : args.getInt(ARG_SELECTED_POSITION);
  }

  @Override
  public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
    return new View(getActivity());
  }

  @Override
  public void onViewCreated(final View view, final Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    setColor(getShownPosition());
  }

  public void setColor(final int index) {
    getView().setBackgroundColor(MainActivity.colors[index]);
  }

}
