package com.taobao.idlefish.flutterboostexample.common.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间: 2020/09/29 16:39
 * 作者: dujunda001
 * 描述:
 */
public class FollowLayout extends ViewGroup {
  List<List<View>> allView = new ArrayList<>();
  List<Integer> linesHeight = new ArrayList<>();

  public FollowLayout(Context context) {
    super(context);
  }

  public FollowLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public FollowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public FollowLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    //
    allView.clear();
    linesHeight.clear();
    int selfWidth = MeasureSpec.getSize(widthMeasureSpec);
    int selfHeight = MeasureSpec.getSize(heightMeasureSpec);
    int widthMode = MeasureSpec.getMode(widthMeasureSpec);
    int heightMode = MeasureSpec.getMode(heightMeasureSpec);
    int paddingLeft = getPaddingLeft();
    int paddingRight = getPaddingRight();
    int paddingTop = getPaddingTop();
    int paddingBottom = getPaddingBottom();
    int finalHeight = 0;
    int finalWidth = 0;
    //计算自己的宽高  -> 宽度如果是精确的就不用计算了，重点在于高度
    //高度与子View能布局成多少行有关
    //可以先把子View分成多行，计算每一行的高度
    List<View> lineView = new ArrayList<>();
    int childCount = getChildCount();
    int curWidth = paddingLeft + paddingRight;
    int maxHeight = 0;
    for (int i = 0; i < childCount; i++) {
      View childView = getChildAt(i);
      if (childView.getVisibility() != GONE) {
        LayoutParams layoutParams = childView.getLayoutParams();
        int childWidth = getChildMeasureSpec(widthMeasureSpec, paddingLeft + paddingRight,
            layoutParams.width);
        int childHeight = getChildMeasureSpec(heightMeasureSpec, paddingTop + paddingBottom,
            layoutParams.height);
        childView.measure(childWidth, childHeight);
        //measureChildWithMargins(childView,widthMeasureSpec,0,heightMeasureSpec,0);
        curWidth += childView.getMeasuredWidth();
        maxHeight = Math.max(maxHeight, childView.getMeasuredHeight());
        if (curWidth > selfWidth) {
          allView.add(lineView);
          linesHeight.add(maxHeight);
          lineView = new ArrayList<>();
          finalHeight += maxHeight;
          maxHeight = 0;
          curWidth = 0;
          i--;
        } else {
          lineView.add(childView);
        }
        if (i == childCount - 1) {
          allView.add(lineView);
          linesHeight.add(maxHeight);
          finalHeight += maxHeight;
        }
      }
    }
    finalHeight += getPaddingBottom();
    finalHeight += getPaddingTop();
    int measureHeight = heightMode == MeasureSpec.EXACTLY ? selfHeight : finalHeight;
    setMeasuredDimension(selfWidth, measureHeight);
  }

  @Override
  protected void onLayout(boolean changed, int l, int t, int r, int b) {
    int topUse = getPaddingTop();
    int leftUse = getPaddingLeft();
    int maxHeight = 0;
    int lineCount = 0;
    for (List<View> views : allView) {
      for (View view : views) {
        view.layout(leftUse, topUse, leftUse + view.getMeasuredWidth(),
            topUse + linesHeight.get(lineCount));
        leftUse += view.getMeasuredWidth();
      }
      topUse += linesHeight.get(lineCount);
      leftUse = getPaddingLeft();
      lineCount++;
    }
  }
}
