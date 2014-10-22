package com.krxkid.android.mp4;

import com.goodow.realtime.android.AndroidPlatform;
import com.goodow.realtime.channel.Bus;
import com.goodow.realtime.channel.impl.SimpleBus;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public class AndroidModule extends AbstractModule {
  static {
    AndroidPlatform.register();
  }

  @Override
  protected void configure() {
  }

  @Provides
  @Singleton
  Bus provideBus() {
    Bus bus = new SimpleBus();
    return bus;
  }

}
