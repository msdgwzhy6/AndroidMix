package com.taobao.idlefish.flutterboostexample.common.util.butterknife;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 创建时间: 2020/10/03 23:02
 * 作者: dujunda001
 * 描述:
 */
public class BindingUtil {
  public static String TAG = "djd " + BindingUtil.class.getName();

  public static <T extends Activity> void uiBind(T var) {
    //这里能拿到的的是Activity还是具体对象的属性呢
    //这里因为传进来的是Activity的对象，所以getClass拿到的是实际的class
    Class c = var.getClass();
    Log.i(TAG, "uiBind: " + c.getName());

    Field[] fields = c.getFields();
    Method[] methods = c.getMethods();
    for (Field field : fields) {
      if (field.isAnnotationPresent(ViewId.class)) {
        ViewId viewId = field.getAnnotation(ViewId.class);
        int resId = viewId.value();
        field.setAccessible(true);
        try {
          field.set(var, var.findViewById(resId));
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        }
      }
    }

    for (Method method : methods) {
      if (method.isAnnotationPresent(OnClick.class)) {
        OnClick onClick = method.getAnnotation(OnClick.class);
        View view = var.findViewById(onClick.value());
        //可以再次添加一个注解的注解，标识是onClick或者其他，通过动态代理
        view.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            try {
              method.invoke(var, v);
            } catch (IllegalAccessException e) {
              e.printStackTrace();
            } catch (InvocationTargetException e) {
              e.printStackTrace();
            }
          }
        });
      }
    }
  }
}
