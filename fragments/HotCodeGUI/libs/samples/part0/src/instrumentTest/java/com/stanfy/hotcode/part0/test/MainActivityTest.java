package com.stanfy.hotcode.part0.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.ImageView;

import com.stanfy.hotcode.part0.MainActivity;
import com.stanfy.hotcode.part0.R;


public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

  public MainActivityTest() {
    super(MainActivity.class);
  }

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    setActivityInitialTouchMode(false);
  }

  public void testContentSet() {
    assertEquals(ImageView.class, getActivity().findViewById(R.id.main_image).getClass());
  }

}
