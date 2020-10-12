package com.taobao.idlefish.flutterboostexample.common.util.base;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import com.taobao.idlefish.flutterboostexample.common.util.StatusBarUtil;

/**
 * 创建时间: 2020/10/10 17:16
 * 作者: dujunda001
 * 描述:
 */
public abstract class BaseActivity extends AppCompatActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    StatusBarUtil.transparencyBar(this);
    StatusBarUtil.setStatusFontWhite(this);

  }
}
