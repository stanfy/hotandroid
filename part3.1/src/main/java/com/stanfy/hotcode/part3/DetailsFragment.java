package com.stanfy.hotcode.part3;

import java.text.SimpleDateFormat;
import java.util.Locale;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Shows person details.
 * @author Olexandr Tereshchuk - <a href="http://stanfy.com.ua">Stanfy LLC</a>
 */
public class DetailsFragment extends Fragment {

  /** Arguments. */
  public static final String ARG_PERSON = "person";

  /** Values. */
  private TextView name, score, date;

  /** Person instance. */
  private Person person;

  /** Date format. */
  private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM HH:mm:ss", Locale.getDefault());

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
    date = (TextView) view.findViewById(R.id.person_date);
    if (person != null) {
      if (person.isCheater()) {
        view.findViewById(R.id.cheater).setVisibility(View.VISIBLE);
      } else {
        name.setText(person.getName());
        score.setText(String.valueOf(person.getScore()));
        date.setText(dateFormat.format(person.getDate()));
      }
    }
  }

}
