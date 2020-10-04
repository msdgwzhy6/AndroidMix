import 'package:flutter/material.dart';

class WeatherHome extends StatefulWidget {
  final Map params;

  const WeatherHome({Key key ,this.params}) : super(key: key);

  @override
  _WeatherHomeState createState() => _WeatherHomeState();
}

class _WeatherHomeState extends State<WeatherHome> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('hello'),),
    );
  }
}
