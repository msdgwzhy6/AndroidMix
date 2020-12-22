package com.taobao.idlefish.flutterboostexample.skeleton.tools.sketon;

import com.taobao.idlefish.flutterboostexample.skeleton.tools.sketon.strategy.SkeletonStrategy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建时间: 2020/11/03 17:49
 * 作者: dujunda001
 * 描述:
 */
public class ViewCollection {
  //String 对应每一个页面，
  public static Map<String, Map<Class<?>, List<SkeletonStrategy>>> mViewCollection =
      new HashMap<>();

  public static void insertViews(String key, Map<Class<?>, List<SkeletonStrategy>> strategyList) {
    mViewCollection.put(key, strategyList);
  }

  public static void removeViews(String key) {
    mViewCollection.remove(key);
  }
}

