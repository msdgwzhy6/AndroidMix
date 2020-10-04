package com.taobao.idlefish.flutterboostexample.common.util.butterknife;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间: 2020/10/03 23:02
 * 作者: dujunda001
 * 描述:
 */
public class ActivityLifeCycleMonitor
    implements Application.ActivityLifecycleCallbacks {

  List<Activity> activities = new ArrayList<>();

  @Override
  public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
    //此处回调会在调用super.onCreate执行，如果没有setContentView就会导致findView失效
    //BindingUtil.uiBind(activity);
  }

  @Override
  public void onActivityStarted(@NonNull Activity activity) {

  }

  @Override
  public void onActivityResumed(@NonNull Activity activity) {
    BindingUtil.uiBind(activity);
  }

  @Override
  public void onActivityPaused(@NonNull Activity activity) {

  }

  @Override
  public void onActivityStopped(@NonNull Activity activity) {

  }

  @Override
  public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

  }

  @Override
  public void onActivityDestroyed(@NonNull Activity activity) {

  }
}
