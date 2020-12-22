package com.taobao.idlefish.flutterboostexample.skeleton.tools.sketon.interceptor;

import android.content.Context;

/**
 * 创建时间: 2020/11/05 12:06
 * 作者: dujunda001
 * 描述:
 */
public interface Interceptor {
  boolean interceptor(Context context, String viewName);
}
