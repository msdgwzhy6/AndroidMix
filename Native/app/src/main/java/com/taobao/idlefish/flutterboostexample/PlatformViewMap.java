package com.taobao.idlefish.flutterboostexample;

import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugin.platform.PlatformViewFactory;
import java.util.HashMap;
import java.util.Map;

/**
 * 创建时间: 2020/10/09 15:58
 * 作者: dujunda001
 * 描述:
 */
public class PlatformViewMap {
  public static final Map<String, PlatformViewFactory> PLATFORM_VIEW_FACTORY_MAP =
      new HashMap<String, PlatformViewFactory>() {
        {
          put("plugins.test/view", new TextPlatformViewFactory(StandardMessageCodec.INSTANCE));
        }
      };
}
