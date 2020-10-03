import 'package:flutter/cupertino.dart';
import 'package:fluttermodule/flutter_boost_example.dart';
import 'package:fluttermodule/simple_page_widgets.dart';

typedef Widget PageBuilder(String pageName, Map params, String uniqueId);

Map<String, PageBuilder> urlMap = {
  'embeded': (pageName, params, _) => EmbededFirstRouteWidget(),
  'first': (pageName, params, _) => FirstRouteWidget(),
  'firstFirst': (pageName, params, _) => FirstRouteWidget(),
  'second': (pageName, params, _) => SecondStatefulRouteWidget(),
  'secondStateful': (pageName, params, _) => SecondStatefulRouteWidget(),
  'tab': (pageName, params, _) => TabRouteWidget(),
  'platformView': (pageName, params, _) => PlatformRouteWidget(),
  'flutterFragment': (pageName, params, _) => FragmentRouteWidget(params),

  ///可以在native层通过 getContainerParams 来传递参数
  'flutterPage': (pageName, params, _) {
    print("flutterPage params:$params");
    return FlutterRouteWidget(params: params);
  }
};
