package com.taobao.idlefish.flutterboostexample.skeleton.tools.sketon;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.LayoutInflaterCompat;
import androidx.fragment.app.FragmentActivity;
import com.taobao.idlefish.flutterboostexample.R;
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
    Log.i("djd ",
        "onCreateView: context " + context.toString() + " view name " + name);

    if (name.equals("TextView")) {
      View view = createView("android.widget.TextView", context, attrs);
      TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Skeleton);

      if (a.getBoolean(R.styleable.Skeleton_show, false)) {
        viewList.add(view);
      }
      return view;
    }
    if (name.equals("ImageView")) {
      View view = createView("android.widget.ImageView", context, attrs);
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
      (view).setBackgroundColor(Color.parseColor("#AAAAAA"));
      if (view instanceof TextView) ((TextView) view).setTextColor(Color.parseColor("#AAAAAA"));
      if (view instanceof ImageView) {
        ((ImageView) view).setImageAlpha(Color.parseColor("#FF000000"));
      }
    }
  }

  public static void back() {
    for (View view : viewList) {
      (view).setBackgroundColor(Color.parseColor("#ffffff"));
      if (view instanceof TextView) ((TextView) view).setTextColor(Color.parseColor("#000000"));
      if (view instanceof ImageView) ((ImageView) view).setImageAlpha(Color.parseColor("#000000"));
    }
  }

  public static void forkLayout() {
    LayoutInflater layoutInflater = getPluginLayoutInflater(null);
    try {
      //Android 布局加载器 使用 mFactorySet 标记是否设置过Factory
      //如设置过抛出一次
      //设置 mFactorySet 标签为false
      Field field = LayoutInflater.class.getDeclaredField("mFactorySet");
      field.setAccessible(true);
      //field.setBoolean(layoutInflater, false);
    } catch (Exception e) {
      e.printStackTrace();
    }

    LayoutInflaterCompat.setFactory2(layoutInflater, new TextSkinChange());
  }

  public static void forkLayout(Activity activity) {
    LayoutInflater layoutInflater = getPluginLayoutInflater(activity);
    try {
      //Android 布局加载器 使用 mFactorySet 标记是否设置过Factory
      //如设置过抛出一次
      //设置 mFactorySet 标签为false
      Field field = LayoutInflater.class.getDeclaredField("mFactorySet");
      field.setAccessible(true);
      field.setBoolean(layoutInflater, false);

      field.setBoolean(activity.getLayoutInflater(), false);
    } catch (Exception e) {
      e.printStackTrace();
    }
    LayoutInflaterCompat.setFactory2(activity.getLayoutInflater(), new TextSkinChange());
    if (activity instanceof FragmentActivity) {
      ((FragmentActivity) activity).getSupportFragmentManager()
          .registerFragmentLifecycleCallbacks(new FragmentCollectionFactory(), true);
    }
    //LayoutInflaterCompat.setFactory2(layoutInflater, new TextSkinChange());
  }

  protected static LayoutInflater getPluginLayoutInflater(Activity activity) {
    Context c = activity;
    return (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }
}
