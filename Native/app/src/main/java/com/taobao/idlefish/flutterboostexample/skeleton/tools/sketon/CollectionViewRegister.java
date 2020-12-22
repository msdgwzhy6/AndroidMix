package com.taobao.idlefish.flutterboostexample.skeleton.tools.sketon;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;
import androidx.core.view.LayoutInflaterCompat;
import androidx.fragment.app.FragmentActivity;
import java.lang.reflect.Field;

/**
 * 创建时间: 2020/11/04 15:10
 * 作者: dujunda001
 * 描述:
 */
public class CollectionViewRegister implements Application.ActivityLifecycleCallbacks {
  @Override
  public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    try {
      //Android 布局加载器 使用 mFactorySet 标记是否设置过Factory
      //如设置过抛出一次
      //设置 mFactorySet 标签为false
      Field field = LayoutInflater.class.getDeclaredField("mFactorySet");
      field.setAccessible(true);
      field.setBoolean(activity.getLayoutInflater(), false);

      field.setBoolean(activity.getLayoutInflater(), false);
    } catch (Exception e) {
      e.printStackTrace();
    }
    LayoutInflaterCompat.setFactory2(activity.getLayoutInflater(), new TextSkinChange());
    if (activity instanceof FragmentActivity) {
      ((FragmentActivity) activity).getSupportFragmentManager()
          .registerFragmentLifecycleCallbacks(new FragmentCollectionFactory(), true);
    }
  }

  @Override
  public void onActivityStarted(Activity activity) {

  }

  @Override
  public void onActivityResumed(Activity activity) {

  }

  @Override
  public void onActivityPaused(Activity activity) {

  }

  @Override
  public void onActivityStopped(Activity activity) {

  }

  @Override
  public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

  }

  @Override
  public void onActivityDestroyed(Activity activity) {

  }
}
