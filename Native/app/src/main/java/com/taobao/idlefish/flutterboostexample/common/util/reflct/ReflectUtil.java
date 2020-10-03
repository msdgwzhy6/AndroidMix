//package com.taobao.idlefish.flutterboostexample.common.util.reflct;
//
//import java.lang.annotation.Annotation;
//import java.lang.reflect.Field;
//import java.util.ArrayList;
//
///**
// * 创建时间: 2020/10/03 23:10
// * 作者: dujunda001
// * 描述:
// */
//public class ReflectUtil {
//  public static<T extends Annotation> Field[] getFiled(Class<T> annotation, Class c) {
//    ArrayList<Field> fieldArrayList = new ArrayList();
//    if (annotation == null || c == null) return null;
//    Field[] fields = c.getFields();
//    for (Field field : fields) {
//      if (field.isAnnotationPresent(annotation)){
//        fieldArrayList.add(field);
//      }
//    }
//    return fieldArrayList.toArray();
//  }
//}
