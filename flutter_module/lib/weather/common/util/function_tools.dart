import 'package:fluttermodule/chanel_manger.dart';

class FunctionTools {
  static toast(String message) {
    ChanelManger.getMethodChanel().invokeMethod('toast', message);
  }
}
