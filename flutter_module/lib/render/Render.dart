import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';

class ContainerTest extends StatefulWidget {
  @override
  _ContainerState createState() => _ContainerState();
}

class _ContainerState extends State<ContainerTest> {
  var container1 = Container(
    width: 100,
    height: 100,
    color: Colors.red,
  );

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: container1,
    );
  }
}
