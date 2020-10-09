import 'package:dio/dio.dart';

const int _kReceiveTimeout = 15000;
const int _kSendTimeout = 15000;
const int _kConnectTimeout = 15000;

///http请求
class NetManager {

  static NetManager _instance = NetManager._internal();

  Dio _dio;

  ///通用全局单例，第一次使用时初始化
  NetManager._internal({String token}) {
    if (null == _dio) {
      _dio = Dio(BaseOptions(
        connectTimeout: _kReceiveTimeout,
        receiveTimeout: _kConnectTimeout,
        sendTimeout: _kSendTimeout,
      ));
    }
    _dio.interceptors.add(LogInterceptor());
    // _dio.interceptors.add(ResponseInterceptors());
  }

  static NetManager getInstance() {
    return _instance;
  }

  NetManager baseUrl(String baseUrl) {
    _dio.options.baseUrl = baseUrl;
    return _instance;
  }

  Future<Response> get(String url,
      {Map<String, dynamic> header, Map<String, dynamic> param}) async {
    Response response;
    try {
      response = await _dio.get(url, queryParameters: param);
    } on DioError catch (e) {
      return resultError(e);
    }
    if (response.data is DioError) {
      return resultError(response.data);
    }
    return response.data;
  }

  resultError(DioError e) {
    Response errorResponse;
    if (e.response != null) {
      errorResponse = e.response;
    } else {
      errorResponse = Response(statusCode: 666);
    }
    if (e.type == DioErrorType.CONNECT_TIMEOUT ||
        e.type == DioErrorType.RECEIVE_TIMEOUT) {}
    return;
  }


}