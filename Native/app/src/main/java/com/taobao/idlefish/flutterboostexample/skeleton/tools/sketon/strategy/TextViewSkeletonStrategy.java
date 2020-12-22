package com.taobao.idlefish.flutterboostexample.skeleton.tools.sketon.strategy;

import android.widget.TextView;

/**
 * 创建时间: 2020/11/05 15:27
 * 作者: dujunda001
 * 描述:
 */
public class TextViewSkeletonStrategy implements SkeletonStrategy {
  TextView sourceTextView;

  public TextViewSkeletonStrategy(TextView sourceTextView) {
    this.sourceTextView = sourceTextView;
  }

  @Override
  public void showSkeleton() {

  }

  @Override
  public void showView() {

  }
}
