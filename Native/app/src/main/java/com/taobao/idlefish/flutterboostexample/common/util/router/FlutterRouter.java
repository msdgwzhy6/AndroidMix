package com.taobao.idlefish.flutterboostexample.common.util.router;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.idlefish.flutterboost.containers.BoostFlutterActivity;
import java.util.Map;

/**
 * 创建时间: 2020/10/04 14:33
 * 作者: dujunda001
 * 描述:
 */
public class FlutterRouter {
  public static String WEATHER_HOME = "weather_home";

  public static void openFlutterPage(Context context, String url, Map params, int requestCode) {
    Intent intent = BoostFlutterActivity.withNewEngine().url(url).params(params)
        .backgroundMode(BoostFlutterActivity.BackgroundMode.opaque).build(context);
    if (context instanceof Activity) {
      Activity activity = (Activity) context;
      activity.startActivityForResult(intent, requestCode);
    } else {
      context.startActivity(intent);
    }
  }

  public static void openFlutterPage(Context context, String url, Map params) {
    Intent intent = BoostFlutterActivity.withNewEngine().url(url).params(params)
        .backgroundMode(BoostFlutterActivity.BackgroundMode.opaque).build(context);
    if (context instanceof Activity) {
      Activity activity = (Activity) context;
      activity.startActivity(intent);
    } else {
      context.startActivity(intent);
    }
  }
}
