package com.taobao.idlefish.flutterboostexample.skeleton.tools.sketon;

import android.app.Application;
import com.taobao.idlefish.flutterboostexample.skeleton.tools.sketon.strategy.SkeletonStrategy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建时间: 2020/11/04 10:30
 * 作者: dujunda001
 * 描述:
 */
public class SkeletonManger {
  private Application application;
  private static volatile SkeletonManger instance;

  private SkeletonManger(Application application) {
    application = application;
  }

  public static void init(Application application) {
    synchronized (SkeletonManger.class) {
      if (instance == null) {
        instance = new SkeletonManger(application);
      }
    }
  }

  public static SkeletonManger getInstance() {
    return instance;
  }

  //第一个key 对应每一个页面
  //第二个List对应每一个页面上的策略集合
  public static Map<String, List<SkeletonStrategy>> mViewCollection =
      new HashMap<>();

  public static void insertViews(String key, List<SkeletonStrategy> strategyList) {
    mViewCollection.put(key, strategyList);
  }

  public static void removeViews(String key) {
    mViewCollection.remove(key);
  }

  public static void showSkeleton(String key) {
    for (SkeletonStrategy skeletonStrategy : mViewCollection.get(key)) {
      skeletonStrategy.showSkeleton();
    }
  }

  public static void showView(String key) {
    for (SkeletonStrategy skeletonStrategy : mViewCollection.get(key)) {
      skeletonStrategy.showView();
    }
  }
}
