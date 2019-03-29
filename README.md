# Barcode Scanner
Android 扫码结束返回对象修改

static Future<ScanResult> scan() async {

    var scanResult = await _channel.invokeMethod('scan');
    Map<String, dynamic> map = json.decode(scanResult);
    return ScanResult(map['SCAN_RESULT'], map['SCAN_FORMAT_RESULT']);
  }
  
  
  码和码的format一并返回 。
