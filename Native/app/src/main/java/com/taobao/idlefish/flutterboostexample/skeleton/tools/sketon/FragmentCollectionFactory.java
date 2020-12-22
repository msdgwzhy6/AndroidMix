package com.taobao.idlefish.flutterboostexample.skeleton.tools.sketon;

import android.content.Context;
import android.view.LayoutInflater;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

/**
 * 创建时间: 2020/10/27 18:04
 * 作者: dujunda001
 * 描述:
 */
public class FragmentCollectionFactory extends FragmentManager.FragmentLifecycleCallbacks {
  @Override
  public void onFragmentPreAttached(@NonNull FragmentManager fm, @NonNull Fragment f,
      @NonNull Context context) {
    super.onFragmentPreAttached(fm, f, context);
    LayoutInflater inflater = f.getActivity().getLayoutInflater();
    if (inflater.getFactory2() == null) {

    } else {

    }
  }
}
