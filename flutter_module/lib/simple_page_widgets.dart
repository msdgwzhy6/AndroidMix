import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_boost/flutter_boost.dart';
import 'package:fluttermodule/flutter_boost_example.dart';
import 'platform_view.dart';

class FirstRouteWidget extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => _FirstRouteWidgetState();
}

class _FirstRouteWidgetState extends State<FirstRouteWidget> {
  _FirstRouteWidgetState();

  // flutter 侧MethodChannel配置，channel name需要和native侧一致
  static const MethodChannel _methodChannel =
      MethodChannel('flutter_native_channel');
  String _systemVersion = '';

  Future<dynamic> _getPlatformVersion() async {
    try {
      final String result =
          await _methodChannel.invokeMethod('getPlatformVersion');
      print('getPlatformVersion:' + result);
      setState(() {
        _systemVersion = result;
      });
    } on PlatformException catch (e) {
      print(e.message);
    }
  }

  @override
  void initState() {
    print('initState');
    super.initState();
  }

  @override
  void didChangeDependencies() {
    print('didChangeDependencies');
    super.didChangeDependencies();
  }

  @override
  void didUpdateWidget(FirstRouteWidget oldWidget) {
    print('didUpdateWidget');
    super.didUpdateWidget(oldWidget);
  }

  @override
  void deactivate() {
    print('deactivate');
    super.deactivate();
  }

  @override
  void dispose() {
    print('[XDEBUG] - FirstRouteWidget is disposing~');
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('First Route')),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            RaisedButton(
              child: const Text('Open native page'),
              onPressed: () {
                print('open natve page!');
                FlutterBoost.singleton
                    .open('native')
                    .then((Map<dynamic, dynamic> value) {
                  print(
                      'call me when page is finished. did recieve native route result $value');
                });
              },
            ),
            RaisedButton(
              child: const Text('Open FF route'),
              onPressed: () {
                print('open FF page!');
                FlutterBoost.singleton
                    .open('firstFirst')
                    .then((Map<dynamic, dynamic> value) {
                  print(
                      'call me when page is finished. did recieve FF route result $value');
                });
              },
            ),
            RaisedButton(
              child: const Text('Open second route1'),
              onPressed: () {
                print('open second page!');
                FlutterBoost.singleton
                    .open('second')
                    .then((Map<dynamic, dynamic> value) {
                  print(
                      'call me when page is finished. did recieve second route result $value');
                });
              },
            ),
            RaisedButton(
              child: const Text('Present second stateful route'),
              onPressed: () {
                print('Present second stateful page!');
                FlutterBoost.singleton.open('secondStateful',
                    urlParams: <String, dynamic>{
                      'present': true
                    }).then((Map<dynamic, dynamic> value) {
                  print(
                      'call me when page is finished. did recieve second stateful route result $value');
                });
              },
            ),
            RaisedButton(
              child: const Text('Present second route'),
              onPressed: () {
                print('Present second page!');
                FlutterBoost.singleton.open('second',
                    urlParams: <String, dynamic>{
                      'present': true
                    }).then((Map<dynamic, dynamic> value) {
                  print(
                      'call me when page is finished. did recieve second route result $value');
                });
              },
            ),
            RaisedButton(
              child: Text(
                  'Get system version by method channel:' + _systemVersion),
              onPressed: () => _getPlatformVersion(),
            ),
          ],
        ),
      ),
    );
  }
}

class EmbededFirstRouteWidget extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    // TODO: implement createState
    return _EmbededFirstRouteWidgetState();
  }
}

class _EmbededFirstRouteWidgetState extends State<EmbededFirstRouteWidget> {
  @override
  Widget build(BuildContext context) {
    print('_EmbededFirstRouteWidgetState build called!');
    return Scaffold(
      body: Center(
        child: RaisedButton(
          child: Text('Open second route2'),
          onPressed: () {
            print("open second page!");
            FlutterBoost.singleton.open("second").then((Map value) {
              print(
                  "call me when page is finished. did recieve second route result $value");
            });
          },
        ),
      ),
    );
  }

  @override
  void dispose() {
    print('[XDEBUG]:_EmbededFirstRouteWidgetState disposing~');
    super.dispose();
  }
}

class SecondStatefulRouteWidget extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    // TODO: implement createState
    return _SecondStatefulRouteWidgetState();
  }
}

class _SecondStatefulRouteWidgetState extends State<SecondStatefulRouteWidget> {
  static const eventPlugin = const EventChannel('native_flutter_channel');
  var _streamSubscription;

  @override
  void initState() {
    super.initState();
    _streamSubscription = eventPlugin.receiveBroadcastStream().listen(_onData,
        onError: _onError, onDone: _onDone, cancelOnError: true);
  }

