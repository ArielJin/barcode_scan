package com.apptreesoftware.barcodescan

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.apptreesoftware.barcodescan.utils.StatusBarUtil
import com.apptreesoftware.barcodescan.widgets.AutoClickImageView
import com.apptreesoftware.barcodescan.widgets.AutoClickTextView
import com.google.android.material.appbar.AppBarLayout
import com.google.zxing.Result
import com.yourcompany.barcodescan.R
import me.dm7.barcodescanner.zxing.ZXingScannerView
import java.lang.Exception


class BarcodeScannerActivity : Activity(), ZXingScannerView.ResultHandler {

    lateinit var scannerView: ZXingScannerView

    companion object {
        val KEY_APPBAR_COLOR = "KEY_APPBAR_COLOR"
        val REQUEST_TAKE_PHOTO_CAMERA_PERMISSION = 100
        val TOGGLE_FLASH = 200

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        var appBarColor = intent.getStringExtra(KEY_APPBAR_COLOR)//FFF5BC8B
        try {
            StatusBarUtil.initWindowsStatusBar(this, Color.parseColor(appBarColor))
        }catch (e: Exception) {
            appBarColor = "#FF000000"
            StatusBarUtil.initWindowsStatusBar(this, Color.parseColor(appBarColor))
        }
        super.onCreate(savedInstanceState)
        title = ""
        scannerView = ZXingScannerView(this)
        scannerView.setAutoFocus(true)
        // this paramter will make your HUAWEI phone works great!
        scannerView.setAspectTolerance(0.5f)

        val  cl = ConstraintLayout(this)
        cl.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT)

        val appBarLayout = AppBarLayout(this)
        
        
        val appBarLayoutLp = AppBarLayout.LayoutParams(AppBarLayout.LayoutParams.MATCH_PARENT, resources.getDimensionPixelSize(R.dimen.app_dp_54))
        appBarLayout.layoutParams = appBarLayoutLp
        appBarLayout.id = R.id.scan_bar

        appBarLayout.setBackgroundColor(Color.parseColor(appBarColor))

        val appBarContentCl = ConstraintLayout(this)
        appBarContentCl.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, resources.getDimensionPixelSize(R.dimen.app_dp_54))

        val leftIcon = AutoClickImageView(this)
        leftIcon.setImageResource(R.mipmap.ic_back_white)
        leftIcon.setPadding(resources.getDimensionPixelSize(R.dimen.app_dp_8), resources.getDimensionPixelSize(R.dimen.app_dp_6), 0, resources.getDimensionPixelSize(R.dimen.app_dp_6))
        val leftIconLp = ConstraintLayout.LayoutParams(resources.getDimensionPixelSize(R.dimen.app_dp_38), ConstraintLayout.LayoutParams.MATCH_PARENT)
        leftIconLp.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID
        leftIconLp.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
        leftIconLp.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
//        leftIconLp.setMargins(getResources().getDimensionPixelSize(R.dimen.app_dp_8), 0, 0, 0);
        leftIcon.setOnClickListener { 
            this.finish()
        }

        leftIcon.layoutParams = leftIconLp
        
        val  rightText = AutoClickTextView(this)
        rightText.text = "开灯"
        rightText.setTextColor(Color.WHITE)
        rightText.gravity = Gravity.CENTER
        rightText.setPadding(resources.getDimensionPixelSize(R.dimen.app_dp_20), 0 ,resources.getDimensionPixelSize(R.dimen.app_dp_20), 0)
        rightText.setOnClickListener {
            
            scannerView.flash = !scannerView.flash
            if (scannerView.flash) {
                rightText.text = "关灯" 
            } else {
                rightText.text = "开灯"
            }
            
        }
        
        val rightTextLp = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, resources.getDimensionPixelSize(R.dimen.app_dp_54))
        rightTextLp.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID
        rightTextLp.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
        rightTextLp.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
        rightText.layoutParams = rightTextLp


        appBarContentCl.addView(leftIcon)
        appBarContentCl.addView(rightText)

        appBarLayout.addView(appBarContentCl)


        val scannerViewLp = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
        scannerViewLp.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
        scannerViewLp.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID
        scannerViewLp.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID


        scannerView.layoutParams = scannerViewLp

        cl.addView(scannerView)
        cl.addView(appBarLayout)

        setContentView(cl)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (scannerView.flash) {
            val item = menu.add(0,
                    TOGGLE_FLASH, 0, "Flash Off")
            item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        } else {
            val item = menu.add(0,
                    TOGGLE_FLASH, 0, "Flash On")
            item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == TOGGLE_FLASH) {
            scannerView.flash = !scannerView.flash
            this.invalidateOptionsMenu()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        scannerView.setResultHandler(this)
        // start camera immediately if permission is already given
        if (!requestCameraAccessIfNecessary()) {
            scannerView.startCamera()
        }
    }

    override fun onPause() {
        super.onPause()
        scannerView.stopCamera()
    }

    override fun handleResult(result: Result?) {

        val field = result?.javaClass?.getDeclaredField("format")
        field?.isAccessible = true
        val format = field?.get(result)

        val intent = Intent()
        intent.putExtra("SCAN_RESULT", result.toString())
        intent.putExtra("SCAN_FORMAT_RESULT", format.toString())
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    fun finishWithError(errorCode: String) {
        val intent = Intent()
        intent.putExtra("ERROR_CODE", errorCode)
        setResult(Activity.RESULT_CANCELED, intent)
        finish()
    }

    private fun requestCameraAccessIfNecessary(): Boolean {
        val array = arrayOf(Manifest.permission.CAMERA)
        if (ContextCompat
                .checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, array,
                    REQUEST_TAKE_PHOTO_CAMERA_PERMISSION)
            return true
        }
        return false
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,grantResults: IntArray) {
        when (requestCode) {
            REQUEST_TAKE_PHOTO_CAMERA_PERMISSION -> {
                if (PermissionUtil.verifyPermissions(grantResults)) {
                    scannerView.startCamera()
                } else {
                    finishWithError("PERMISSION_NOT_GRANTED")
                }
            }
            else -> {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }
    }
}

object PermissionUtil {

    /**
     * Check that all given permissions have been granted by verifying that each entry in the
     * given array is of the value [PackageManager.PERMISSION_GRANTED].

     * @see Activity.onRequestPermissionsResult
     */
    fun verifyPermissions(grantResults: IntArray): Boolean {
        // At least one result must be checked.
        if (grantResults.size < 1) {
            return false
        }

        // Verify that each required permission has been granted, otherwise return false.
        for (result in grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }
}
