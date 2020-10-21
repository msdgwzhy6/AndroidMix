import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'dart:math';

class AnimationDemo extends StatefulWidget {
  @override
  _AnimationDemoState createState() => _AnimationDemoState();
}

class _AnimationDemoState extends State<AnimationDemo>
    with TickerProviderStateMixin {
  AnimationController animationController;
  double startWidth = 30;
  double width;

  Color color = Colors.red;
  Tween tween;
  AnimationController animationController1;

  AnimationController controller;

  Animation animation;

  double position;

  @override
  void initState() {
    super.initState();
    width = startWidth;
    animationController = AnimationController(
        vsync: this,
        duration: Duration(milliseconds: 1000),
        lowerBound: 0,
        upperBound: 1);
    animationController.addListener(() {
      setState(() {
        print(animationController.value);
        width = startWidth * (1 + animationController.value);
      });
    });

    animationController1 = AnimationController(
        vsync: this,
        duration: Duration(
          milliseconds: 1000,
        ),
        lowerBound: 0,
        upperBound: 1);
    tween = ColorTween(begin: Colors.red, end: Colors.green);
    tween.animate(animationController1).addListener(() {
      setState(() {
        color = tween.evaluate(animationController1);
      });
    });

    controller = AnimationController(
        vsync: this,
        duration: Duration(
          milliseconds: 1000,
        ),
        lowerBound: 0,
        upperBound: 1);
    //弹性
    animation = CurvedAnimation(parent: controller, curve: Curves.bounceIn);
    //使用Color
    animation.addListener(() {
      setState(() {
        position = 100 * animation.value;
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("动画使用"),
      ),
      body: body(),
    );
  }

  Widget body() {
    return Container(
      width: double.infinity,
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.center,
        children: <Widget>[
          SizedBox(
            height: 10,
          ),
          animation0(),
          animation1(),
          animation2()
          // animation2()
        ],
      ),
    );
  }

  Widget animation0() {
    return Column(
      children: <Widget>[
        Transform.rotate(
          angle: pi * animationController.value,
          child: GestureDetector(
            onTap: () {
              animationController.reverse();
            },
            child: Container(
              color: Colors.red,
              width: width,
              height: width,
            ),
          ),
        ),
        RaisedButton(
          onPressed: () {
            animationController.forward();
          },
          child: Text('开始大小角度动画'),
        )
      ],
    );
  }

  Widget animation1() {
    return Column(
      children: <Widget>[
        GestureDetector(
          onTap: () {
            animationController1.reverse();
          },
          child: Container(
            color: color,
            width: 40,
            height: 40,
          ),
        ),
        RaisedButton(
          onPressed: () {
            animationController1.forward();
          },
          child: Text('开始插值器动画'),
        )
      ],
    );
  }

  Widget animation2() {
    return Column(
      children: <Widget>[
        GestureDetector(
          onTap: () {
            controller.reverse();
          },
          child: Container(
            color: Colors.red,
            width: position,
            height: position,
          ),
        ),
        RaisedButton(
          onPressed: () {
            controller.forward();
          },
          child: Text('开始Curve动画动画'),
        )
      ],
    );
  }
}
