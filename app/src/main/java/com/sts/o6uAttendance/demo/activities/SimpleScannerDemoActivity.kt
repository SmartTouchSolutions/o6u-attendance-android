package com.sts.o6uAttendance.demo.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import me.dm7.barcodescanner.zbar.Result
import me.dm7.barcodescanner.zbar.ZBarScannerView
import me.dm7.barcodescanner.zbar.ZBarScannerView.ResultHandler


class SimpleScannerDemoActivity : AppCompatActivity(), ResultHandler {
    private var mScannerView: ZBarScannerView? = null
    public override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        mScannerView = ZBarScannerView(this) // Programmatically initialize the scanner view
        setContentView(mScannerView) // Set the scanner view as the content view
    }

    public override fun onResume() {
        super.onResume()
        mScannerView!!.setResultHandler(this) // Register ourselves as a handler for scan results.
        mScannerView!!.startCamera() // Start camera on resume
    }

    public override fun onPause() {
        super.onPause()
        mScannerView!!.stopCamera() // Stop camera on pause
    }

    override fun handleResult(rawResult: Result) { // Do something with the result here
        Log.v("STS Dev", rawResult.contents) // Prints scan results
        Log.v("STS Dev",rawResult.barcodeFormat.name) // Prints the scan format (QR code, pdf417 etc.)
        if(rawResult.barcodeFormat.name == "CODE39"){
            val returnIntent = Intent()
            returnIntent.putExtra("result", rawResult.contents)
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }
        // If you would like to resume scanning, call this method below:
       mScannerView?.resumeCameraPreview(this)
    }
}