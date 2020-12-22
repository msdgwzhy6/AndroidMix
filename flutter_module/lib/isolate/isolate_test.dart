import 'dart:isolate';

import 'package:flutter/material.dart';
import 'package:flutter/foundation.dart';

class IsolateTest extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return IsolateTestState();
  }
}

class IsolateTestState extends State<IsolateTest> {
  int _count = 0;

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    // create(1);
    // Future.delayed(Duration(milliseconds: 5000), () {
    //   Future(() {
    //     print('djd Future+start  ${DateTime.now().toString()}');
    //     _count = countEven(1000000000);
    //     print('djd Future+end  ${DateTime.now().toString()}');
    //
    //     // setState(() {});
    //   });
    // });
  }

  @override
  Widget build(BuildContext context) {
    return Material(
      child: Center(
        child: Column(
          children: <Widget>[
            Container(
              width: 100,
              height: 100,
              color: Colors.yellow,
              // child: CircularProgressIndicator(),
            ),
            Container(
              width: 100,
              height: 100,
              color: Colors.red,
              // child: CircularProgressIndicator(),
            ),
            Container(
              width: 100,
              height: 100,
              color: Colors.green,
              // child: CircularProgressIndicator(),
            ),
            FlatButton(
                onPressed: () async {
                  print('djd isolate+start  ${DateTime.now().toString()}');
                  // _count = await compute(asyncCountEven, 1000000000);

                  // setState(() {});
                  // await create(1);
                  // aPort.sendPort.send(1000000);
                  print('djd isolate+end  ${DateTime.now().toString()}');
                  setState(() {
                  _count=10000;
                  });
                  // _count = await asyncCountEven(1000000000);
                },
                child: Text(
                  _count.toString(),
                )),
          ],
          // mainAxisSize: MainAxisSize.min,
        ),
      ),
    );
  }

  //计算偶数的个数
  int countEven(int num) {
    int count = 0;
    while (num > 0) {
      if (num % 2 == 0) {
        count++;
      }
      num--;
    }
    return count;
  }

  static Future<int> asyncCountEven(int num) async {
    int count = 0;
    while (num > 0) {
      if (num % 2 == 0) {
        count++;
      }
      num--;
    }
    return count;
  }

  Future<void> start() async {}

  Future<dynamic> create(int n) async {
    await Isolate.spawn(buildCounter, aPort.sendPort);
    aPort.listen((msg) {
      setState(() {
        _count = msg;
      });
    });
  }

  final aPort = ReceivePort();
  final bPort = ReceivePort();

  void buildCounter(SendPort aPort) {
    aPort.send(bPort.sendPort);
    bPort.listen((msg) {
      int ans = countEven(msg);
      aPort.send(ans);
    });
  }
}
