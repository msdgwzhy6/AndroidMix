package com.taobao.idlefish.flutterboostexample.customeview;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.os.Bundle;
import androidx.constraintlayout.solver.widgets.Analyzer;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.taobao.idlefish.flutterboostexample.R;
import com.taobao.idlefish.flutterboostexample.common.ui.StarView;
import com.taobao.idlefish.flutterboostexample.common.util.base.BaseActivity;
import java.util.Random;

public class SunSetActivity extends BaseActivity {

  @BindView(R.id.bottom_plant)
  ImageView bottomPlant;
  @BindView(R.id.left_plant)
  ImageView leftPlant;
  @BindView(R.id.left_bottom_plant)
  ImageView leftBottomPlant;
  @BindView(R.id.right_plant)
  ImageView rightPlant;
  @BindView(R.id.content)
  ViewGroup mLlContent;
  @BindView(R.id.start_layout)
  ViewGroup starContent;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sun_set);
    ButterKnife.bind(this);
    initView();
    initAnimation();
  }

  private void initView() {
    //添加几个植物出现的动画
    //通过res的方式设置动画
    Animation leftPantA = AnimationUtils.loadAnimation(this, R.anim.left_right_tran200);
    leftPlant.setAnimation(leftPantA);

    //通过手写方式实现动画组合
    AnimationSet animationSet = new AnimationSet(true);
    Animation bottomTopAnimation =
        //type参数表示这个这个值参考自身还是父控件的大小
        //其中from参数和to参数取值范围0-1，都是基于View当前的位置的，当前位置为0，如果to 1的话，最终会左移动到1个单位（取决于type）的位置，from 1的话，动画从当前位置的左边一个单位开始
        new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0,
            Animation.RELATIVE_TO_SELF, 0.9f, Animation.RELATIVE_TO_SELF, 0f);
    bottomTopAnimation.setDuration(3000);
    bottomTopAnimation.setInterpolator(new DecelerateInterpolator());
    Animation alhAnimation = new AlphaAnimation(0.5f, 1f);
    alhAnimation.setDuration(3000);
    animationSet.addAnimation(alhAnimation);
    animationSet.addAnimation(bottomTopAnimation);

    bottomPlant.setAnimation(bottomTopAnimation);
    leftBottomPlant.setAnimation(animationSet);

    AnimationSet rightAnimation = new AnimationSet(true);
    Animation rightTran =
        new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0f,
            Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f);
    rightTran.setDuration(3000);
    rightTran.setInterpolator(new DecelerateInterpolator());
    rightAnimation.addAnimation(rightTran);
    rightAnimation.addAnimation(alhAnimation);
    rightPlant.setAnimation(rightAnimation);

    starContent.getViewTreeObserver().addOnGlobalLayoutListener(
        new ViewTreeObserver.OnGlobalLayoutListener() {
          @Override
          public void onGlobalLayout() {
            //一定要先remove这个监听者
            starContent.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            int width = starContent.getWidth();
            int height = starContent.getHeight();
            for (int i = 0; i < 30; i++) {
              View view = new StarView(SunSetActivity.this);

              int leftPadding = new Random().nextInt(width);
              int topPadding = new Random().nextInt(height);
              int size = new Random().nextInt(15)+5;
              ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(size, size);
              layoutParams.leftMargin = leftPadding;
              layoutParams.topMargin = topPadding;
              view.setLayoutParams(layoutParams);
              starContent.addView(view);
            }
          }
        });
  }

  private void initAnimation() {

  }
}