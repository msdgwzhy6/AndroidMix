package com.taobao.idlefish.flutterboostexample;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.taobao.idlefish.flutterboostexample.common.util.butterknife.OnClick;
import com.taobao.idlefish.flutterboostexample.common.util.router.FlutterRouter;
import com.taobao.idlefish.flutterboostexample.customeview.SunSetActivity;
import com.taobao.idlefish.flutterboostexample.ndk.JNI;
import com.taobao.idlefish.flutterboostexample.skeleton.tools.TextSkinChange;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import static com.taobao.idlefish.flutterboostexample.common.util.router.FlutterRouter.ANIMATION;
import static com.taobao.idlefish.flutterboostexample.common.util.router.FlutterRouter.ISOLATE;
import static com.taobao.idlefish.flutterboostexample.common.util.router.FlutterRouter.Render;
import static com.taobao.idlefish.flutterboostexample.common.util.router.FlutterRouter.WEATHER_HOME;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
  static {
    System.loadLibrary("thread-lib");
  }

  public static String TAG = "djd " + MainActivity.class.getName();
  public static WeakReference<MainActivity> sRef;

  private TextView mOpenNative;
  private TextView mOpenFlutter;
  private TextView mOpenFlutterFragment;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    TextSkinChange.forkLayout(this);
    sRef = new WeakReference<>(this);

    setContentView(R.layout.native_page);

    mOpenNative = findViewById(R.id.open_native);
    mOpenFlutter = findViewById(R.id.open_flutter);
    mOpenFlutterFragment = findViewById(R.id.open_flutter_fragment);

    mOpenNative.setOnClickListener(this);
    mOpenFlutter.setOnClickListener(this);
    mOpenFlutterFragment.setOnClickListener(this);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    sRef.clear();
    sRef = null;
  }

  @Override
  public void onClick(View v) {
    Map params = new HashMap();
    params.put("test1", "v_test1");
    params.put("test2", "v_test2");
    //Add some params if needed.
    if (v == mOpenNative) {
      PageRouter.openPageByUrl(this, PageRouter.NATIVE_PAGE_URL, params);
    } else if (v == mOpenFlutter) {
      PageRouter.openPageByUrl(this, PageRouter.FLUTTER_PAGE_URL, params);
    } else if (v == mOpenFlutterFragment) {
      PageRouter.openPageByUrl(this, PageRouter.FLUTTER_FRAGMENT_PAGE_URL, params);
    }
  }

  @OnClick(R.id.open_weather)
  public void goToWeather(View v) {
    Log.i(TAG, "goToWeather: ");
    FlutterRouter.openFlutterPage(this, WEATHER_HOME, new HashMap(), 1);
  }

  @OnClick(R.id.sun_set)
  public void sendMessageToFlutter(View v) {
    Intent intent = new Intent(this, SunSetActivity.class);
    startActivity(intent);
  }

  @OnClick(R.id.huanfu)
  public void openSettingActivity(View v) {
    mOpenNative.setText(JNI.stringFromJNI());
  }

  @OnClick(R.id.flutter_animation)
  public void openFlutterAnimation(View v) {
    FlutterRouter.openFlutterPage(this, ANIMATION, new HashMap(), 1);
  }

  @OnClick(R.id.render)
  public void openRender(View v) {
    FlutterRouter.openFlutterPage(this, Render, new HashMap(), 1);
  }

  @OnClick(R.id.isolate)
  public void openIsolate(View v) {
    FlutterRouter.openFlutterPage(this, ISOLATE, new HashMap(), 1);
  }
}
