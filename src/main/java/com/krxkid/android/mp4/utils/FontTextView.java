package com.krxkid.android.mp4.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class FontTextView extends TextView {

  public FontTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
    Typeface typeFace = FontUtil.getInstance(getContext()).getTypeFace();
    this.setTextSize(20);
    this.setTypeface(typeFace);
  }

}
