package com.example.hotclient.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.hotclient.MainActivity;

public class HotTest extends ActivityInstrumentationTestCase2<MainActivity> {

  public HotTest() {
    super(MainActivity.class);
  }

  public void testListView() throws Exception {
    final MainActivity a = getActivity();
    final ListView lv = (ListView) a.findViewById(android.R.id.list);
    // wait 15 seconds to retrieve data
    for (int i = 0; i < 15; i++) {
      Thread.sleep(1 * 1000);
      final ListAdapter adapter = lv.getAdapter();
      if (adapter != null) {
        assertTrue(adapter.getCount() > 0);
        return;
      }
    }
    throw new AssertionError("Timeout");
  }

}
