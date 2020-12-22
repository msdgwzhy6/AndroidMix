package com.taobao.idlefish.flutterboostexample.skeleton.tools.sketon.interceptor;

import android.content.Context;

/**
 * 创建时间: 2020/11/05 11:39
 * 作者: dujunda001
 * 描述:
 */
public class PackageInterceptor implements Interceptor {
  public static final String[] mPackageFilter = { "com." };
  public static final String[] mViewFilter = { "TextView", "" };

  @Override
  public boolean interceptor(Context context, String viewName) {
    return true;
  }
}
