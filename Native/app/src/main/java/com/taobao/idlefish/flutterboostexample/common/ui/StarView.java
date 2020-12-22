package com.taobao.idlefish.flutterboostexample.common.ui;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 创建时间: 2020/10/14 18:24
 * 作者: dujunda001
 * 描述:
 */
public class StarView extends View {
  Paint mPaint;
  Path mPath;
  int alpha = 255;
  //星星外接圆的半径
  float outSize;
  //星星外接圆与内接园的直径比例
  float rate = 2;
  float innerSize;
  PointF middlePoint;
  //保存内外的五个点
  List<PointF> outPoints = new ArrayList<>();
  List<PointF> innerPoints = new ArrayList<>();

  public StarView(Context context) {
    super(context);
    init();
  }

  public StarView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public StarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  public StarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr,
      int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);

    init();
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
  }

  public void measureInfo() {
    outSize = Math.min(getWidth(), getHeight()) / 2;
    innerSize = outSize / rate;
    middlePoint = new PointF(outSize, outSize);
    outPoints.clear();
    innerPoints.clear();
    mPath.reset();
    outPoints.add(calPoint(middlePoint, -90, outSize));
    outPoints.add(calPoint(middlePoint, -18, outSize));
    outPoints.add(calPoint(middlePoint, 54, outSize));
    outPoints.add(calPoint(middlePoint, 126, outSize));
    outPoints.add(calPoint(middlePoint, 198, outSize));

    innerPoints.add(calPoint(middlePoint, -54, innerSize));
    innerPoints.add(calPoint(middlePoint, 18, innerSize));
    innerPoints.add(calPoint(middlePoint, 90, innerSize));
    innerPoints.add(calPoint(middlePoint, 162, innerSize));
    innerPoints.add(calPoint(middlePoint, 234, innerSize));
    mPath.moveTo(outPoints.get(0).x, outPoints.get(0).y);
    for (int i = 0; i < 5; i++) {
      mPath.lineTo(innerPoints.get(i).x, innerPoints.get(i).y);
      if (i != 4) {
        mPath.lineTo(outPoints.get(i + 1).x, outPoints.get(i + 1).y);
      }
    }
  }

  public void init() {
    int time = (new Random().nextInt(5) + 1) * 1000;
    mPaint = new Paint();
    mPath = new Path();
    mPaint.setStyle(Paint.Style.FILL);
    mPaint.setAntiAlias(true);
    mPaint.setDither(true);
    ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 255);
    valueAnimator.setDuration(time);
    valueAnimator.setInterpolator(new DecelerateInterpolator());
    valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
    // 重复的次数
    valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator animation) {
        alpha = (int) animation.getAnimatedValue();
        invalidate();
      }
    });
    valueAnimator.start();
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    measureInfo();
    mPaint.setARGB(alpha, 255, 255, 255);
    canvas.drawPath(mPath, mPaint);
  }
  //通过三角函数计算
  public PointF calPoint(PointF p, float angle, float length) {
    float dx = (float) (length * Math.cos(Math.toRadians(angle)));
    float dy = (float) (length * Math.sin(Math.toRadians(angle)));
    return new PointF(p.x + dx, p.y + dy);
  }
}
