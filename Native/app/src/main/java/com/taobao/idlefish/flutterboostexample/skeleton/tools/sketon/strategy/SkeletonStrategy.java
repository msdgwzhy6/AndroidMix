package com.taobao.idlefish.flutterboostexample.skeleton.tools.sketon.strategy;

/**
 * 创建时间: 2020/11/05 15:25
 * 作者: dujunda001
 * 描述: 不同的View替换为骨架的策略，这里的策略可以可以完全自定义，那策略如何匹配呢
 * 比如Text Image,提供一种策略注册模式
 */

public interface SkeletonStrategy {

  //对应View显示为骨架的策略
  void showSkeleton();

  //对应View的替换策略
  void showView();
}
