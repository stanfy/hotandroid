package com.stanfy.hotcode.part2;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Activity with color.
 * @author Olexandr Kusakov (Stanfy - http://stanfy.com)
 */
public class DetailsActivity extends FragmentActivity {

  @Override
  protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
      finish();
      return;
    } else {
      DetailsFragment details = new DetailsFragment();
      details.setArguments(getIntent().getExtras());
      getSupportFragmentManager().beginTransaction().add(android.R.id.content, details).commit();
    }
  }
}
