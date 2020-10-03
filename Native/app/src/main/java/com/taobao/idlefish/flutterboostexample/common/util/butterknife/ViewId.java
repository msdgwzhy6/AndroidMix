package com.taobao.idlefish.flutterboostexample.common.util.butterknife;

import androidx.annotation.IdRes;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 创建时间: 2020/10/03 23:08
 * 作者: dujunda001
 * 描述:
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ViewId {
  @IdRes
  int value();
}
