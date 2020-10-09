package com.taobao.idlefish.flutterboostexample.common.util.butterknife;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public final class ActivityLifeCycleMonitor implements Application.ActivityLifecycleCallbacks {

  private static String sTopActivityTag;
  private static final Map<String, ActivityRecord> sActivityRecords = new ConcurrentHashMap<>();

  private static final List<Application.ActivityLifecycleCallbacks> sCallbacks = new ArrayList<>();

  public static class ActivityRecord {
    public Activity activity;
    public boolean isForeground;

    public ActivityRecord(Activity activity, boolean isForeground) {
      this.activity = activity;
      this.isForeground = isForeground;
    }
  }

  public static synchronized void registerActivityLifecycleCallback(
      Application.ActivityLifecycleCallbacks callback) {
    if (callback != null && !sCallbacks.contains(callback)) {
      sCallbacks.add(callback);
    }
  }

  public static synchronized void unRegisterActivityLifecycleCallback(
      Application.ActivityLifecycleCallbacks callback) {
    if (callback != null) {
      sCallbacks.remove(callback);
    }
  }

  public static synchronized void clearActivityLifecycleCallbacks() {
    sCallbacks.clear();
  }

  @Override
  public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    final String tag = generateRecordTag(activity);
    sActivityRecords.put(tag, new ActivityRecord(activity, false));

    final ArrayList<Application.ActivityLifecycleCallbacks> callbacks = new ArrayList<>(sCallbacks);
    for (Application.ActivityLifecycleCallbacks callback : callbacks) {
      callback.onActivityCreated(activity, savedInstanceState);
    }
  }

  @Override
  public void onActivityStarted(Activity activity) {
    BindingUtil.uiBind(activity);
    final ArrayList<Application.ActivityLifecycleCallbacks> callbacks = new ArrayList<>(sCallbacks);
    for (Application.ActivityLifecycleCallbacks callback : callbacks) {
      callback.onActivityStarted(activity);
    }
  }

  @Override
  public void onActivityResumed(Activity activity) {

    final String tag = generateRecordTag(activity);
    sTopActivityTag = tag;
    ActivityRecord record = sActivityRecords.get(tag);
    if (record == null) {
      record = new ActivityRecord(activity, true);
      sActivityRecords.put(tag, record);
    } else {
      record.isForeground = true;
    }

    final ArrayList<Application.ActivityLifecycleCallbacks> callbacks = new ArrayList<>(sCallbacks);
    for (Application.ActivityLifecycleCallbacks callback : callbacks) {
      callback.onActivityResumed(activity);
    }
  }

  @Override
  public void onActivityPaused(Activity activity) {

    final String tag = generateRecordTag(activity);
    ActivityRecord record = sActivityRecords.get(tag);
    if (record != null) {
      record.isForeground = false;
    }

    final ArrayList<Application.ActivityLifecycleCallbacks> callbacks = new ArrayList<>(sCallbacks);
    for (Application.ActivityLifecycleCallbacks callback : callbacks) {
      callback.onActivityPaused(activity);
    }
  }

  @Override
  public void onActivityStopped(Activity activity) {

    final ArrayList<Application.ActivityLifecycleCallbacks> callbacks = new ArrayList<>(sCallbacks);
    for (Application.ActivityLifecycleCallbacks callback : callbacks) {
      callback.onActivityStopped(activity);
    }
  }

  @Override
  public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    final ArrayList<Application.ActivityLifecycleCallbacks> callbacks = new ArrayList<>(sCallbacks);
    for (Application.ActivityLifecycleCallbacks callback : callbacks) {
      callback.onActivitySaveInstanceState(activity, outState);
    }
  }

  @Override
  public void onActivityDestroyed(Activity activity) {

    sActivityRecords.remove(generateRecordTag(activity));
    if (!TextUtils.isEmpty(sTopActivityTag)) {
      ActivityRecord record = sActivityRecords.get(sTopActivityTag);
      if (record != null && record.activity == activity) {
        sTopActivityTag = null;
      }
    }

    final ArrayList<Application.ActivityLifecycleCallbacks> callbacks = new ArrayList<>(sCallbacks);
    for (Application.ActivityLifecycleCallbacks callback : callbacks) {
      callback.onActivityDestroyed(activity);
    }
  }

  private static String generateRecordTag(Object object) {
    return object.getClass().getName() + "@" + Integer.toHexString(object.hashCode());
  }

  public static ActivityRecord getTopActivityRecord() {
    if (TextUtils.isEmpty(sTopActivityTag)) {
      return null;
    }
    ActivityRecord record = sActivityRecords.get(sTopActivityTag);
    return record;
  }

  public static Activity getTopActivity() {
    ActivityRecord record = getTopActivityRecord();
    if (record != null) {
      return record.activity;
    }
    return null;
  }

  public static int getActiveActivitySize() {
    return sActivityRecords.size();
  }
}
