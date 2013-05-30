package com.stanfy.hotcode.part2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Fragment with color.
 * @author Olexandr Kusakov
 */
public class DetailsFragment extends Fragment {

  /** Fragment argument key. */
  public static final String ARG_COLOR = "selected_color";

  public static DetailsFragment newInstance(final int color) {
    DetailsFragment fragment = new DetailsFragment();
    Bundle args = new Bundle();
    args.putInt(ARG_COLOR, color);
    fragment.setArguments(args);
    return fragment;
  }

  public int getCurrentColor() {
    Bundle args = getArguments();
    return args == null ? 0 : args.getInt(ARG_COLOR);
  }

  @Override
  public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
    View view = new View(getActivity());
    int color = getCurrentColor();
    view.setBackgroundColor(color);
    return view;
  }

}
