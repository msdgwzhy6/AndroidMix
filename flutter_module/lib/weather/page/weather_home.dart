import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:fluttermodule/weather/common/url_const.dart';
import 'package:fluttermodule/weather/common/util/function_tools.dart';
import 'package:fluttermodule/weather/common/util/net_manager.dart';

class WeatherHome extends StatefulWidget {
  final Map params;

  const WeatherHome({Key key, this.params}) : super(key: key);

  @override
  _WeatherHomeState createState() => _WeatherHomeState();
}

class _WeatherHomeState extends State<WeatherHome> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('hello'),
      ),
      body: Container(
        child: GestureDetector(
          onTap: () {
            FunctionTools.toast('提示成功');
            NetManager.getInstance()
                .baseUrl(weatherBaseUrl)
                .get("")
                .then((value) {
              FunctionTools.toast(value.data);
            });
          },
          child: Text('点击提示'),
        ),
      ),
    );
  }
}
