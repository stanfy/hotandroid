package com.stanfy.hotcode.part3.service.test;

import android.test.ServiceTestCase;

import com.stanfy.hotcode.part3.service.HotService;

public class HotServiceTest extends ServiceTestCase<HotService> {

  public HotServiceTest() {
    super(HotService.class);
  }

  public void testDumb() throws Exception {
    assertNotNull(getService());
  }

}
