package com.taobao.idlefish.flutterboostexample.skeleton.tools.sketon;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import com.taobao.idlefish.flutterboostexample.skeleton.tools.sketon.interceptor.Interceptor;
import com.taobao.idlefish.flutterboostexample.skeleton.tools.sketon.interceptor.PackageInterceptor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建时间: 2020/11/03 18:20
 * 作者: dujunda001
 * 描述:
 */
public class ActivityCollectionFactory implements LayoutInflater.Factory2 {
  Interceptor interceptor;
  public String pageTag;
  //当前页面中收集到的View
  public Map<Class<?>, List<View>> pageViews;

  public ActivityCollectionFactory(String pageTag) {
    pageTag = pageTag;
    pageViews = new HashMap<>();
    interceptor = new PackageInterceptor();
  }

  @Override
  public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
    if (interceptor.interceptor(context, name)) {

    }
    //TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Skeleton);
    return null;
  }

  @Override
  public View onCreateView(String name, Context context, AttributeSet attrs) {
    return null;
  }
}
