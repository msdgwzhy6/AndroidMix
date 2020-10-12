package com.taobao.idlefish.flutterboostexample.customeview;

import android.view.ViewGroup;
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
import com.taobao.idlefish.flutterboostexample.common.util.base.BaseActivity;

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

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sun_set);
    ButterKnife.bind(this);
    initView();
    initAnimation();
  }

  private void initView() {
    Animation leftPantA = AnimationUtils.loadAnimation(this, R.anim.left_right_tran200);
    leftPlant.setAnimation(leftPantA);

    AnimationSet animationSet = new AnimationSet(true);
    Animation bottomTopAnimation =
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

  }

  private void initAnimation() {

  }
}