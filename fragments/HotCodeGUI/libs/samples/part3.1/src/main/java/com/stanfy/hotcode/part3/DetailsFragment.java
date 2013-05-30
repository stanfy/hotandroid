package com.stanfy.hotcode.part3;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author Olexandr Tereshchuk - <a href="http://stanfy.com.ua">Stanfy LLC</a>
 */
public class DetailsFragment extends Fragment {

  /** Arguments. */
  public static final String ARG_PERSON = "person";

  /** Values. */
  private TextView name, score;

  /** Person instance. */
  private Person person;

  public MainActivity getOwnerActivity() { return (MainActivity) getActivity(); }

  @Override
  public void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
    person = getArguments() == null ? null : (Person) getArguments().get(ARG_PERSON);
  }

  @Override
  public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
    return inflater.inflate(R.layout.details, container, false);
  }

  @Override
  public void onViewCreated(final View view, final Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    name = (TextView) view.findViewById(R.id.person_name);
    score = (TextView) view.findViewById(R.id.person_score);

    if (person != null) {
      name.setText(person.getName());
      score.setText(String.valueOf(person.getScore()));
    }
  }

}