  void _onData(Object event) {
    print("_onData_ " + event);
    // 接收数据
    setState(() {

    });
  }

  void _onError(Object error) {
    // 发生错误时被回调
    print("_onData_ " + error);
    setState(() {
      //eventVal = "错误";
    });
  }

  void _onDone() {
    //结束时调用
    print("_onData_ done" );
  }

  @override
  void dispose() {
    super.dispose();
    if (_streamSubscription != null) {
      _streamSubscription.cancel();
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Flutter接受Native侧传来的数据"),
      ),
      body: Center(
        child: RaisedButton(
          onPressed: () {
            // Navigate back to first route when tapped.

            BoostContainerSettings settings =
                BoostContainer.of(context).settings;
            FlutterBoost.singleton.close(settings.uniqueId,
                result: <dynamic, dynamic>{"result": "data from second"});
          },
          child: Text('Go back with result!'),
        ),
      ),
    );
  }
}

class SecondRouteWidget extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Second Route"),
      ),
      body: Center(
        child: RaisedButton(
          onPressed: () {
            // Navigate back to first route when tapped.

            BoostContainerSettings settings =
                BoostContainer.of(context).settings;
            FlutterBoost.singleton.close(settings.uniqueId,
                result: <dynamic, dynamic>{"result": "data from second"});
          },
          child: Text('Go back with result!'),
        ),
      ),
    );
  }
}

class TabRouteWidget extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Tab Route"),
      ),
      body: Center(
        child: RaisedButton(
          onPressed: () {
            FlutterBoost.singleton.open("second");
          },
          child: Text('Open second route3'),
        ),
      ),
    );
  }
}

class PlatformRouteWidget extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Platform Route"),
      ),
      body: Center(
        child: RaisedButton(
          child: TextView(),
          onPressed: () {
            print("open second page!");
            FlutterBoost.singleton.open("second").then((Map value) {
              print(
                  "call me when page is finished. did recieve second route result $value");
            });
          },
        ),
      ),
    );
  }
}

class FragmentRouteWidget extends StatelessWidget {
  final Map params;

  FragmentRouteWidget(this.params);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('flutter_boost_example'),
      ),
      body: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: <Widget>[
          Container(
            margin: const EdgeInsets.only(top: 80.0),
            child: Text(
              "This is a flutter fragment",
              style: TextStyle(fontSize: 28.0, color: Colors.blue),
            ),
            alignment: AlignmentDirectional.center,
          ),
          Container(
            margin: const EdgeInsets.only(top: 32.0),
            child: Text(
              params['tag'] ?? '',
              style: TextStyle(fontSize: 28.0, color: Colors.red),
            ),
            alignment: AlignmentDirectional.center,
          ),
          Expanded(child: Container()),
          InkWell(
            child: Container(
                padding: const EdgeInsets.all(8.0),
                margin: const EdgeInsets.all(8.0),
                color: Colors.yellow,
                child: Text(
                  'open native page',
                  style: TextStyle(fontSize: 22.0, color: Colors.black),
                )),
            onTap: () => FlutterBoost.singleton.open("sample://nativePage"),
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
            onTap: () => FlutterBoost.singleton.open("sample://flutterPage"),
          ),
          InkWell(
            child: Container(
                padding: const EdgeInsets.all(8.0),
                margin: const EdgeInsets.fromLTRB(8.0, 8.0, 8.0, 80.0),
                color: Colors.yellow,
                child: Text(
                  'open flutter fragment page',
                  style: TextStyle(fontSize: 22.0, color: Colors.black),
                )),
            onTap: () =>
                FlutterBoost.singleton.open("sample://flutterFragmentPage"),
          )
        ],
      ),
    );
  }
}

class PushWidget extends StatefulWidget {
  @override
  _PushWidgetState createState() => _PushWidgetState();
}

class _PushWidgetState extends State<PushWidget> {
  VoidCallback _backPressedListenerUnsub;

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
  }

  @override
  void didChangeDependencies() {
    // TODO: implement didChangeDependencies
    super.didChangeDependencies();

//    if (_backPressedListenerUnsub == null) {
//      _backPressedListenerUnsub =
//          BoostContainer.of(context).addBackPressedListener(() {
//        if (BoostContainer.of(context).onstage &&
//            ModalRoute.of(context).isCurrent) {
//          Navigator.pop(context);
//        }
//      });
//    }
  }

  @override
  void dispose() {
    // TODO: implement dispose
    print('[XDEBUG] - PushWidget is disposing~');
    super.dispose();
    _backPressedListenerUnsub?.call();
  }

  @override
  Widget build(BuildContext context) {
    return FlutterRouteWidget(message: "Pushed Widget");
  }
}
