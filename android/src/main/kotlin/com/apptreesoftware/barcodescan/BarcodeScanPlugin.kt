package com.apptreesoftware.barcodescan

import android.app.Activity
import android.content.Intent
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.PluginRegistry
import io.flutter.plugin.common.PluginRegistry.Registrar
import org.json.JSONObject

class BarcodeScanPlugin(val activity: Activity): MethodCallHandler,
    PluginRegistry.ActivityResultListener {
  var result : Result? = null
  companion object {
    @JvmStatic
    fun registerWith(registrar: Registrar): Unit {
      val channel = MethodChannel(registrar.messenger(), "com.apptreesoftware.barcode_scan")
      val plugin = BarcodeScanPlugin(registrar.activity())
      channel.setMethodCallHandler(plugin)
      registrar.addActivityResultListener(plugin)
    }
  }

  override fun onMethodCall(call: MethodCall, result: Result): Unit {
    if (call.method.equals("scan")) {
      this.result = result
      showBarcodeView()
    } else {
      result.notImplemented()
    }
  }

  private fun showBarcodeView() {
    val intent = Intent(activity, BarcodeScannerActivity::class.java)
    activity.startActivityForResult(intent, 100)
  }

  override fun onActivityResult(code: Int, resultCode: Int, data: Intent?): Boolean {
    if (code == 100) {
      if (resultCode == Activity.RESULT_OK) {
        val barcode = data?.getStringExtra("SCAN_RESULT")
        val format = data?.getStringExtra("SCAN_FORMAT_RESULT")
          val json =  JSONObject()
          barcode?.let {
              json.put("SCAN_RESULT",barcode)
          }
          format?.let {
              json.put("SCAN_FORMAT_RESULT", format)
          }
          (json.length() > 0).let {
              this.result?.success(json.toString())
          }
//        barcode?.let { this.result?.success(barcode) }
      } else {
        val errorCode = data?.getStringExtra("ERROR_CODE")
        this.result?.error(errorCode, null, null)
      }
      return true
    }
    return false
  }
}
