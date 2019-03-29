# Barcode Scanner
Android 扫码结束返回对象修改

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
  
  码和码的format一并返回 。
