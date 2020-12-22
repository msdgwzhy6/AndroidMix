package com.taobao.idlefish.flutterboostexample.skeleton.tools;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.LayoutInflaterCompat;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 创建时间: 2020/10/17 15:41
 * 作者: dujunda001
 * 描述:
 */
public class TextSkinChange implements LayoutInflater.Factory2 {

  public static List<View> viewList = new ArrayList<>();
  private static final HashMap<String, Constructor<? extends View>> mConstructorMap =
      new HashMap<String, Constructor<? extends View>>();

  //记录对应VIEW的构造函数
  private static final Class<?>[] mConstructorSignature = new Class[] {
      Context.class, AttributeSet.class
  };

  @Nullable
  @Override
  public View onCreateView(@Nullable View parent, @NonNull String name, @NonNull Context context,
      @NonNull AttributeSet attrs) {
    if (name.equals("TextView")) {
      View view = createView("android.widget.TextView", context, attrs);
      viewList.add(view);
      return view;
    }
    return null;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull String name, @NonNull Context context,
      @NonNull AttributeSet attrs) {
    return null;
  }

  private View createView(String name, Context context, AttributeSet
      attrs) {
    Constructor<? extends View> constructor = findConstructor(context, name);
    try {
      return constructor.newInstance(context, attrs);
    } catch (Exception e) {
    }
    return null;
  }

  private Constructor<? extends View> findConstructor(Context context, String name) {
    Constructor<? extends View> constructor = mConstructorMap.get(name);
    if (constructor == null) {
      try {
        Class<? extends View> clazz = context.getClassLoader().loadClass
            (name).asSubclass(View.class);
        constructor = clazz.getConstructor(mConstructorSignature);
        mConstructorMap.put(name, constructor);
      } catch (Exception e) {
      }
    }
    return constructor;
  }

  public static void replace() {
    for (View view : viewList) {
      view.setBackgroundColor(78);
    }
  }

  public static void forkLayout(Activity activity) {
    LayoutInflater layoutInflater = activity.getLayoutInflater();

    try {
      //Android 布局加载器 使用 mFactorySet 标记是否设置过Factory
      //如设置过抛出一次
      //设置 mFactorySet 标签为false
      Field field = LayoutInflater.class.getDeclaredField("mFactorySet");
      field.setAccessible(true);
      field.setBoolean(layoutInflater, false);
    } catch (Exception e) {
      e.printStackTrace();
    }

    LayoutInflaterCompat.setFactory2(layoutInflater, new TextSkinChange());
  }
}
