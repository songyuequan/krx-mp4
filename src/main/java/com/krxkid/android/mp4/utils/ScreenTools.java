package com.krxkid.android.mp4.utils;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import org.apache.http.conn.util.InetAddressUtils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;

public class ScreenTools {
  /*
   * 获取屏幕高度
   */
  public static int getScreenHeight(Context context) {
    DisplayMetrics dm = new DisplayMetrics();
    if (Build.VERSION.SDK_INT < 17) {
      ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
    } else {
      ((Activity) context).getWindowManager().getDefaultDisplay().getRealMetrics(dm);
    }
    int height = dm.heightPixels;
    return height;
  }

  /*
   * 获取屏幕宽度
   */
  public static int getScreenWidth(Context context) {
    DisplayMetrics dm = new DisplayMetrics();
    if (Build.VERSION.SDK_INT < 17) {
      //NEXUS 5 DisplayMetrics{density=3.0, width=1080, height=1776, scaledDensity=3.0, xdpi=442.451, ydpi=443.345}
      ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
    } else {
      //NEXUS 5 DisplayMetrics{density=3.0, width=1080, height=1920, scaledDensity=3.0, xdpi=442.451, ydpi=443.345}
      ((Activity) context).getWindowManager().getDefaultDisplay().getRealMetrics(dm);
    }
    int width = dm.widthPixels;
    return width;
  }


}
