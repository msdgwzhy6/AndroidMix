import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_boost/flutter_boost.dart';
import 'package:fluttermodule/simple_page_widgets.dart';

class FlutterRouteWidget extends StatefulWidget {
  FlutterRouteWidget({this.params, this.message});

  final Map params;
  final String message;

  @override
  _FlutterRouteWidgetState createState() => _FlutterRouteWidgetState();
}

class _FlutterRouteWidgetState extends State<FlutterRouteWidget> {
  final TextEditingController _usernameController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    final String message = widget.message;
    return Scaffold(
      appBar: AppBar(
        brightness: Brightness.light,
        backgroundColor: Colors.white,
        textTheme: new TextTheme(title: TextStyle(color: Colors.black)),
        title: Text('flutter_boost_example'),
      ),
      body: SingleChildScrollView(
        child: Container(
          margin: const EdgeInsets.all(24.0),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: <Widget>[
              Container(
                margin: const EdgeInsets.only(top: 10.0, bottom: 20.0),
                child: Text(
                  message ??
                      "This is a flutter activity \n params:${widget.params}",
                  style: TextStyle(fontSize: 28.0, color: Colors.blue),
                ),
                alignment: AlignmentDirectional.center,
              ),
//                Expanded(child: Container()),
              const CupertinoTextField(
                prefix: Icon(
                  CupertinoIcons.person_solid,
                  color: CupertinoColors.lightBackgroundGray,
                  size: 28.0,
                ),
                padding: EdgeInsets.symmetric(horizontal: 6.0, vertical: 12.0),
                clearButtonMode: OverlayVisibilityMode.editing,
                textCapitalization: TextCapitalization.words,
                autocorrect: false,
                decoration: BoxDecoration(
                  border: Border(
                      bottom: BorderSide(
                          width: 0.0, color: CupertinoColors.inactiveGray)),
                ),
                placeholder: 'Name',
              ),
              InkWell(
                child: Container(
                    padding: const EdgeInsets.all(8.0),
                    margin: const EdgeInsets.all(8.0),
                    color: Colors.yellow,
                    child: Text(
                      'open native page',
                      style: TextStyle(fontSize: 22.0, color: Colors.black),
                    )),

                ///后面的参数会在native的IPlatform.startActivity方法回调中拼接到url的query部分。
                ///例如：sample://nativePage?aaa=bbb
                onTap: () => FlutterBoost.singleton
                    .open("sample://nativePage", urlParams: <dynamic, dynamic>{
                  "query": {"aaa": "bbb"}
                }),
              ),
              InkWell(
                child: Container(
                    padding: const EdgeInsets.all(8.0),
                    margin: const EdgeInsets.all(8.0),
                    color: Colors.yellow,
                    child: Text(
                      'open first',
                      style: TextStyle(fontSize: 22.0, color: Colors.black),
                    )),

                ///后面的参数会在native的IPlatform.startActivity方法回调中拼接到url的query部分。
                ///例如：sample://nativePage?aaa=bbb
                onTap: () => FlutterBoost.singleton
                    .open("first", urlParams: <dynamic, dynamic>{
                  "query": {"aaa": "bbb"}
                }),
              ),
              InkWell(
                child: Container(
                    padding: const EdgeInsets.all(8.0),
                    margin: const EdgeInsets.all(8.0),
                    color: Colors.yellow,
                    child: Text(
                      'EventMethodChanel Flutter接受Native侧传来的数据',
                      style: TextStyle(fontSize: 22.0, color: Colors.black),
                    )),

                ///后面的参数会在native的IPlatform.startActivity方法回调中拼接到url的query部分。
                ///例如：sample://nativePage?aaa=bbb
                onTap: () => FlutterBoost.singleton
                    .open("second", urlParams: <dynamic, dynamic>{
                  "query": {"aaa": "bbb"}
                }),
              ),
              InkWell(
                child: Container(
                    padding: const EdgeInsets.all(8.0),
                    margin: const EdgeInsets.all(8.0),
                    color: Colors.yellow,
                    child: Text(
                      'open tab',
                      style: TextStyle(fontSize: 22.0, color: Colors.black),
                    )),

                ///后面的参数会在native的IPlatform.startActivity方法回调中拼接到url的query部分。
                ///例如：sample://nativePage?aaa=bbb
                onTap: () => FlutterBoost.singleton
                    .open("tab", urlParams: <dynamic, dynamic>{
                  "query": {"aaa": "bbb"}
                }),
              ),
              InkWell(
                child: Container(
                    padding: const EdgeInsets.all(8.0),
                    margin: const EdgeInsets.all(8.0),
                    color: Colors.yellow,
                    child: Text(
                      'open flutter page',
                      style: TextStyle(fontSize: 22.0, color: Colors.black),
                    )),

                ///后面的参数会在native的IPlatform.startActivity方法回调中拼接到url的query部分。
                ///例如：sample://nativePage?aaa=bbb
                onTap: () => FlutterBoost.singleton
                    .open("sample://flutterPage", urlParams: <String, dynamic>{
                  "query": {"aaa": "bbb"}
                }),
              ),
              InkWell(
                child: Container(
                    padding: const EdgeInsets.all(8.0),
                    margin: const EdgeInsets.all(8.0),
                    color: Colors.yellow,
                    child: Text(
                      'push flutter widget',
                      style: TextStyle(fontSize: 22.0, color: Colors.black),
                    )),
                onTap: () {
                  Navigator.push<dynamic>(context,
                      MaterialPageRoute<dynamic>(builder: (_) => PushWidget()));
                },
              ),

              InkWell(
                child: Container(
                    padding: const EdgeInsets.all(8.0),
                    margin: const EdgeInsets.all(8.0),
                    color: Colors.yellow,
                    child: Text(
                      'push Platform demo',
                      style: TextStyle(fontSize: 22.0, color: Colors.black),
                    )),
                onTap: () {
                  Navigator.push<dynamic>(
                      context,
                      MaterialPageRoute<dynamic>(
                          builder: (_) => PlatformRouteWidget()));
                },
              ),
              InkWell(
                child: Container(
                    padding: const EdgeInsets.all(8.0),
                    margin: const EdgeInsets.all(8.0),
                    color: Colors.yellow,
                    child: Text(
                      'open flutter fragment page',
                      style: TextStyle(fontSize: 22.0, color: Colors.black),
                    )),
                onTap: () =>
                    FlutterBoost.singleton.open("sample://flutterFragmentPage"),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
