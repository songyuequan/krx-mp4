package com.krxkid.android.mp4.utils;

import android.content.Context;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.view.Gravity;
import android.view.WindowManager;
import com.goodow.realtime.channel.Bus;
import com.goodow.realtime.channel.Message;
import com.goodow.realtime.channel.MessageHandler;
import com.goodow.realtime.json.Json;
import com.goodow.realtime.json.JsonObject;
import com.google.inject.Inject;

/**
 * Description: Registry
 * Author: danhantao
 * Update: danhantao(2014-10-22 16:40)
 * Email: danhantao@yeah.net
 */
public class Registry {
  @Inject
  private Context ctx;
  @Inject
  private Bus bus;

  public void subscribe() {
    // 标注
    bus.subscribeLocal(Constant.ADDR_VIEW_SCRAWL, new MessageHandler<JsonObject>() {
      private final WindowManager mWindowManager = (WindowManager) ctx.getApplicationContext()
          .getSystemService(Context.WINDOW_SERVICE);
      private WindowManager.LayoutParams mLayoutParams;
      private DrawView mDrawView;
      private boolean mSwitch = true;
      private int screenWidth = ScreenTools.getScreenWidth(ctx);
      private int screenHeight = ScreenTools.getScreenHeight(ctx);

      @Override
      public void handle(Message<JsonObject> message) {
        if (mDrawView == null) {

          mDrawView =
              new DrawView(ctx, screenWidth, screenHeight);
          mLayoutParams = new WindowManager.LayoutParams();
          mLayoutParams.gravity = Gravity.LEFT;
          mLayoutParams.format = PixelFormat.RGBA_8888;
          mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
          mLayoutParams.x = 0;
          mLayoutParams.y = 0;
          mLayoutParams.width = screenWidth - 80;
          mLayoutParams.height = screenHeight;
          System.out.println(screenHeight+":"+screenWidth);
          mLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        JsonObject draw = message.body();
        if (draw.has("annotation")) {
          if (draw.getBoolean("annotation")) {
            if (mSwitch) {
              mWindowManager.addView(mDrawView, mLayoutParams);
              mSwitch = false;
            }
          } else {
            if (!mSwitch) {
              mWindowManager.removeView(mDrawView);
              mDrawView = null;
              mSwitch = true;
            }
          }
        } else if (draw.has("clear")) {
          if (!mSwitch) {
            mDrawView.clear();
          }
        }
      }
    });

    bus.subscribeLocal(Constant.ADDR_AUDIO, new MessageHandler<JsonObject>() {
      @Override
      public void handle(Message<JsonObject> message) {
        JsonObject body = message.body();
        String action = body.getString("action");
        AudioManager mAudioManager = (AudioManager) ctx.getSystemService(Context.AUDIO_SERVICE);
        if ("get".equalsIgnoreCase(action)) {
          JsonObject msg = Json.createObject();
          boolean mute =
              mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC) > 0 ? false : true;
          double volume =
              (double) mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
                  / mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
          if (mute) {
            mAudioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
            volume =
                (double) mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
                    / mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            mAudioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
          }
          msg.set("mute", mute).set("volume", volume);
          message.reply(msg, null);
          return;
        }
        if (action == null || "post".equalsIgnoreCase(action)) {
          // 静音
          if (body.has("mute")) {
            mAudioManager.setStreamMute(AudioManager.STREAM_MUSIC, body.getBoolean("mute"));
            // 设置音量
          } else if (body.has("volume")) {
            mAudioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
            double volume = body.getNumber("volume");
            mAudioManager
                .setStreamVolume(AudioManager.STREAM_MUSIC, (int) (mAudioManager
                        .getStreamMaxVolume(AudioManager.STREAM_MUSIC) * volume),
                    AudioManager.FLAG_SHOW_UI
                );
            // 设置增幅
          } else if (body.has("range")) {
            mAudioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
            double range = body.getNumber("range");
            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, (int) (mAudioManager
                .getStreamVolume(AudioManager.STREAM_MUSIC) + mAudioManager
                .getStreamMaxVolume(AudioManager.STREAM_MUSIC)
                * range), AudioManager.FLAG_SHOW_UI);
          }
          return;
        }
      }
    });
  }

}
