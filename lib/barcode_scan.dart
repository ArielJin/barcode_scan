import 'dart:async';
import 'dart:convert';
import 'dart:io';

import 'package:barcode_scan/mode/sacn_result.dart';
import 'package:flutter/services.dart';

class BarcodeScanner {
  static const CameraAccessDenied = 'PERMISSION_NOT_GRANTED';
  static const MethodChannel _channel =
      const MethodChannel('com.apptreesoftware.barcode_scan');
  static Future<ScanResult> scan() async {

    var scanResult = await _channel.invokeMethod('scan');
    if(Platform.isAndroid) {
      /**
       * Android返回SCAN_RESULT 和 FORMAT
       */
      Map<String, dynamic> map = json.decode(scanResult);
      return ScanResult(map['SCAN_RESULT'], map['SCAN_FORMAT_RESULT']);
    } else {
      /**
       * IOS未实现  所以FORMAT返回""
       */
      return ScanResult(scanResult, "");

    }

  }
}
