package com.taobao.idlefish.flutterboostexample.common.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import com.taobao.idlefish.flutterboostexample.R;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间: 2020/10/13 11:37
 * 作者: dujunda001
 * 描述:
 */
public class StarLayout extends ViewGroup {
  int starCount = 20;
  List<View> viewList = new ArrayList<>();

  public StarLayout(Context context) {
    super(context);
  }

  public StarLayout(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    init(context, attrs);
  }

  public StarLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context, attrs);
  }

  public StarLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr,
      int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init(context, attrs);
  }

  private void init(Context context, AttributeSet attrs) {
    TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.StarLayout);
    starCount = typedArray.getInt(R.styleable.StarLayout_star_count, starCount);
    typedArray.recycle();
    for (int i = 0; i < starCount; i++) {
      StarView starView = new StarView(getContext());
      LayoutParams params = new LayoutParams(5,5);
      starView.setLayoutParams(params);
    }
  }

  @Override
  protected void onLayout(boolean changed, int l, int t, int r, int b) {
    for (View view : viewList) {
      view.layout(l,t,r,b);
    }
  }
}
