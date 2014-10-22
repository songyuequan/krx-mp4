package com.krxkid.android.mp4.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

public class FontUtil {
  private static Context context;
  private static FontUtil instance = new FontUtil();
  private static Typeface typeface;

  public static FontUtil getInstance(Context context) {
    FontUtil.context = context;
    return instance;
  }

  public Typeface getTypeFace() {
    if (typeface == null) {
      typeface = Typeface.createFromAsset(context.getAssets(), "fonts/font.ttf");
    }
    return typeface;
  }

}
