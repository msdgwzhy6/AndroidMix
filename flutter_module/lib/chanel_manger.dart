import 'package:flutter/services.dart';

/// Copyright 2020 ke.com. All rights reserved.
/// @author dujunda001@ke.com
/// @date     2020/10/9 4:36 PM
/// @desc    管理MethodChanel和Event等Chanel
///
class ChanelManger {
  static ChanelManger _instance;
  MethodChannel _methodChannel;
  EventChannel _eventChannel;

  static ChanelManger instance() {
    if (_instance == null) {
      _instance = ChanelManger.construct();
    }
    return _instance;
  }

  ChanelManger.construct() {
    _methodChannel = MethodChannel('flutter_native_channel');
    _eventChannel = EventChannel('native_flutter_channel');
  }

  static MethodChannel getMethodChanel() {
    return instance()._methodChannel;
  }

  static EventChannel getEventChannl() {
    return instance()._eventChannel;
  }
}
